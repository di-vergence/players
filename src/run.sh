#!/bin/bash

while [ "$end" != "true" ]
do
  echo "Type -t for programm in one process or -p for programm with 2 processes"
  read INPUT_STRING
  if [ "$INPUT_STRING" == "-t" ]; then
      echo "thread"
      cd ../ ; mvn package
      java -cp target/players-1.0-SNAPSHOT.jar implementation.thread.Main
      end="true"
    elif [ "$INPUT_STRING" == "-p" ]; then
      echo "processes"
      cd ../; mvn package;
      java -cp target/players-1.0-SNAPSHOT.jar implementation.process.ListnerServer 8080 &
      java -cp target/players-1.0-SNAPSHOT.jar implementation.process.Initiator 8080 &
      end="true"
    else
      echo "$INPUT_STRING doesn't support"
  fi
done