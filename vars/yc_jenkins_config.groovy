def call(Closure body) {
  def notify = new com.YcNotify()
  def dsl = new com.YcDsl()
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = dsl
  body()

  yc_pipeline( dsl, notify )
}

def yc_pipeline(dsl, notify) {
  def app_name = dsl.apps?.find { true }
  def yc_envs = ["APP_NAME=${app_name}"]

  node() {
    withEnv( yc_envs ) {
      timestamps {

        echo "${dsl.apps}"
        echo "${dsl.agent}"
        echo "${dsl.daysToKeeplogs}"
        echo "${dsl.upstreams}"
        echo "${dsl.downstreams}"
        echo env.APP_NAME

        echo "${notify.succeed()}"
        echo "${notify.failed()}"
        echo "${notify.aborted()}"

        yc_props(dsl)
        yc_stages(dsl, notify)

      }
    }
  }
}

def yc_props(dsl) {
  def yc_params = []
  dsl.apps.each {
    yc_params.add(
      string(
        name: "${it.toLowerCase().capitalize()}",
        defaultValue: "release",
        trim: true,
        description: "which branch you want to deploy?"
      )
    )
  }

  properties([
    // githubProjectProperty(displayName: '', projectUrlStr: 'https://github.com/'),
    buildDiscarder( logRotator( daysToKeepStr: "${dsl.daysToKeeplogs}" ) ),
    parameters( yc_params ),
    pipelineTriggers([ upstream('tset') ])
  ])
}

def yc_stages(dsl, notify) {
  yc_checkout_stage(dsl, notify)
  yc_build_stage(dsl)
}

def yc_checkout_stage(dsl, notify) {
  stage("checkout") {
    echo "${notify.started()}"

    dsl.apps.each {
      dir("${it.toLowerCase()}") {
        git branch: params."${it.toLowerCase().capitalize()}",
            changelog: false,
            poll: false,
            // credentialsId: "github-creds",
            url: "git@github.com:compareit4mehub/${it.toLowerCase()}.git"
      }
    }
  }
}

def yc_build_stage(dsl) {
  stage("build") {
    echo "${dsl.apps} for builds"
    echo "my build stage"
  }
}
