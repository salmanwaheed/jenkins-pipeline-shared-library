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
        def envs = []
        this.environments.each { key, value ->
            envs.add("${key}=${value}")
        }
        return envs
    }

    def properties() {
        YcPipelineProperties props = new YcPipelineProperties(lib)

        props.generate([
            props.githubProject(this.githubProject),
            props.logs(this.logs),
            props.parameters(apps: this.apps)
        ])
    }

    def stages() {
        YcStages args = new YcStages(lib)

        args.checkout name: "checkoasdf", apps: this.apps
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
    // def logs = [ days: -1 ]
    // def githubProject = [ name: null, url: null ]

    YcPipelineProperties(lib) {
        this.lib = lib
    }

    def githubProject(args=[:]) {
        lib.githubProjectProperty(
            displayName: args.name,
            projectUrlStr: args.url
        )
    }

    def logs(args=[:]) {
        lib.buildDiscarder(
            lib.logRotator(daysToKeepStr: "${args.days}")
        )
    }

    // def triggers() {
    //     lib.pipelineTriggers([
    //         lib.upstream('tset')
    //     ])
    // }

    def parameters(args=[:]) {
        def params = []
        args.apps.each {
            params.add(
                lib.string(
                    name: it.toLowerCase().capitalize(),
                    defaultValue: "release",
                    trim: true,
                    description: "which branch you want to deploy?"
                )
            )
        }

        lib.parameters(params)
    }

    def generate(args=[]) {
        lib.properties(args)
    }
}

class YcStages {
    def lib

    YcStages(lib) {
        this.lib = lib
    }

    def checkout(args=[:]) {
        lib.stage(args.name) {
            lib.echo "my checkout ${args}"

            args.apps.each {
                def app_name = it.toLowerCase()

                lib.dir(app_name) {
                    lib.git branch: lib.params."${app_name.capitalize()}",
                        changelog: false,
                        poll: false,
                        // credentialsId: "github-creds",
                        url: "git@github.com:compareit4mehub/${app_name}.git"
                }
            }
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

utils.logs = [ days: 30 ]
utils.githubProject = [ name: "testing...", url: "https://github.com" ]

// utils.upstreams = ["up"]
// utils.downstreams = ["down", "test"]

// utils.builds = [
//     echo: "Hi, echo",
//     sh: "hello, shell"
// ]

utils.run()



