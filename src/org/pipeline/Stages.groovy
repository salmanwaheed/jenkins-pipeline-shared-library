package org.pipeline

class Stages implements Serializable {
  def lib

  Stages(lib) {
    this.lib = lib
  }

  def checkout(args=[:]) {
    args.get("name", "Checkout")
    args.get("apps", [])

    if ( !args.apps ) return;

    lib.stage(args.name) {
      // lib.echo "my checkout ${args}"
      args.apps.each {
        def app_name = it.name.toLowerCase()
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

  def build(args=[:]) {
    args.get("name", "Build")
    args.get("commands", [])

    if ( !args.commands ) return;

    lib.stage(args.name) {
      args.commands.each { key, value ->
        lib."${key}" "${value}"
      }
    }
  }
}
