import org.pipeline.Pipeline

def call(Closure body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  def utils = new Pipeline(this)
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
}
