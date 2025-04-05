import abwcf.actors.Crawler
import abwcf.util.{CrawlerSettings, PrioritizationFunctions}
import com.typesafe.config.ConfigFactory
import org.apache.pekko.actor.typed.ActorSystem

import scala.jdk.CollectionConverters.*

@main def startCrawler(): Unit = {
  val settings = CrawlerSettings().withPrioritizationFunction(PrioritizationFunctions.Random)
  val actorSystem = ActorSystem(Crawler(settings), "crawler")

  val seedUrls = ConfigFactory.load("seed-urls")
    .getStringList("seed-urls")
    .asScala
    .toSeq

  actorSystem ! Crawler.SeedUrls(seedUrls)
}
