#!/bin/sh

function runLintCheck() {
  ./gradlew lint 2> /dev/null | grep -E "[1-9]\d? issues found"
  if [ $? == 0 ]
  then
      return 1
  else
      return 0
  fi
}

runLintCheck
