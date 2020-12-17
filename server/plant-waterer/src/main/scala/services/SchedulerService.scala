package services
import java.time.Instant

case class State(time: String, state: Boolean)

class SchedulerService() {

  def getSchedule(pinId: Int, startTime: Instant, duration: Int): Seq[State] = {

    Seq(
      State(Instant.now().toString, state = true),
      State(Instant.now().toString, state = true),
      State(Instant.now().toString, state = false)
    )
  }
}
