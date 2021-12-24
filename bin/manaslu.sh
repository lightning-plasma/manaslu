#!/bin/bash
set -eu

# JVM Options
JVM_OPTIONS="-Xmx512M -Xms256M"
# Java command
JAVA_CMD=/usr/bin/java
# main class
MAIN_CLASS=com.archetype.manaslu.ManasluApplicationKt
# Jar file path / setting your path
JAR_PATH="/path/to/manaslu-0.0.1-SNAPSHOT.jar"

# application setting / use if necessary
# spring profiles
# export SPRING_PROFILES_ACTIVE="dev"

# config
# export LOGGING_LEVEL_IO_GRPC=INFO
# export LOGGING_LEVEL_IO_NETTY=INFO
# export HOST="localhost"
# export PORT="6565"
# export ENABLE_SSL="false"
# export PARALLELS="3"
# export EXECUTION_TIME="10000"

EXEC_JAVA="${JAVA_CMD} ${JVM_OPTIONS} -jar ${JAR_PATH} ${MAIN_CLASS} $@"
eval ${EXEC_JAVA}
exit $?
