# import RPi.GPIO as GPIO
import sched
import time
from datetime import datetime

import requests

# from RPiSim import GPIO

#  =================
#  STATIC variables
#  =================
PLANT_WATERER_ADDRESS = "http://localhost:9000"
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
        return [
            {
                "plantType": "Cactus",
                "id": 1100877211,
                "schedule": "every week",
                "pinId": 1333302141,
                "volume": 4
            },
            {
                "plantType": "Maple",
                "id": 318525812,
                "schedule": "every day",
                "pinId": -985857873,
                "volume": 1
            }
        ]
    else:
        url = f"{PLANT_WATERER_ADDRESS}/plants"
        return requests.get(url).json()


def schedules_response(plant_id, start_time: datetime, duration_in_mins: int):
    if MOCKING:
        return [
            {
                "time": "2020-12-17T21:21:23.109Z",
                "state": True
            },
            {
                "time": "2020-12-17T21:21:23.109Z",
                "state": True
            },
            {
                "time": "2020-12-17T21:21:23.109Z",
                "state": False
            }
        ]
    else:
        url = (f"{PLANT_WATERER_ADDRESS}/schedules"
               f"?pinId={plant_id}"
               f"&startTime={start_time.isoformat().format()}Z"
               f"&duration={duration_in_mins}")
        print(url)
        return requests.get(url).json()


#  =================
#  Event loops
#  =================
def watering_loop(sc):
    print("Watering...")
    # do your stuff
    watering_scheduler.enter(5, 1, watering_loop, (sc,))


def server_checking_loop(sc):
    print("Checking Server...")
    # do your stuff
    # Plants(pinId: Int,
    #        plantType: String,
    #        volume: Float,
    #        schedule: String)
    plants = plants_response()

    for plant in plants:
        print(plant)
        plant_schedules = schedules_response(plant["pinId"], datetime.now(), 60)
    server_scheduler.enter(10, 1, server_checking_loop, (sc,))


server_scheduler.enter(0, 1, server_checking_loop, (server_scheduler,))
watering_scheduler.enter(0, 1, watering_loop, (watering_scheduler,))

while True:
    server_scheduler.run(False)
    watering_scheduler.run(False)
