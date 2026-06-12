@echo off
chcp 65001 > nul
if exist out rmdir /s /q out
mkdir out
javac -encoding UTF-8 -d out src\com\dealmate\DealMateApplication.java src\com\dealmate\controller\*.java src\com\dealmate\model\*.java src\com\dealmate\service\*.java src\com\dealmate\web\*.java
if errorlevel 1 (
  echo Compile failed.
  exit /b 1
)
jar --create --file dealmate.jar --main-class com.dealmate.DealMateApplication -C out .
echo Build complete: dealmate.jar
