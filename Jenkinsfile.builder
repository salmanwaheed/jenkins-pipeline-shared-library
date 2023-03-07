@Library("yc-lib")_

yc_jenkins_config {
    apps "cover", "safeguard"
    agent "my-agent"
    daysToKeeplogs 30
    upstreams "up"
    downstreams "down", "test"

    actions {
      echo 'hello first script'
      echo 'hello second script'
    }

    // actions [
    //     "echo 'hello first script'",
    //     "echo 'hello second script'"
    // ]
}
