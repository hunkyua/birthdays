#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp target/birthdays.jar \
    oktoserv@91.200.232.73:/home/ubuntu

echo "Restart server..."

ssh oktoserv@91.200.232.73 << EOF

pgrep java | xargs kill -9
nohup java -jar birthdays.jar > log.txt &

EOF

echo 'Bye'