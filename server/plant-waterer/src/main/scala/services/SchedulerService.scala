package services
import java.time.temporal.ChronoUnit
import java.time.{Instant, LocalDateTime, ZoneId, ZoneOffset}

import cron4s._
import cron4s.lib.javatime._
import services.utils.DateTimeFormats._
case class State(time: String, volume: Int)

class SchedulerService(plantsService: PlantsService) {

  def getSchedule(pinId: Int, startTime: Instant, duration: Int): Seq[State] = {
    plantsService.getPlants(pinId = Some(pinId)).headOption match {
      case Some(value) =>
        val cronSchedule = Cron.unsafeParse(value.schedule)
        val localDateTime = LocalDateTime.ofInstant(startTime, ZoneId.from(ZoneOffset.UTC))
        (0 until duration).map { increment =>
          val nextTime = localDateTime.plus(increment, ChronoUnit.MINUTES)
          val formattedLocalDateTime = reformatLocalDateTime(nextTime)
          State(
            time = printerFormatter.format(formattedLocalDateTime),
            volume = if (cronSchedule.allOf(formattedLocalDateTime)) value.volume else 0
          )
        }
      case None => Seq.empty[State]
    }
  }
}
