#!/bin/bash

# 帮助信息功能
# Help info function
help(){
  echo -e '\033[34m'
  echo "--------------------------------------------------------------------------"
  echo ""
  echo "usage: ./kuugatool.sh [install | doc | pack]"
  echo ""
  echo "- install    Install kuugatool to your local Maven repository."
  echo "             (将kuugatool安装到本地Maven存储库)"
  echo "- doc        Generate Java doc api for kuugatool, you can see it in target dir"
  echo "             (为kuugatool生成Java文档api，您可以在目标目录中看到它)"
  echo "- pack       Make jar package by Maven"
  echo "             (通过Maven制作jar包)"
  echo ""
  echo "--------------------------------------------------------------------------"
  echo -e '\033[0m'
}


# Start
./bin/logo.sh
case "$1" in
  # 安装
  'install')
    bin/install.sh
	;;
  # 生成文档
  'doc')
    bin/javadoc.sh
	;;
  # 打包到maven仓库
  'pack')
    bin/package.sh
	;;
  *)
    help
esac
