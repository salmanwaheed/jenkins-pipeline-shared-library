package org.pipeline

import org.pipeline.Config
import org.pipeline.Properties
import org.pipeline.Stages
import org.pipeline.Notify

class Pipeline implements Serializable {

  def lib
  def config

  Pipeline(lib, Config config) {
    this.lib = lib
    this.config = config
  }

  def envs() {
    def envs = []
    this.config.environments.each { key, value ->
      envs.add("${key}=${value}")
    }
    return envs
  }

  def properties() {
    Properties props = new Properties(lib)

    props.generate([
      props.githubProject(this.config.githubProject),
      props.logs(this.config.logs),
      props.parameters(apps: this.config.apps)
    ])
  }

  def stages() {
    Stages stage = new Stages(lib)

    stage.checkout apps: this.config.apps
    stage.build commands: this.config.build_commands
  }

  def run() {
    lib.node(this.config.agent) {
      lib.withEnv( this.envs() ) {
        lib.timestamps {
          // lib.echo "${this.config.environments}"
          // lib.echo "${lib.env.APP_NAME}"
          // echo this.config.apps

          this.properties()
          this.stages()
        }
      }
    }
  }
}
