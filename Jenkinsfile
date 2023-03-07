@Library("shared-library")_

jenkins_pipeline {
  apps "cover", "safeguard"
  agent "my-agent"
  daysToKeeplogs 30
  upstreams "up"
  downstreams "down", "test"

  // actions {
  //   echo 'hello first script'
  //   echo 'hello second script'
  // }

  // actions = [
  //   "echo 'hello first script'",
  //   "echo 'hello second script'"
  // ]
}
