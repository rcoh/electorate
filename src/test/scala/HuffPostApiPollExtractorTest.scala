import java.time.LocalDate

import models._
import models.States.Colorado
import org.scalatest.{FlatSpec, Matchers}
import rss.HuffPostApiPollExtractor

/**
  * Created by russell on 11/6/16.
  */
class HuffPostApiPollExtractorTest extends FlatSpec with Matchers {
  behavior of "PollExtractor"

  it should "load polls from huffpost" in {
    val sut = new HuffPostApiPollExtractor()
    //pprint.pprintln(sut.loadPolls(5))
  }
}
