package models

import java.time.LocalDate
import java.time.temporal.ChronoUnit.DAYS

import scala.util.Random

/**
  * Created by russell on 11/6/16.
  */
case class SimulatorSettings(stalenessPenaltyPerDay: Double, pollingErrorStdev: Double = 3.0) {
  require(stalenessPenaltyPerDay < 1)
}

class Simulator(settings: SimulatorSettings) {

  def simulateN(polls: Seq[Poll], n: Int): SimulationResult = {
    println(s"Polls from ${polls.groupBy(_.state).keys.toList.length} states")
    val results = (0 until n).map { _ =>
      determineWinner(simulateOnce(polls))
    }

    val winners = results.map(_.winner).groupBy(identity).mapValues(_.length.toDouble / n)
    val stateResults = {
      val stateMaps = results.map(_.stateMap)
      val states = stateMaps.head.keySet
      states.map { state =>
        val stateResult = stateMaps.map(_.apply(state)).groupBy(identity).mapValues(_.length.toDouble / n)
        state -> stateResult
      }.toMap
    }
    SimulationResult(winners, stateResults)
  }

  def determineWinner(result: Map[State, Candidate]): ElectionResult = {
    val candidateMap = result.groupBy(_._2).mapValues(_.keys.toList)
    val votesToEach = candidateMap.mapValues(states => states.map(_.votes).sum)
    val winner = votesToEach.maxBy(_._2)._1
    ElectionResult(winner, result)
  }

  def simulateOnce(polls: Seq[Poll]): Map[State, Candidate] = {
    val statePollMap = polls.groupBy(_.state)
    val stateResults = statePollMap.map { case (state, statePolls) => state -> simulateState(state, statePolls) }
    val stateWinners = stateResults.map { case (state, result) =>
        state -> result.maxBy(_._2)._1
    }
    stateWinners
  }

  def simulateState(state: State, polls: Seq[Poll]): Map[Candidate, Double] = {
    require(polls.forall(_.state == state))

    // first derive an average
    val weights = {
      val unnormalized = polls.map(poll => poll -> determineWeight(poll)).toMap
      val weightSum = unnormalized.values.sum
      unnormalized.mapValues(_ / weightSum)
    }

    val average = weights.foldLeft(Map.empty[Candidate, Double].withDefaultValue(0.0)) { case (accum, (poll, weight)) =>
        val weightedResult = poll.result.mapValues(res => res * weight)
        val newMap = weightedResult.map { case (candidate, percentage) =>
          candidate -> (accum(candidate) + percentage)
        }
        accum ++ newMap
    }

    // Estimate undecided pct. Our weighting strategy will undercount the percentage of undecided voters
    val pollsWithUndecided = polls.count(_.result.contains(Undecided))
    val ratio = pollsWithUndecided.toDouble / polls.length
    val undecidedPerc = average.getOrElse(Undecided, 0.0) / ratio
    pollingError(average - Undecided, undecidedPerc)
  }

  def pollingError(inp: Map[Candidate, Double], extraError: Double) = {
    // stdev 1                   percentage stdev
    val error = Random.nextGaussian() * (settings.pollingErrorStdev + extraError * 100) / 100
    val list = inp.toArray
    val c1 = Random.nextInt(inp.size)
    val c2 = (c1 + 1 + Random.nextInt(inp.size - 1)) % inp.size
    val candidate1 = list(c1)._1
    val candidate2 = list(c2)._1
    require(candidate1 != candidate2)
    inp.updated(candidate1, inp(candidate1) + error).updated(candidate2, inp(candidate2) - error)
  }

  def determineWeight(poll: Poll) = {
    val today = LocalDate.now()
    val dateOffset = DAYS.between(poll.dateRange._1, today)
    val weightBySize = poll.sample.size
    val discountByAge = Math.pow(1 - settings.stalenessPenaltyPerDay, dateOffset)
    weightBySize * discountByAge
  }

}
