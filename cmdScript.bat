set /P command="What would you like to execute? Usage <command> <parameters> : "
echo.Called %command%

@echo off

%MVN_HOME%bin\mvn exec:java -Dexec.mainClass=ro.uaic.info.configuration.BatchStartPoint -Dexec.args=%command%

@echo on

echo.%command%
