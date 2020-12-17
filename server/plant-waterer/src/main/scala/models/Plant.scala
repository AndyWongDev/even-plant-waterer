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
