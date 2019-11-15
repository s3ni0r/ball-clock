package fr.flaminem.logic
import fr.flaminem.models.Clock._
import fr.flaminem.models._
import monocle.Lens

import scala.annotation.tailrec

object Handler {

  def initClock(capacity: Int) = {
    val bottomBalls = (1 to capacity).map(Ball(_)).toList
    Clock(Track(bottomBalls, capacity), Track(capacity = 4), Track(capacity = 11), Track(capacity = 11))
  }

  def addMinute(clockState: Clock): Clock = {
    bottomTrackBallsLens.get(clockState).headOption match {
      case Some(oldestBottomTrackBall) => {
        (bottomTrackBallsLens.modify(e => e.drop(1)) andThen minutesTrackBallsLens.modify(e =>
          e :+ oldestBottomTrackBall))(clockState)
      }
      case None => clockState
    }
  }

  def assertTrackState(
      sourceTrackLens: => Lens[Clock, Track],
      targetTrackLens: => Lens[Clock, Track],
  )(clockState: Clock): Clock = {
    sourceTrackLens.get(clockState) match {
      case Track(balls, capacity) if balls.size > capacity => {
        val sourceTrackBallLens = (sourceTrackLens composeLens trackBallsLens)
        val targetTrackBallLens = (targetTrackLens composeLens trackBallsLens)
        val newBottomTrackBalls = sourceTrackBallLens.get(clockState).dropRight(1).reverse
        val overflowBall        = sourceTrackBallLens.get(clockState).lastOption
        (sourceTrackBallLens.set(List.empty[Ball]) andThen
          bottomTrackBallsLens.modify(e => e ++ newBottomTrackBalls) andThen
          targetTrackBallLens.modify(e  => e ++ overflowBall))(clockState)
      }
      case _ => clockState
    }
  }

  def tick(clockState: Clock) = {
    val chainAll = (addMinute _ andThen assertTrackState(minutesTrackLens, fiveMinutesTrackLens) _) andThen
      (assertTrackState(fiveMinutesTrackLens, hoursTrackLens) _) andThen
      (assertTrackState(hoursTrackLens, bottomTrackLens) _)

    chainAll(clockState)
  }

  def runForFiniteDuration(capacity: Int, minutes: Int): Clock = {
    (1 to minutes).foldLeft(initClock(capacity))((cs, _) => tick(cs))
  }

  @tailrec
  def runUntilCondition(bottom: Track, clockState: Clock, minutesCounter: Int): Int = {
    if (bottom == clockState.bottom && minutesCounter != 0) {
      minutesCounter / 60 / 24
    } else
      runUntilCondition(bottom, tick(clockState), minutesCounter + 1)
  }
}
