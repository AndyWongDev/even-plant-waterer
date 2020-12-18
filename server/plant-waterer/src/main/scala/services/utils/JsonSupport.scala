package services.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import models.{Plant, PlantCreateData}
import services.State
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val plantFormat = jsonFormat6(Plant)
  implicit val plantCreateDataFormat = jsonFormat4(PlantCreateData)
  implicit val stateFormat = jsonFormat2(State)
}
