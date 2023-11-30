#!/bin/bash

# 可运行
exec mvn -T 1C clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=false -Dmaven.compile.fork=true
#exec mvn -T 1C clean source:jar javadoc:javadoc install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dmaven.compile.fork=true
