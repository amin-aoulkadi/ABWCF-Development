import abwcf.actors.FetchResultConsumer.*
import abwcf.actors.PageManager
import abwcf.data.{Page, PageStatus}
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.cluster.sharding.typed.scaladsl.ClusterSharding

object LoggingFetchResultConsumer {
  def apply(): Behavior[Command] = Behaviors.setup(context => {
    val sharding = ClusterSharding(context.system)

    def notifyPageManager(page: Page): Unit = {
      val pageManager = sharding.entityRefFor(PageManager.TypeKey, page.url)
      pageManager ! PageManager.SetStatus(PageStatus.Processed)
    }

    Behaviors.receiveMessage({
      case Success(page, response) =>
        context.log.info("Processing page {} ({}, {} bytes)", page.url, response.status, response.body.length)
        notifyPageManager(page)
        Behaviors.same

      case Redirect(page, statusCode, redirectTo) =>
        context.log.info("Processing redirect from {} ({}, redirection to {})", page.url, statusCode, redirectTo)
        notifyPageManager(page)
        Behaviors.same

      case Error(page, statusCode) =>
        context.log.info("Processing error from {} ({})", page.url, statusCode)
        notifyPageManager(page)
        Behaviors.same

      case LengthLimitExceeded(page, response) =>
        context.log.info("{} ({}) was not fetched because it exceeds the maximum accepted content length", page.url, response.status)
        notifyPageManager(page)
        Behaviors.same
    })
  })
}
