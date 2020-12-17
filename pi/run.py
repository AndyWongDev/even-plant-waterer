# import RPi.GPIO as GPIO
import sched
import time
from datetime import datetime

import requests
from RPiSim import GPIO

#  =================
#  STATIC variables
#  =================
PLANT_WATERER_ADDRESS = "https://plantwaterer.com"
MOCKING = True

#  =================
#  Set up schedulers
#  =================
watering_scheduler = sched.scheduler(time.time, time.sleep)
server_scheduler = sched.scheduler(time.time, time.sleep)

#  =================
#  MVars for holding state
#  =================
plants = []
plant_schedules = {}


#  =================
#  HTTP with Server
#  =================
def plants_response():
    if MOCKING:
        return
    else:
        return requests.get(f"{PLANT_WATERER_ADDRESS}/plants").json()


def schedules_response(plantId, time: datetime, duration_in_mins: int):
    if MOCKING:
        return
    else:
        return requests.get(f"{PLANT_WATERER_ADDRESS}/schedules"
                            f"?plantId={plantId}"
                            f"&start={time.isoformat().format()}"
                            f"&durationInMins={duration_in_mins}").json()


#  =================
#  Event loops
#  =================
def watering_loop(sc):
    print("Doing stuff...")
    # do your stuff
    watering_scheduler.enter(60, 1, watering_loop, (sc,))


def server_checking_loop(sc):
    print("Doing stuff...")
    # do your stuff
    watering_scheduler.enter(60 * 5, 1, watering_loop, (sc,))
    # Plants(pinId: Int,
    #        plantType: String,
    #        volume: Float,
    #        schedule: String)
    plants = plants_response()

    for plant in plants:
        plant_schedules = schedules_response(plant["id"], datetime.now(), 60)


watering_scheduler.enter(60, 1, watering_loop, (watering_scheduler,))
watering_scheduler.run()
server_scheduler.enter(60 * 5, 1, server_checking_loop, (server_scheduler,))
server_scheduler.run()
