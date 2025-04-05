@echo off
echo Setting up Java environment...
set JAVA_HOME=C:\Program Files\Java\jdk-24
set PATH=%JAVA_HOME%\bin;%PATH%

echo Creating directories...
mkdir lib 2>nul
mkdir target 2>nul
mkdir src\main\java\com\stockmarket\gui 2>nul
mkdir src\main\java\com\stockmarket\model 2>nul
mkdir src\main\java\com\stockmarket\service 2>nul

echo Downloading required dependencies...
powershell -Command "& {Invoke-WebRequest -Uri 'https://search.maven.org/remotecontent?filepath=com/google/code/gson/gson/2.8.9/gson-2.8.9.jar' -OutFile 'lib/gson-2.8.9.jar'}"

echo Compiling Java files...
javac -cp "lib/*" -d target src\main\java\com\stockmarket\*.java src\main\java\com\stockmarket\gui\*.java src\main\java\com\stockmarket\model\*.java src\main\java\com\stockmarket\service\*.java

echo Running the application...
java -cp "target;lib/*" com.stockmarket.StockMarketSimulator 