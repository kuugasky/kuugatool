#!/bin/bash

#--------------------------------------
# Check dependency, thanks to t-io
#--------------------------------------
echo -e '\033[31m'
echo '# 显示项目中依赖的更新版本信息'
echo ''
echo '# 运行这个命令时，Maven 会分析你项目中的所有依赖，并检查是否有可用的更新版本。然后，它会列出当前使用的依赖版本以及可用的最新版本，让你能够了解哪些依赖可以进行更新。'
echo ''
echo '# 这个命令对于管理项目的依赖版本非常有用，可以帮助你及时发现并更新项目中使用的依赖到最新版本，以确保项目的安全性和性能。'
echo -e '\033[0m'

mvn versions:display-dependency-updates
