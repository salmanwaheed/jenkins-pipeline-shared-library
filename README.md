# jenkins-pipeline-shared-library

add the following code into `/var/lib/jenkins/jenkins.yaml` file and reload existing configuration from `JENKINS_URL/manage/configuration-as-code/`

```yml
unclassified:
  # ....

  globalLibraries:
    libraries:
      - name: shared-library
        defaultVersion: release
        retriever:
          modernSCM:
            scm:
              git:
                id: shared-library
                remote: https://github.com/salmanwaheed/jenkins-pipeline-shared-library.git
                traits:
                  - gitBranchDiscovery
```


create an item, choose pipeline and configure.

```groovy
@Library("shared-library")_

jenkins_pipeline {
  agent = "my-agent"

  apps = [
    [ name: "cover", type: "string", defaultBranch: "branch-name", desc: "my string" ],
    [ name: "safeguard", type: "choice", choices: ["item-1", "item-2"], desc: "my choices" ]
  ]

  environments = [
    MY_VAR: "my-value",
    MY_VAR_INT: 12
  ]

  logs = [ days: 60 ]
  githubProject = [ name: "GITHUB NAME", url: "https://github.com" ]

  // upstreams = ["up"]
  // downstreams = ["down", "test"]

  build_commands = [
    echo: "Hi, echo",
    sh: "echo hello, shell"
  ]
}
```
