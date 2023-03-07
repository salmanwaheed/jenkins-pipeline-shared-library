// @Library("yc-lib@test-01")_

// yc_jenkins_config {
//     apps "cover", "safeguard"
//     agent "my-agent"
//     daysToKeeplogs 30
//     upstreams "up"
//     downstreams "down", "test"
// }

class YcPipeline implements Serializable {

    def lib
    def agent = null
    def apps = []
    def environments = [:]
    def properties = [:]

    YcPipeline(lib) {
        this.lib = lib
        // this.config << new YcConfig()
    }

    def envs() {
        def params = []
        this.environments.each { key, value ->
            params.add("${key}=${value}")
        }
        return params
    }

    def properties() {
        YcPipelineProperties props = new YcPipelineProperties(lib)
        props.logs = this.properties.logs
        props.githubProject = this.properties.githubProject
        // props.triggers = this.properties.triggers

        props.generate()
    }

    def stages() {
        YcStages args = new YcStages(lib)

        args.checkout()
        args.build()
    }

    def run() {
        lib.node(this.agent) {
            lib.withEnv( this.envs() ) {
                lib.timestamps {
                    // lib.echo "${this.environments}"
                    lib.echo "${lib.env.APP_NAME}"
                    // echo this.apps

                    this.properties()
                    this.stages()
                }
            }
        }
    }
}

// class YcConfig {

// }

class YcPipelineProperties {
    def lib
    def logs = [ days: -1 ]
    def githubProject = [ name: null, url: null ]

    YcPipelineProperties(lib) {
        this.lib = lib
    }

    def githubProject() {
        lib.githubProjectProperty(
            displayName: this.githubProject.name,
            projectUrlStr: this.githubProject.url
        )
    }

    def logs() {
        lib.buildDiscarder(
            lib.logRotator(daysToKeepStr: "${this.logs.days}")
        )
    }

    // def triggers() {
    //     lib.pipelineTriggers([
    //         lib.upstream('tset')
    //     ])
    // }

    def generate() {
        lib.properties([
            this.githubProject(),
            this.logs(),
            // this.parameters( yc_params ),
            // this.triggers()
        ])
    }
}

class YcStages {
    def lib

    YcStages(lib) {
        this.lib = lib
    }

    def checkout(name="checkout") {
        lib.stage(name) {
            lib.echo "my checkout"
        }
    }

    def build(name="build") {
        lib.stage(name) {
            lib.echo "my build"
        }
    }
}

def utils = new YcPipeline(this)
utils.apps = ["cover", "safeguard"]
utils.agent = null

utils.environments = [
    APP_NAME: utils.apps?.find { true }
]

utils.properties = [
    logs: [ days: 30 ],
    githubProject: [ name: "testing...", url: "https://github.com" ],
    // triggers = []
]

// utils.upstreams = ["up"]
// utils.downstreams = ["down", "test"]
utils.run()



