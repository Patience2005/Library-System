@echo off
echo Cleaning up Java class files...

REM Delete all .class files in lims directory
cd /d "%~dp0lims"
del *.class 2>nul

REM Also clean class_files directory if it exists
if exist "class_files" (
    del class_files\*.class 2>nul
)

echo Cleanup complete!
echo.
echo All .class files have been removed.
pause
