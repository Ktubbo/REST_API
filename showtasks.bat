call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openbrowser
echo Gradle cannot build
goto fail

:openbrowser
start "C:\Program Files\Mozilla Firefox\firefox.exe" http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo Cannot open browser
goto fail

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.
