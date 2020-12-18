package services.utils
import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, ZoneId, ZoneOffset}

object DateTimeFormats {

  val cronFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:00").withZone(ZoneId.from(ZoneOffset.UTC))

  val printerFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:00.000").withZone(ZoneId.from(ZoneOffset.UTC))

  def reformatLocalDateTime(localDateTime: LocalDateTime): LocalDateTime = {
    LocalDateTime.ofInstant(
      Instant.parse(cronFormatter.format(localDateTime) + 'Z'),
      ZoneId.from(ZoneOffset.UTC)
    )
  }

}
