#!/bin/bash

#------------------------------------------------
# 升级版本，包括：
# 1. 升级pom.xml中的版本号
# 2. 替换README.md和docs中的版本号
#------------------------------------------------

if [ ! -n "$1" ]; then
        echo "ERROR: 新版本不存在，请指定参数1"
        exit
fi

# 获取原版本号
old_version=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

# 替换io.github.kuugasky所有模块pom.xml中的版本
mvn versions:set -DnewVersion=$1

# 不带-SNAPSHOT的版本号，用于替换其它地方
version=${1%-SNAPSHOT} $version

# 替换其它地方的版本
$(pwd)/bin/replaceVersion.sh "$version"

echo -e '\033[34m《所有模块版本号替换完成》\033[0m'
echo -e '\033[31m'
echo '================================================================'
echo '原来的版本号：' ${old_version//%}
echo '现在的版本号：' $1
echo '================================================================'
echo -e '\033[0m'

read -p "是否执行构建脚本? (y/n): " answer
if [ "$answer" = "y" ]; then
    echo "Executing the other script..."
    bash $(pwd)/bin/fast_install.sh
    echo -e '\033[34m[INFO] 项目构建完成\033[0m'
elif [ "$answer" = "n" ]; then
    echo "Execution cancelled."
else
    echo "Invalid input. Please enter 'y' or 'n'."
fi
