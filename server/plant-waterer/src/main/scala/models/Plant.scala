package models
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Plant(id: Int,
                 pinId: Int,
                 plantType: String,
                 volume: Int,
                 schedule: String,
                 deleted: Option[Boolean])

case class PlantCreateData(pinId: Int,
                           plantType: String,
                           volume: Int,
                           schedule: String)

object Plants {

  trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val plantFormat = jsonFormat6(Plant)
    implicit val plantCreateDataFormat = jsonFormat4(PlantCreateData)
  }

}
