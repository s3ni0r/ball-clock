package fr.flaminem
import fr.flaminem.logic.Handler.{initClock, _}
import fr.flaminem.models.Clock._
import fr.flaminem.utils.Command._
import io.circe.syntax._
import scopt.OParser
import cats.effect._

object Main {

  /**
    * Error Handling is not implemented as it should be, it is superficial validation in scopt
    * i've tried my luck with Refined but compile time refinement is not my thing :)
    * */
  def main(args: Array[String]): Unit = {
    val program = for {
      config <- IO(OParser.parse(parser, args, Config()))
      result <- checkArgs(config)
      res    <- putLnStr(result)
    } yield res

    program.unsafeRunSync()
  }

  def checkArgs(config: Option[Config]) = {
    config match {
      case Some(Config(numberOfBalls, -1))      => IO(displayRunUntilCondition(numberOfBalls))
      case Some(Config(numberOfBalls, minutes)) => IO(runForFiniteDuration(numberOfBalls, minutes).asJson.noSpaces)
      case None                                 => IO.never
    }
  }

  def putLnStr(message: Any) = IO { println(message) }

  def displayRunUntilCondition(capacity: Int) = {
    val initClockState = initClock(capacity)
    val res            = runUntilCondition(initClockState.bottom, initClockState, 0)
    s"${capacity} balls cycle after ${res} days"
  }

}
