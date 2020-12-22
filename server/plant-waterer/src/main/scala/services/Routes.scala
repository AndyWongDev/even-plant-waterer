package services

import java.time.Instant

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import models.PlantCreateData
import services.utils.JsonSupport

class Routes(plantsService: PlantsService, schedulerService: SchedulerService) extends JsonSupport {

  def routes: Route =
    cors() {
      path("plants") {
        get {
          parameter("pinId".?) { pinId =>
            complete(plantsService.getPlants(pinId.map(_.toInt)))
          }
        } ~ post {
          entity(as[PlantCreateData]) { plantCreateData =>
            val plant = plantsService.addPlant(plantCreateData)
            complete(plant)
          }
        } ~ delete {
          parameter("pinId".as[Int]) { (id) =>
            plantsService.delete(id)
            complete(s"plant at pinId: $id deleted")
          }
        } ~ put {
          parameter("pinId".as[Int], "schedule", "volumn".as[Int]) { (pinId, schedule, volume) =>
            complete(plantsService.updatePlant(pinId, schedule, volume))
          }
        }
      } ~ path("schedules") {
        get {
          parameters("pinId".as[Int], "startTime", "duration".as[Int]) { (pinId, startTime, duration) =>
            complete(
              schedulerService
                .getSchedule(pinId, Instant.parse(startTime), duration)
            )
          }
        }
      }
    }
}
