#!/usr/bin/env bash
set -e
rm -rf out
mkdir -p out
javac -encoding UTF-8 -d out src/com/dealmate/DealMateApplication.java src/com/dealmate/controller/*.java src/com/dealmate/model/*.java src/com/dealmate/service/*.java src/com/dealmate/web/*.java
jar --create --file dealmate.jar --main-class com.dealmate.DealMateApplication -C out .
echo "Build complete: dealmate.jar"
