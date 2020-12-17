package services

import java.time.Instant

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import models.PlantCreateData

class Routes(plantsService: PlantsService, schedulerService: SchedulerService)
    extends JsonSupport {

  def routes: Route =
    path("plants") {
      get {
        complete(plantsService.getPlants())
      } ~ post {
        entity(as[PlantCreateData]) { plantCreateData =>
          val plant = plantsService.addPlant(plantCreateData)
          complete(plant)
        }
      }
    } ~ path("schedules") {
      get {
        parameters("pinId", "startTime", "duration") {
          (pinId, startTime, duration) =>
            complete(
              schedulerService.getSchedule(
                pinId.toInt,
                Instant.parse(startTime),
                duration.toInt
              )
            )
        }
      }
    }
}
