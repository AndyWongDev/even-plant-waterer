package init
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import database.PlantsTable
import services.{PlantsService, Routes}

import scala.concurrent.ExecutionContext

object Main extends App {
  val host = "0.0.0.0"
  val port = 9000
  implicit val system: ActorSystem = ActorSystem("plant-waterer")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val plantsTable = new PlantsTable()
  val plantsService = new PlantsService(plantsTable)
  def route = new Routes(plantsService).routes

  Http().bindAndHandle(route, host, port)
}
