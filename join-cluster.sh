#!/bin/bash

echo 'joining cluster'
rabbitmqctl stop_app
rabbitmqctl join_cluster rabbit@rabbitmq-1
rabbitmqctl start_app
echo 'joined cluster'
