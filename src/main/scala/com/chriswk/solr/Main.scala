package com.chriswk.solr

object Main {
  def main(args: Array[String]): Unit = {
    akka.Main.main(Array(classOf[Batch].getName))
  }
}
