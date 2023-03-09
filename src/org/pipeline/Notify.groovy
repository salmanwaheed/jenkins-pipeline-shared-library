package org.pipeline

class Notify implements Serializable {

  def started() {
    return "started"
  }

  def succeed() {
    return "succeed"
  }

  def failed() {
    return "failed"
  }

  def aborted() {
    return "aborted"
  }

}
