import abwcf.actors.{Crawler, FetchResultConsumer}
import abwcf.api.{CrawlerSettings, UserCode}
import com.typesafe.config.ConfigFactory
import org.apache.pekko.actor.typed.{ActorSystem, Behavior}

import scala.jdk.CollectionConverters.*

@main def startCrawler(): Unit = {
  val userCode = new UserCode {
    override def createFetchResultConsumer(settings: CrawlerSettings): Behavior[FetchResultConsumer.Command] =
      LoggingFetchResultConsumer()
  }

  val settings = CrawlerSettings(userCode)
  val actorSystem = ActorSystem(Crawler(settings), "crawler")

  val seedUrls = ConfigFactory.load("seed-urls")
    .getStringList("seed-urls")
    .asScala
    .toSeq

  actorSystem ! Crawler.SeedUrls(seedUrls)
}
