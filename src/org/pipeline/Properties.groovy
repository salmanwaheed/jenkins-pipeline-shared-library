package org.pipeline

class Properties implements Serializable {
  def lib

  Properties(lib) {
    this.lib = lib
  }

  def githubProject(args=[:]) {
    args.get("name", null)
    args.get("url", null)

    lib.githubProjectProperty(
      displayName: args.name,
      projectUrlStr: args.url
    )
  }

  def logs(args=[:]) {
    args.get("days", -1)

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
      def method_type = it.type
      def name = it.name.toLowerCase().capitalize()

      switch(method_type) {
        case "choice":
          params.add(
            lib.choice(
              name: name,
              choices: it.get("choices", []),
              description: it.get("desc", "choose an item")
            )
          )
        break
        case "string":
          params.add(
            lib.string(
              name: name,
              trim: true,
              defaultValue: it.get("defaultBranch", "release"),
              description: it.get("desc", "add a valid branch")
            )
          )
        break
        // default:
        //   it.get("description", "desc will be here")

        //   params.add(
        //     lib."${method_type}"(it)
        //   )
      }

      // it.remove("type")

      // it.get("type", "string")

      // if ( it.type == "choice" ) {
      //   it.get("desc", "choose an item")

      //   params.add(
      //     lib.choice(
      //       name: it.name.toLowerCase().capitalize(),
      //       choices: it.choices,
      //       description: it.desc
      //     )
      //   )
      // } else {
      //   it.get("defaultBranch", "release")
      //   it.get("desc", "add a valid branch")

      //   params.add(
      //     lib.string(
      //       name: it.name.toLowerCase().capitalize(),
      //       defaultValue: it.defaultBranch,
      //       trim: true,
      //       description: it.desc
      //     )
      //   )
      // }
    }

    lib.parameters(params)
  }

  def generate(args=[]) {
    lib.properties(args)
  }
}
