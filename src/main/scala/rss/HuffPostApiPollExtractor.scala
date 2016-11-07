package rss

import java.time.LocalDate

import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.{Authority, NamedHost, Path}
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.util.ByteString
import models._
import org.json4s.DefaultFormats
import org.json4s._
import org.json4s.native.JsonMethods._
import rss.PostPollExtractor.PollModel

import scala.concurrent.{Await, Future}

/**
  * Created by russell on 11/6/16.
  */

object PostPollExtractor {

  case class PollModel(pollster: String, start_date: String, end_date: String, questions: List[QuestionModel])

  case class QuestionModel(code: String, name: String, topic: String, state: String, subpopulations: List[PopulationModel])

  case class PopulationModel(name: String, observations: Option[Int], responses: List[ResponseModel])

  case class ResponseModel(choice: String, value: Int)

}

class HuffPostApiPollExtractor() {

  implicit val formats = DefaultFormats
  val path = "/pollster/api/polls.json"
  val scheme = "http"
  val target = "elections.huffingtonpost.com"
  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  def loadPolls(n: Int): Seq[Poll] = {
    val pagesRequired = (n / 10).max(1).min(32)
    val pageFutures = (0 until pagesRequired).map { page =>
      val uri = Uri(scheme = scheme, authority = Authority(NamedHost(target), 80), path = Path(path), queryString = Some(s"page=$page&topic=2016-president"))
      val responseFuture: Future[HttpResponse] =
        Http().singleRequest(HttpRequest(uri = uri))
      for {
        resp <- responseFuture
        data <- resp.entity.dataBytes.runFold(ByteString(""))(_ ++ _)
      } yield {
        val responseString = data.utf8String
        val parsed = parse(responseString)
        parsed.extract[List[PollModel]]
      }
    }

    val pollFuture = Future.sequence(pageFutures).map(_.flatten)

    val extractedPollFuture = pollFuture.map { polls => polls.flatMap { poll =>
      val pollster = poll.pollster
      val startDate = LocalDate.parse(poll.start_date)
      val endDate = LocalDate.parse(poll.end_date)
      val validQuestions = poll.questions.filter(q => q.topic == "2016-president" && q.state != "US")
      validQuestions.flatMap { question =>
        val StateRegex = """2016 (.*?) President.*""".r
        val state = question.name match {
          case StateRegex(s) => State.fromString(s)
          case _ => throw new RuntimeException("IllegalState")
        }
        question.subpopulations.map { subpop =>
          val sample = Sample(LikelyVoter, subpop.observations.getOrElse(1))
          val result = subpop.responses.flatMap { resp =>
            Candidate.fromString(resp.choice).map(_ -> resp.value.toDouble / 100)
          }.toMap
          Poll(state, startDate -> endDate, pollster, sample, result)
        }
      }
    } }

    Await.result(extractedPollFuture, 10.seconds)
  }
}
