@echo off

rd /s /q "out/yee"

setlocal enableextensions
md out\yee
endlocal

xcopy src\main\java out\yee /E /K > nul

del /S out\yee\*.java > nul

xcopy src\main\resources out\yee /E /K > nul
xcopy lib out\yee /E /K > nul

xcopy users.txt out\yee /K > nul
java -jar lib\aspectj\aspectjtools.jar -d out\yee -source 1.8 -sourceroots src\main\java\ -cp "lib/jfoenix-8.0.1.jar;lib/aspectj/aspectjrt.jar;out/yee/"