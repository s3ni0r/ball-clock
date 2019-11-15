package fr.flaminem.utils
import scopt.OParser

object Command {
  /*
   * this is bad :(
   */
  case class Config(numberOfBalls: Int = -1, minutes: Int = -1)

  val builder = OParser.builder[Config]

  val parser = {
    import builder._
    OParser.sequence(
      programName("BallClock"),
      opt[Int]('c', "capacity")
        .required()
        .action((x, c) => c.copy(numberOfBalls = x))
        .validate {
          case s if s < 27 | s > 127 => failure("ball cpacity value must be in range 27 to 127")
          case _                     => success
        }
        .text("cpacity is a required field"),
      opt[Int]('m', "minutes")
        .optional()
        .action((x, c) => c.copy(minutes = x))
        .validate {
          case s if s < 0 => failure("minutes value must be in  positive")
          case _          => success
        }
        .text("minutes is optional"),
      help("help").text(
        """
          | BallClock simulation in scala
          |
          | Two simulation are implemented :
          |
          | 1. Get number of elapsed days before first recycle by providing a number of balls in 27 to 127 range
          |
          | Example :
          |
          |   BallClock --capacity 30
          |   BallClock -c 30
          |
          | 2. Get Clock state after running simulation for a number of minutes and number of balls provided as parameter
          |
          | Example :
          |
          |   BallClock --capacity 30 --minutes 325
          |   BallClock -c 30 -m 325
          |
        """.stripMargin),
    )
  }
}
