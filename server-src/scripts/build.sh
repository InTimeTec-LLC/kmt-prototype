#!/bin/bash
set -e
./gradlew -x test check
./gradlew -x test build
