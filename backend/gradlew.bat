@ECHO OFF
SETLOCAL

SET DIRNAME=%~dp0
IF "%DIRNAME%"=="" SET DIRNAME=.

SET CLASSPATH=%DIRNAME%\gradle\wrapper\gradle-wrapper-main-8.12.1.jar;%DIRNAME%\gradle\wrapper\gradle-wrapper-shared-8.12.1.jar

IF DEFINED JAVA_HOME (
  SET JAVA_EXE=%JAVA_HOME%\bin\java.exe
) ELSE (
  SET JAVA_EXE=java.exe
)

"%JAVA_EXE%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

ENDLOCAL
