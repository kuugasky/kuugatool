#!/bin/bash

echo -e '\033[31m'
echo '【开始】清理&构建项目 - kuugatool-parent'
echo -e '\033[0m'

sleep 1

mvn clean install -T 1C -Dmaven.javadoc.skip=true -Dmaven.test.skip -f pom.xml

echo -e '\033[31m'
echo '【结束】清理&构建项目 - kuugatool-parent'
echo -e '\033[0m'
sleep 1

cd kuugatool-all

echo -e '\033[31m'
echo '【开始】清理&构建项目 - kuugatool-all'
echo -e '\033[0m'

sleep 1

 mvn clean install -Dmaven.javadoc.skip=true -Dmaven.test.skip -f pom.xml

echo -e '\033[31m'
echo '【结束】清理&构建项目 - kuugatool-all'
echo -e '\033[0m'