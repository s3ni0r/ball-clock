package fr.flaminem.models
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.{Interval, Positive}

object Types {
  type Capacity = Int Refined Interval.ClosedOpen[W.`27`.T, W.`127`.T]
  type Minutes  = Int Refined Positive
}
