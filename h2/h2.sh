#!/usr/bin/env bash
dir=$(dirname "$0")
java -cp "$dir/h2-2.1.214.jar:$H2DRIVERS:$CLASSPATH" org.h2.tools.Console "$@"
