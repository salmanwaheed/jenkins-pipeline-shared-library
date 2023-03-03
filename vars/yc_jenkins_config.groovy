def call(Closure body) {
  def utils = new com.YcDsl()
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = utils
  body()

  pipeline {
    agent any

    stages {

      stage("build") {
        steps {
          script {
            echo "${utils.apps}"
            echo "${utils.agent}"
            echo "${utils.daysToKeeplogs}"
            echo "${utils.upstreams}"
            echo "${utils.downstreams}"
            // echo "${utils.actions}"
          }
        }
      }

      // stage("test") {
      //     steps {
      //         // script {
      //           echo "${utils.name}"
      //           // echo "${utils.repos}"
      //             // for ( x in utils.repos ) {
      //             //   echo "${x}"
      //             // }
      //         // }
      //     }
      // }

    }
  }

}
