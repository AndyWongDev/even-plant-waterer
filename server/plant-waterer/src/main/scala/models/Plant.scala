package models

case class Plant(id: Int, pinId: Int, plantType: String, volume: Int, schedule: String, deleted: Boolean = false)

case class PlantCreateData(pinId: Int, plantType: String, volume: Int, schedule: String)
