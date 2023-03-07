package org.pipeline

class Properties implements Serializable {
  def lib
  // def logs = [ days: -1 ]
  // def githubProject = [ name: null, url: null ]

  Properties(lib) {
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
