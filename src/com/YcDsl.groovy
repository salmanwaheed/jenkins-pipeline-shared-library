package com

class YcDsl {

  List apps = []
  String agent = "any"
  Integer daysToKeeplogs = 60
  List downstreams = []
  List upstreams = []
  List actions = []

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

  // def actions(Closure body) {
  //   // this.actions = actions

  //   def utils = new com.YcAction()
  //   body.resolveStrategy = Closure.DELEGATE_FIRST
  //   body.delegate = utils
  //   body()
  // }
}
