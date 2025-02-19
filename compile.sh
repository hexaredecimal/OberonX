#!/bin/bash

set -e
# Compile the OberonX System
ant
./compile_apps.sh
