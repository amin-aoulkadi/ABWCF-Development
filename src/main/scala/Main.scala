import abwcf.actors.Crawler
import abwcf.data.{FetchResponse, Page, PageCandidate}
import abwcf.util.{CrawlerSettings, PrioritizationFunctions, UserCode}
import com.typesafe.config.ConfigFactory
import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.scaladsl.ActorContext
import org.apache.pekko.http.scaladsl.model.StatusCode

import scala.jdk.CollectionConverters.*

@main def startCrawler(): Unit = {
  val userCode = new UserCode {
    override def prioritize(candidate: PageCandidate, context: ActorContext[_]): Long =
      PrioritizationFunctions.random

    override def onFetchSuccess(page: Page, response: FetchResponse, context: ActorContext[_]): Unit =
      context.log.info("Processing page {} ({}, {} bytes)", page.url, response.status, response.body.length)

    override def onFetchRedirect(page: Page, statusCode: StatusCode, redirectTo: Option[String], context: ActorContext[_]): Unit =
      context.log.info("Processing redirect from {} ({}, redirection to {})", page.url, statusCode, redirectTo)

    override def onFetchError(page: Page, statusCode: StatusCode, context: ActorContext[_]): Unit =
      context.log.info("Processing error from {} ({})", page.url, statusCode)
  }

  val settings = CrawlerSettings(userCode)
  val actorSystem = ActorSystem(Crawler(settings), "crawler")

  val seedUrls = ConfigFactory.load("seed-urls")
    .getStringList("seed-urls")
    .asScala
    .toSeq

  actorSystem ! Crawler.SeedUrls(seedUrls)
}
