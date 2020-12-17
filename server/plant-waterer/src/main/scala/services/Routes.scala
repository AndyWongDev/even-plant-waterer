package services

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import models.PlantCreateData
import models.Plants.JsonSupport

class Routes(plantsService: PlantsService) extends JsonSupport {

  def routes: Route = path("plants") {
    get {
      complete(plantsService.getPlants())
    } ~ post {
      entity(as[PlantCreateData]) { plantCreateData =>
        val plant = plantsService.addPlant(plantCreateData)
        complete(plant)
      }
    }
  }

}
