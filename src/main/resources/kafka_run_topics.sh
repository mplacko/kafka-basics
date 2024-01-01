#!/bin/sh
# ------------------------------------------------------------------------------
# --- registering trap for errors during script execution
on_error() {
  printf "\n\nAn error occurred!\n"
  exit 1
}
trap on_error ERR
java -jar kafka-basics-1.0-jar-with-dependencies.jar
exit 0