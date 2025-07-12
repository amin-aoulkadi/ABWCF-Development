import abwcf.actors.{Crawler, FetchResultConsumer}
import abwcf.api.{CrawlerSettings, UserCode}
import com.typesafe.config.ConfigFactory
import io.opentelemetry.api.GlobalOpenTelemetry
import org.apache.pekko.actor.typed.{ActorSystem, Behavior}

import scala.jdk.CollectionConverters.*

@main def startCrawler(): Unit = {
  val userCode = new UserCode {
    override def createFetchResultConsumer(settings: CrawlerSettings): Behavior[FetchResultConsumer.Command] =
      LoggingFetchResultConsumer()
  }

  val openTelemetry = GlobalOpenTelemetry.get()
  val settings = CrawlerSettings(userCode, openTelemetry)
  val actorSystem = ActorSystem(Crawler(settings), "crawler")

  val seedUrls = ConfigFactory.load("seed-urls")
    .getStringList("seed-urls")
    .asScala
    .toSeq

  actorSystem ! Crawler.SeedUrls(seedUrls)
}
