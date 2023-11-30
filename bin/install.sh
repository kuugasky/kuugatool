#!/bin/bash

# 完整构建，包括文档，单测
#e xec mvn -T 1C clean source:jar javadoc:javadoc install -Dmaven.test.skip=false -Dmaven.javadoc.skip=false

# exec mvn -T 1C clean source:jar javadoc:javadoc install -Dmaven.test.skip=false -Dmaven.javadoc.skip=true
# 跳过文档和单测
exec mvn -T 1C clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true

