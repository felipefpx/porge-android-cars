#!/bin/bash
# Turn off animations
adb shell settings put global window_animation_scale 0 &
adb shell settings put global transition_animation_scale 0 &
adb shell settings put global animator_duration_scale 0 &

clear
./gradlew clean mergedJacocoReport
./gradlew jacocoFullReport

REPORT_PATH="file://$(pwd)/build/reports/jacoco/jacocoFullReport/html/index.html"

# Copy path to clipboard
echo ${REPORT_PATH} | pbcopy

echo "Report available at:"
echo ${REPORT_PATH}

echo "Report file path copied to clipboard. You can paste into your favorite browser. :)"