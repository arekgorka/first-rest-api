call runcrud.bat
if "%ERRORLEVEL%" == "0" goto runwebbrowser
echo.
echo opening runcrud.bat has errors – breaking work
goto fail

:runwebbrowser
start chrome.exe http://localhost:8080/v1/tasks
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.