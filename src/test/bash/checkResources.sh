#!/bin/bash
set -euo pipefail

JAR=${1:?Must specify path to built jblst jar file}

CONTENTS=`jar tvf ${JAR}`

EXPECTED="Linux/aarch64/libblst.so
Linux/amd64/libblst.so
Mac/aarch64/libblst.dylib
Mac/x86_64/libblst.dylib
Windows/amd64/blst.dll"

EXIT_CODE=0


for LIB in $EXPECTED
do
  echo -n "Checking for $LIB: "
  if [[ "$CONTENTS" == *"$LIB"* ]]
  then
    echo "Present"
  else
    echo "Missing!"
    EXIT_CODE=1
  fi
done

exit $EXIT_CODE