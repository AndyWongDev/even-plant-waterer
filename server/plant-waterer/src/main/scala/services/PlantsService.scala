package services
import database.PlantsTable
import models.{Plant, PlantCreateData}

import scala.util.Random

class PlantsService(table: PlantsTable) {

  def addPlant(createData: PlantCreateData): Plant = {
    val plant = Plant(
      id = Random.nextInt(),
      pinId = createData.pinId,
      plantType = createData.plantType,
      volume = createData.volume,
      schedule = createData.schedule,
      deleted = None
    )
    println("creating new plant")
    table.plants = plant :: table.plants
    plant
  }

  def delete(pinId: Int) = {
    println(s"deleting plant at pin: $pinId")
  }

  def getPlants(pinId: Option[Int] = None): Seq[Plant] = {
    Seq(
      Plant(
        id = Random.nextInt(),
        pinId = Random.nextInt(),
        plantType = "Cactus",
        volume = 4,
        schedule = "every week",
        None
      ),
      Plant(
        id = Random.nextInt(),
        pinId = Random.nextInt(),
        plantType = "Maple",
        volume = 1,
        schedule = "every day",
        None
      )
    )
  }

}
