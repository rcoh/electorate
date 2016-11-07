import java.time.LocalDate

import models._
import models.States._
import org.scalatest.{FlatSpec, Matchers}
import rss.HuffPostApiPollExtractor

/**
  * Created by russell on 11/6/16.
  */
class SimulatorTest extends FlatSpec with Matchers {
  val sut = new Simulator(SimulatorSettings(.05, 5))
  val poll1 = Poll(California, (LocalDate.now(), LocalDate.now()), "foo", Sample(LikelyVoter, 10), Map(Trump -> 0.3, Clinton -> 0.5))
  val poll2 = Poll(California, (LocalDate.now(), LocalDate.now()), "foo", Sample(LikelyVoter, 10), Map(Trump -> 0.5, Clinton -> 0.3))
  val poll3 = Poll(California, (LocalDate.now(), LocalDate.now()), "foo", Sample(LikelyVoter, 30), Map(Trump -> 0.3, Clinton -> 0.5))

  lazy val extractor = new HuffPostApiPollExtractor()
  lazy val polls = extractor.loadPolls(1000).toSet.toList
  behavior of "Poll weighting"
  it should "weight polls properly by voter size" ignore {
    sut.determineWeight(poll1) should be(10.0)
    sut.simulateState(California, Seq(poll1)) should be (Map(Clinton -> 0.5, Trump -> 0.3))
    sut.simulateState(California, Seq(poll1, poll2)) should be (Map(Clinton -> 0.4, Trump -> 0.4))
    sut.simulateState(California, Seq(poll2, poll3)) should be(Map(Clinton -> 0.45, Trump -> 0.35))
  }

  it should "weight polls properly by date" in{
    val oldPoll = poll1.copy(dateRange = (LocalDate.now().minusDays(1), LocalDate.now()))
    sut.determineWeight(oldPoll) should be(9.5)
  }

  it should "simulate the election" in {
    println(s"Running with ${polls.length} polls, 10k iterations")
    pprint.pprintln(sut.simulateN(polls, 10000))
  }

  it should "compute a polling error" in {
    val inp: Map[Candidate, Double] = Map(Clinton -> 0.4, Trump -> 0.2, Stein -> 0.1)
    val res = sut.pollingError(inp, .05)
    res should not equal(inp)
    res.values.sum should equal(inp.values.sum)
  }

}
