package models

import java.time.LocalDate

import models.States.Colorado

/**
  * Created by russell on 11/6/16.
  */

sealed trait Candidate
object Candidate {
  def fromString(s: String): Option[Candidate] = {
    if (s.toLowerCase contains "clinton") {
      Some(Clinton)
    } else if (s.toLowerCase contains "trump") {
      Some(Trump)
    } else if (s.toLowerCase contains "johnson") {
      Some(Johnson)
    } else if (s.toLowerCase contains "stein") {
      Some(Stein)
    } else if (s.toLowerCase contains "mcmull") {
      Some(McMullin)
    } else {
      Some(Undecided)
    }
  }
}

case object Clinton extends Candidate

case object Trump extends Candidate

case object Stein extends Candidate

case object Johnson extends Candidate

case object McMullin extends Candidate

case object Undecided extends Candidate

sealed trait VoterType

case object LikelyVoter extends VoterType

case object Adult extends VoterType

case object RegisteredVoter extends VoterType

case class Sample(voterType: VoterType, size: Int)

case class Poll(state: State, dateRange: (LocalDate, LocalDate), pollster: String, sample: Sample, result: Map[Candidate, Double]) {
  require(result.values.forall(_ < 1))
}

case class ElectionResult(winner: Candidate, stateMap: Map[State, Candidate])
case class SimulationResult(winChance: Map[Candidate, Double], stateWinChance: Map[State, Map[Candidate, Double]])

object State {
  import States._
  def fromString(s: String) = {
    s.toLowerCase() match {
      case  "alabama" => Alabama
      case  "alaska" => Alaska
      case  "arizona" => Arizona
      case  "arkansas" => Arkansas
      case  "california" => California
      case  "colorado" => Colorado
      case  "connecticut" => Connecticut
      case  "delaware" => Delaware
      case  "florida" => Florida
      case  "georgia" => Georgia
      case  "hawaii" => Hawaii
      case  "idaho" => Idaho
      case  "indiana" => Indiana
      case  "illinois" => Illinois
      case  "iowa" => Iowa
      case  "kansas" => Kansas
      case  "kentucky" => Kentucky
      case  "louisiana" => Louisiana
      case  "maine" => Maine
      case  "maryland" => Maryland
      case  "massachusetts" => Massachusetts
      case  "michigan" => Michigan
      case  "minnesota" => Minnesota
      case  "mississippi" => Mississippi
      case  "missouri" => Missouri
      case  "nebraska" => Montana
      case  "montana" => Montana
      case  "nevada" => Nevada
      case  "new hampshire" => NewHampshire
      case  "new jersey" => NewJersey
      case  "new mexico" => NewMexico
      case  "new york" => NewYork
      case  "north carolina" => NorthCarolina
      case  "north dakota" => NorthDakota
      case  "ohio" => Ohio
      case  "oklahoma" => Oklahoma
      case  "oregon" => Oregon
      case  "pennsylvania" => Pennsylvania
      case  "rhode island" => RhodeIsland
      case  "south carolina" => SouthCarolina
      case  "south dakota" => SouthDakota
      case  "tennessee" => Tennessee
      case  "texas" => Texas
      case  "utah" => Utah
      case  "vermont" => Vermont
      case  "virginia" => Virginia
      case  "washington" => Washington
      case  "west virginia" => WestVirginia
      case  "wisconsin" => Wisconsin
      case  "wyoming" => Wyoming
    }
  }
}
sealed trait State {
  def votes: Int
}

object States {
  case object Alabama extends State {
    def votes: Int = 9
  }

  case object Montana extends State {
    def votes: Int = 3
  }

  case object Alaska extends State {
    def votes: Int = 3
  }

  case object Nebraska extends State {
    def votes: Int = 5
  }

  case object Arizona extends State {
    def votes: Int = 11
  }

  case object Nevada extends State {
    def votes: Int = 6
  }

  case object Arkansas extends State {
    def votes: Int = 6
  }

  case object NewHampshire extends State {
    def votes: Int = 4
  }

  case object California extends State {
    def votes: Int = 55
  }

  case object NewJersey extends State {
    def votes: Int = 14
  }

  case object Colorado extends State {
    def votes: Int = 9
  }

  case object NewMexico extends State {
    def votes: Int = 5
  }

  case object Connecticut extends State {
    def votes: Int = 7
  }

  case object NewYork extends State {
    def votes: Int = 29
  }

  case object Delaware extends State {
    def votes: Int = 3
  }

  case object NorthCarolina extends State {
    def votes: Int = 15
  }

  case object Florida extends State {
    def votes: Int = 29
  }

  case object NorthDakota extends State {
    def votes: Int = 3
  }

  case object Georgia extends State {
    def votes: Int = 16
  }

  case object Ohio extends State {
    def votes: Int = 18
  }

  case object Hawaii extends State {
    def votes: Int = 4
  }

  case object Oklahoma extends State {
    def votes: Int = 7
  }

  case object Idaho extends State {
    def votes: Int = 4
  }

  case object Oregon extends State {
    def votes: Int = 7
  }

  case object Illinois extends State {
    def votes: Int = 20
  }

  case object Pennsylvania extends State {
    def votes: Int = 20
  }

  case object Indiana extends State {
    def votes: Int = 11
  }

  case object RhodeIsland extends State {
    def votes: Int = 4
  }

  case object Iowa extends State {
    def votes: Int = 6
  }

  case object SouthCarolina extends State {
    def votes: Int = 9
  }

  case object Kansas extends State {
    def votes: Int = 6
  }

  case object SouthDakota extends State {
    def votes: Int = 3
  }

  case object Kentucky extends State {
    def votes: Int = 8
  }

  case object Tennessee extends State {
    def votes: Int = 11
  }

  case object Louisiana extends State {
    def votes: Int = 8
  }

  case object Texas extends State {
    def votes: Int = 38
  }

  case object Maine extends State {
    def votes: Int = 4
  }

  case object Utah extends State {
    def votes: Int = 6
  }

  case object Maryland extends State {
    def votes: Int = 10
  }

  case object Vermont extends State {
    def votes: Int = 3
  }

  case object Massachusetts extends State {
    def votes: Int = 11
  }

  case object Virginia extends State {
    def votes: Int = 13
  }

  case object Michigan extends State {
    def votes: Int = 16
  }

  case object Washington extends State {
    def votes: Int = 12
  }

  case object Minnesota extends State {
    def votes: Int = 10
  }

  case object WestVirginia extends State {
    def votes: Int = 5
  }

  case object Mississippi extends State {
    def votes: Int = 6
  }

  case object Wisconsin extends State {
    def votes: Int = 10
  }

  case object Missouri extends State {
    def votes: Int = 10
  }

  case object Wyoming extends State {
    def votes: Int = 3
  }

}

