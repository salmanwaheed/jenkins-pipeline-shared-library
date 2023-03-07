package org.pipeline

import org.pipeline.Config
import org.pipeline.Properties
import org.pipeline.Stages
import org.pipeline.Notify

class Pipeline implements Serializable {

  def lib
  def agent = null
  def apps = []
  def environments = [:]
  def logs = [:]
  def githubProject = [:]

  Pipeline(lib) {
    this.lib = lib
  }

  def envs() {
    def envs = []
    this.environments.each { key, value ->
      envs.add("${key}=${value}")
    }
    return envs
  }

  def properties() {
    Properties props = new Properties(lib)

    props.generate([
      props.githubProject(this.githubProject),
      props.logs(this.logs),
      props.parameters(apps: this.apps)
    ])
  }

  def stages() {
    Stages stage = new Stages(lib)

    stage.checkout name: "checkout", apps: this.apps
    stage.build name: "build"
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
