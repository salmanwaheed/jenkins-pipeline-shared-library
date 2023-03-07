package com

class YcDsl {

  List apps = []
  String agent = "any"
  Integer daysToKeeplogs = 60
  List downstreams = []
  List upstreams = []
  YcAction actions

  def apps(String[] arg) {
    this.apps = arg
  }

  def agent(String arg) {
    this.agent = arg
  }

  def daysToKeeplogs(Integer arg) {
    this.daysToKeeplogs = arg
  }

  def upstreams(String[] args) {
    this.upstreams = args
  }

  def downstreams(String[] args) {
    this.downstreams = args
  }

  // def parameters() {
  //   // return parameters(
  //     this.apps.each { x ->
  //       string(
  //         name: "${x}",
  //         defaultValue: "release",
  //         trim: true,
  //         description: "which branch you want to deploy?"
  //       )
  //     }
  //   // )
  // }

  // def actions(Closure body) {
  //   def utils = new com.YcAction()
  //   body.resolveStrategy = Closure.DELEGATE_FIRST
  //   body.delegate = utils
  //   // this.actions = body
  //   body.call()
  // }
}
