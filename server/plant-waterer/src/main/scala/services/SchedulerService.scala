package services
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.{Instant, LocalDateTime, ZoneId, ZoneOffset}

import cron4s._
import cron4s.lib.javatime._
case class State(time: String, volume: Int)

class SchedulerService(plantsService: PlantsService) {

  val dateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:00").withZone(ZoneId.from(ZoneOffset.UTC))

  val dtf: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:00.000").withZone(ZoneId.from(ZoneOffset.UTC))

  def getSchedule(pinId: Int, startTime: Instant, duration: Int): Seq[State] = {

    plantsService.getPlants(pinId = Some(pinId)).headOption match {
      case Some(value) =>
        val parsed = Cron.unsafeParse(value.schedule)
        println(parsed)
        (0 until duration).map { increment =>
          val localDateTime = LocalDateTime.ofInstant(startTime, ZoneId.from(ZoneOffset.UTC))
          val nextTime = localDateTime.plus(increment, ChronoUnit.MINUTES)
          val x =
            LocalDateTime.ofInstant(
              Instant.parse(dateTimeFormatter.format(nextTime) + 'Z'),
              ZoneId.from(ZoneOffset.UTC)
            )

          State(dtf.format(x), if (parsed.allOf(x)) { value.volume } else { 0 })
        }
      case None => Seq.empty[State]
    }
  }

}
