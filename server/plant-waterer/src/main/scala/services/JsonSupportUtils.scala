package services
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import models.{Plant, PlantCreateData}
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val plantFormat = jsonFormat6(Plant)
  implicit val plantCreateDataFormat = jsonFormat4(PlantCreateData)
  implicit val stateFormat = jsonFormat2(State)
}
