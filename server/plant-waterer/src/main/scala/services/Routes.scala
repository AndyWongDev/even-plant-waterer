package services

import java.time.Instant

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import models.PlantCreateData

class Routes(plantsService: PlantsService, schedulerService: SchedulerService) extends JsonSupport {

  def routes: Route =
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
      }
    } ~ path("schedules") {
      get {
        parameters("pinId".as[Int], "startTime", "duration".as[Int]) { (pinId, startTime, duration) =>
          val response = schedulerService.getSchedule(pinId, Instant.parse(startTime), duration)
          println(response)
          complete(
            schedulerService
              .getSchedule(pinId, Instant.parse(startTime), duration)
          )
        }
      }
    }
}
