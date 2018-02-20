cd ../%SERVICE_HOME%
./gradlew -x test check
if %errorlevel% neq 0 exit /b %errorlevel%
./gradlew runUnitTests
if %errorlevel% neq 0 exit /b %errorlevel%
./gradlew bootRun
