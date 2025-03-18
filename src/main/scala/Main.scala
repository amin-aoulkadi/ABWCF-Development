import abwcf.actors.Crawler
import com.typesafe.config.ConfigFactory
import org.apache.pekko.actor.typed.ActorSystem

import scala.jdk.CollectionConverters.*

@main def startCrawler(): Unit = {
  val actorSystem = ActorSystem(Crawler(), "crawler")
  
  val seedUrls = ConfigFactory.load("seed-urls")
    .getStringList("seed-urls")
    .asScala
    .toSeq

  actorSystem ! Crawler.SeedUrls(seedUrls)
}
