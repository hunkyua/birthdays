#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp target/birthdays.jar \
    voserv@91.200.232.73:/home/voserv

echo "Restart server..."

ssh voserv@91.200.232.73 << EOF

pgrep java | xargs kill -9
nohup java -jar birthdays.jar > log.txt &

EOF

echo 'Bye'