#!/bin/sh
# ------------------------------------------------------------------------------
# --- registering trap for errors during script execution
on_error() {
  printf "\n\nAn error occurred!\n"
  exit 1
}
trap on_error ERR
java -cp kafka-basics-1.0-jar-with-dependencies.jar eu.placko.examples.kafka.basics.SimpleConsumer
exit 0