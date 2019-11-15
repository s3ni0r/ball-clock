package fr.flaminem.models
import io.circe.syntax._
import io.circe.{Encoder, Json}
import monocle.macros.GenLens

final case class Clock(bottom: Track, minutes: Track, fiveMinutes: Track, hours: Track)

object Clock {

  implicit val encoder: Encoder[Clock] = (a: Clock) =>
    Json.obj(
      ("Min", a.minutes.balls.map(_.tag).asJson),
      ("FiveMin", a.fiveMinutes.balls.map(_.tag).asJson),
      ("Hour", a.hours.balls.map(_.tag).asJson),
      ("Main", a.bottom.balls.map(_.tag).asJson),
  )

  val bottomTrackLens      = GenLens[Clock](_.bottom)
  val minutesTrackLens     = GenLens[Clock](_.minutes)
  val fiveMinutesTrackLens = GenLens[Clock](_.fiveMinutes)
  val hoursTrackLens       = GenLens[Clock](_.hours)
  val trackBallsLens       = GenLens[Track](_.balls)

  val bottomTrackBallsLens     = bottomTrackLens composeLens trackBallsLens
  val minutesTrackBallsLens    = minutesTrackLens composeLens trackBallsLens
  val fiveMinutesRailBallsLens = fiveMinutesTrackLens composeLens trackBallsLens
  val hoursRailBallsLens       = hoursTrackLens composeLens trackBallsLens
}
