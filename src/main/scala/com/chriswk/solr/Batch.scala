package com.chriswk.solr
import scala.util.Random
import akka.actor.{ActorLogging, Props, Actor}
object Batch {
  case object Done
}
class Batch extends Actor with ActorLogging {

  val nouns = List("programmer", "nerd", "geek", "techie", "luddite")
  val adjectives = List("friendly", "sober", "inebriated", "drunk", "cozy", "stupid")
  val foodstuffs = List("bacon", "eggs", "ham", "pancakes", "sirloin")
  override def preStart(): Unit = {
    log.info("Starting batch job")
    val indexer = context.actorOf(Props[Indexer], "indexer")
    val provider = Provider(1, "Lorem provider")
    indexer ! IndexProvider(provider)
    for (i <- 1 to 1000) {
      val doc = Article(i, s"${Random.shuffle(adjectives).head} ${Random.shuffle(nouns).head}", s"${Random.shuffle(foodstuffs).head}", 1)
      indexer ! IndexArticle(doc)
    }
    indexer ! Commit
    indexer ! Batch.Done
  }

  def receive = {
    case Batch.Done => context.stop(self)
  }

}
