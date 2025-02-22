#!/bin/bash

mkdir -p apps
cd ./built-ins/

find . -maxdepth 1 -type d ! -name "." | while read dir; do
  echo "Compling: $dir"
  cd $dir
  # Compile the app
  ant >>/dev/null
  cp -r dist/*.jar dist/lib ../../apps
  cd ..
done

cd ..
