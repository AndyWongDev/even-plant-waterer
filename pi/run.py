# import RPi.GPIO as GPIO
import sched
import time
from datetime import datetime
from typing import List

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
global_scheduler = sched.scheduler(time.time, time.sleep)

#  =================
#  global MVars for holding state
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
                "time": "2020-12-18T17:36",
                "state": True
            },
            {
                "time": "2020-12-18T17:37",
                "state": True
            },
            {
                "time": "2020-12-18T17:38",
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


def should_water_now(scheduled: List[dict]) -> bool:
    ts = datetime.utcnow().strftime("%Y-%m-%dT%H:%M")
    print(ts)
    for s in scheduled:
        if s['time'] == ts:
            return s['state']
    return False


#  =================
#  Event loops
#  =================
def watering_loop(sc):
    for plant in plants:
        schedule = plant_schedules[plant['id']]
        if should_water_now(schedule):
            print(f"Watering : {plant['plantType']} on pin {plant['pinId']}")
    watering_scheduler.enter(5, 1, watering_loop, (sc,))


def server_checking_loop(sc):
    global plants
    global plant_schedules
    plants = plants_response()
    print(f"Checking : received {len(plants)} plants")

    for plant in plants:
        plant_schedules[plant['id']] = schedules_response(plant["pinId"], datetime.now(), 60)
    server_scheduler.enter(10, 1, server_checking_loop, (sc,))


def global_scheduling_loop(sc):
    server_scheduler.run(False)
    watering_scheduler.run(False)
    time.sleep(1)
    global_scheduler.enter(0, 1, global_scheduling_loop, (global_scheduler,))


def unsafe_run_sync():
    server_scheduler.enter(0, 1, server_checking_loop, (server_scheduler,))
    watering_scheduler.enter(0, 1, watering_loop, (watering_scheduler,))
    global_scheduler.enter(0, 1, global_scheduling_loop, (global_scheduler,))
    global_scheduler.run(False)

unsafe_run_sync()