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
  apps "cover", "safeguard"
  agent "my-agent"
  daysToKeeplogs 30
  upstreams "up"
  downstreams "down", "test"
}
```
