package services
import database.PlantsTable
import models.{Plant, PlantCreateData}

import scala.util.Random

class PlantsService(table: PlantsTable) {

  def addPlant(createData: PlantCreateData): Plant = {
    table.plants.find(_.pinId == createData.pinId) match {
      case Some(_) =>
        delete(pinId = createData.pinId)
        val plant = Plant(
          id = Random.nextInt(),
          pinId = createData.pinId,
          plantType = createData.plantType,
          volume = createData.volume,
          schedule = createData.schedule
        )
        println("creating new plant")
        table.plants = plant :: table.plants
        plant
      case None =>
        val plant = Plant(
          id = Random.nextInt(),
          pinId = createData.pinId,
          plantType = createData.plantType,
          volume = createData.volume,
          schedule = createData.schedule
        )
        println("creating new plant")
        table.plants = plant :: table.plants
        plant
    }
  }

  def delete(pinId: Int): Unit = {
    val plantToBeDeleted =
      table.plants.find(plant => plant.pinId == pinId && !plant.deleted)
    val remainingPlants =
      table.plants.filterNot(plant => plant.pinId == pinId && !plant.deleted)
    plantToBeDeleted match {
      case Some(value) =>
        println(s"deleting plant at pinId: $pinId")
        table.plants = Plant(
          id = value.id,
          pinId = value.pinId,
          plantType = value.plantType,
          volume = value.volume,
          schedule = value.schedule,
          deleted = true
        ) :: remainingPlants
      case None =>
        println(s"could not find plant with pinId $pinId")
        table.plants = remainingPlants
    }
  }

  def updatePlant(pinId: Int, schedule: String, volume: Int): Option[Plant] = {
    getPlants(pinId = Some(pinId)).headOption match {
      case Some(value) =>
        val otherPlants = table.plants.filter(_.pinId == pinId)
        val updatedPlant =
          Plant(id = value.id, pinId = value.pinId, plantType = value.plantType, volume = volume, schedule = schedule)
        table.plants = updatedPlant :: otherPlants
        Some(updatedPlant)
      case None => None
    }
  }

  def getPlants(pinId: Option[Int] = None): Seq[Plant] = {
    pinId match {
      case Some(id) => table.plants.filter(plant => plant.pinId == id && !plant.deleted)
      case None     => table.plants.filter(!_.deleted)
    }
  }
}
