@echo off

rem purge-all.cmd
rem 
rem Copyright (C) 2014 Universidad de Sevilla
rem 
rem The use of this project is hereby constrained to the conditions of the 
rem TDG Licence, a copy of which you may download from 
rem http://www.tdg-seville.info/License.html

echo Purging workspace "%CD%"...

if not exist .metadata goto no-workspace

echo %DATE% - %TIME% > purge-workspace.log
echo. >> purge-workspace.log
echo Purging workspace .metadata ... >> purge-workspace.log
echo. >> purge-workspace.log

del .metadata\.plugins\org.eclipse.m2e.core\nexus /s /f /q 1>> purge-workspace.log 2>&1 
rmdir .metadata\.plugins\org.eclipse.m2e.core\nexus /s /q 1>> purge-workspace.log 2>&1 
del .metadata\.plugins\org.eclipse.wst.server.core\tmp0 /s /f /q 1>> purge-workspace.log 2>&1 
rmdir .metadata\.plugins\org.eclipse.wst.server.core\tmp0 /s /q 1>> purge-workspace.log 2>&1 
del .metadata\.plugins\org.eclipse.wst.server.core\tmp1 /s /f /q 1>> purge-workspace.log 2>&1 
rmdir .metadata\.plugins\org.eclipse.wst.server.core\tmp1 /s /q 1>> purge-workspace.log 2>&1 
del .metadata\.plugins\org.eclipse.jdt.core /s /f /q 1>> purge-workspace.log 2>&1 
rmdir .metadata\.plugins\org.eclipse.jdt.core /s /q 1>> purge-workspace.log 2>&1 

echo. >> purge-workspace.log
echo Purging project targets ... >> purge-workspace.log
echo. >> purge-workspace.log

for /d %%p in (*) do (
	if exist "%%p"\target ( 
        echo %%p 1>> purge-workspace.log 2>&1
		del /s /f /q "%%p"\target 1>> purge-workspace.log 2>&1
		rmdir /s /q "%%p"\target 1>> purge-workspace.log 2>&1 
	)
)

echo. >> purge-workspace.log
echo Removing .bak and .log files ... >> purge-workspace.log
echo. >> purge-workspace.log

del *.bak /s /q 1>> purge-workspace.log 2>&1 
rem del *.log /s /q 1>> purge-workspace.log 2>&1 

goto end


:no-workspace

    echo.
    echo This utility was designed to run in an Eclipse workspace
    echo folder.  I didn't find the .metadata configuration folder.
    echo Please, make sure you run this utility with an Eclipse
    echo workspace folder.
    echo.

    goto end


:end

