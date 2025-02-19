#!/bin/bash

set -e
# Compile the OberonX System
echo "Compiling: OberonX"
time ant >>/dev/null
echo "OberonX: Done"

echo "Compiling: Apps"
time ./compile_apps.sh
echo "Apps: Done"
