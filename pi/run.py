# import RPi.GPIO as GPIO
import sched
import time
from datetime import datetime, timedelta
from typing import List

import requests

# from RPiSim import GPIO

#  =================
#  STATIC variables
#  =================
PLANT_WATERER_ADDRESS = "http://localhost:9000"
MOCKING = False
INTERVAL_WATERING = 5
INTERVAL_SERVER = 10
INTERVAL_GLOBAL = 1

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
                "time": "2020-12-18T19:32:00.000Z",
                "volume": 1
            },
            {
                "time": "2020-12-18T19:33:00.000Z",
                "volume": 2
            },
            {
                "time": "2020-12-18T19:34:00.000Z",
                "volume": 0
            }
        ]
    else:
        url = (f"{PLANT_WATERER_ADDRESS}/schedules"
               f"?pinId={plant_id}"
               f"&startTime={start_time.strftime('%Y-%m-%dT%H:%M:00.000Z')}"
               f"&duration={duration_in_mins}")
        return requests.get(url).json()


def watering_vol_for_time(scheduled: List[dict]) -> bool:
    ts = datetime.utcnow().strftime("%Y-%m-%dT%H:%M:00.000Z")
    for s in scheduled:
        if s['time'] == ts:
            return s['volume']
    return 0


#  =================
#  Event loops
#  =================
def watering_loop(sc):
    for plant in plants:
        schedule = plant_schedules[plant['id']]
        volume = watering_vol_for_time(schedule)
        print(
            f"[{datetime.utcnow().strftime('%Y-%m-%dT%H:%M:00.000Z')}] Watering : {plant['plantType']} on pin {plant['pinId']} with volume: {volume}")
    watering_scheduler.enter(INTERVAL_WATERING, 1, watering_loop, (sc,))


def server_checking_loop(sc):
    global plants
    global plant_schedules
    try:
        plants = plants_response()
    except :
        pass
    print(f"[{datetime.utcnow().strftime('%Y-%m-%dT%H:%M:00.000Z')}] Checking : received {len(plants)} plants")

    five_mins_ago = datetime.now() - timedelta(minutes=5)
    for plant in plants:
        try:
            plant_schedules[plant['id']] = schedules_response(plant["pinId"], five_mins_ago, 60)
        except:
            continue
    server_scheduler.enter(INTERVAL_SERVER, 1, server_checking_loop, (sc,))


def global_scheduling_loop(sc):
    server_scheduler.run(False)
    watering_scheduler.run(False)
    time.sleep(INTERVAL_GLOBAL)
    global_scheduler.enter(0, 1, global_scheduling_loop, (sc,))


#  =================
#  Driver function
#  =================
def unsafe_run_sync():
    server_scheduler.enter(0, 1, server_checking_loop, (server_scheduler,))
    watering_scheduler.enter(0, 1, watering_loop, (watering_scheduler,))
    global_scheduler.enter(0, 1, global_scheduling_loop, (global_scheduler,))
    global_scheduler.run(False)


if __name__ == "__main__":
    unsafe_run_sync()
