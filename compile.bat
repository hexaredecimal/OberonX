@echo off
setlocal enabledelayedexpansion

echo Compiling: OberonX
time /t
ant >NUL
echo OberonX: Done

echo Compiling: Apps
time /t
call compile_apps.bat
echo Apps: Done

:: compile_apps.bat
echo off
cd built-ins
for /d %%F in (*) do (
    echo Compiling: %%F
    cd %%F
    ant >NUL
    xcopy /Y dist\*.jar dist\lib ..\..\apps
    cd ..
)
cd ..

