package fr.flaminem

import fr.flaminem.logic.Handler._
import fr.flaminem.models._
import org.scalatest._

class HandleSpec extends FunSpec with Matchers {
  describe("ClockState") {
    describe("Initialization") {
      it("should init it state correctly ") {
        initClock(27) shouldBe
          Clock(
            Track((1 to 27).toList.map(Ball(_)), 27),
            Track(capacity = 4),
            Track(capacity = 11),
            Track(capacity = 11)
          )
      }
    }

    describe("addMinute") {
      it("should return oldest element in the base track and update clockState's base track") {
        val expected = Clock(
          Track((2 to 27).toList.map(Ball(_)), 27),
          Track(List(Ball(1)), 4),
          Track(capacity = 11),
          Track(capacity = 11)
        )
        addMinute(initClock(27)) shouldBe expected
      }
    }

    describe("checkMinutesRail") {
      it("should handle overflow") {
        val overflowBall = Ball(5)
        val newBaseBalls = List(Ball(1), Ball(2), Ball(3), Ball(4))
        val input = Clock(
          Track((5 to 27).toList.map(Ball(_)), 27),
          Track(newBaseBalls :+ overflowBall, 4),
          Track(capacity = 11),
          Track(capacity = 11)
        )
        val expected = Clock(
          Track((5 to 27).toList.map(Ball(_)) ++ newBaseBalls.reverse, 27),
          Track(List(), 4),
          Track(List(overflowBall), 11),
          Track(capacity = 11)
        )

        assertTrackState(Clock.minutesTrackLens, Clock.fiveMinutesTrackLens)(input) shouldBe expected

      }
    }

    describe("Run for finite duration") {
      it("should return expected value") {
        runForFiniteDuration(30, 325) match {
          case Clock(base, minutes, fiveMinutes, hours) => {
            base.balls.map(_.tag) shouldBe List(11, 5, 26, 18, 2, 30, 19, 8, 24, 10, 29, 20, 16, 21, 28, 1, 23, 14, 27,
              9)
            minutes.balls.map(_.tag) shouldBe List()
            fiveMinutes.balls.map(_.tag) shouldBe List(22, 13, 25, 3, 7)
            hours.balls.map(_.tag) shouldBe List(6, 12, 17, 4, 15)
          }
        }
      }
    }

    describe("Run until recycle") {
      it("should return 15 days for 30 balls") {
        val initState = initClock(30)
        runUntilCondition(initState.bottom, initState, 0) shouldBe 15
      }

      it("should return 378 days for 45 balls") {
        val initState = initClock(45)
        runUntilCondition(initState.bottom, initState, 0) shouldBe 378
      }
    }
  }
}
