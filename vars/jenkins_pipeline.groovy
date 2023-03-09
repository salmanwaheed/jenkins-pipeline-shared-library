import org.pipeline.Config
import org.pipeline.Pipeline

def call(Closure body) {
  def config = new Config()
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  // config.apps = [
  //   [ name: "cover", type: "string", defaultBranch: "branch-name", desc: "my string" ],
  //   [ name: "safeguard", type: "choice", choices: ["item-1", "item-2"], desc: "my choices" ]
  // ]
  echo "config.apps: ${config.apps}"

  // config.agent = null
  echo "config.agent: ${config.agent}"

  config.environments.get('APP_NAME', config.apps?.name?.find { true } )
  echo "config.environments: ${config.environments}"

  // config.logs = [ days: 30 ]
  echo "config.logs: ${config.logs}"

  // config.githubProject = [ name: "testing...", url: "https://github.com" ]
  echo "config.githubProject: ${config.githubProject}"

  // config.upstreams = ["up"]
  // echo "config.upstreams: ${config.upstreams}"

  // config.downstreams = ["down", "test"]
  // echo "config.downstreams: ${config.downstreams}"

  // config.builds = [
  //     echo: "Hi, echo",
  //     sh: "hello, shell"
  // ]
  // echo "config.builds: ${config.builds}"

  def utils = new Pipeline(this, config)
  utils.run()
}
