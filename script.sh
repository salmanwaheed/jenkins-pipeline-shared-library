#!/bin/bash

_GET_JAVA_PATH=$(readlink -f /usr/bin/javac | sed "s:/bin/javac::")

JAVA_HOME=$_GET_JAVA_PATH groovy -cp ./src ./run.groovy
