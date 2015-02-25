package com.chriswk.solr

import akka.actor.{Actor, ActorLogging}
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer
import org.apache.solr.common.SolrInputDocument


case class IndexArticle(article: Article)
case class IndexProvider(provider: Provider)
case class DeleteDoc(id: Long)
case class Article(id: Long, title: String, body: String, provider_id: Long)
case object Commit
case class Provider(id: Long, name: String)

class Indexer extends Actor with ActorLogging {
  val server = new ConcurrentUpdateSolrServer("http://localhost:8983/solr/gettingstarted", 10, 10)

  def receive = {
    case IndexArticle(article) => {
      log.info(s"Adding ${article}")
      server.add(getSolrDoc(article))
    }
    case IndexProvider(provider) => server.add(getSolrDoc(provider))
    case DeleteDoc(id) => server.deleteByQuery(s"id:${id}")
    case Commit => server.commit()
    case done@Batch.Done => sender() ! done
  }

  def getSolrDoc(a: Any): SolrInputDocument = {
    val solrDoc = new SolrInputDocument()
    a.getClass.getDeclaredFields.foreach { field =>
      field.setAccessible(true)
      solrDoc.addField(field.getName, field.get(a))
    }
    solrDoc
  }
}


