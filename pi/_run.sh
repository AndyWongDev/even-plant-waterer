#!/bin/bash

# Pull in python dependencies
[ -d env ] && rm -r env
python3 -m venv env
. env/bin/activate
pip install -r requirements.dev.txt
deactivate