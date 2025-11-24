@echo off
echo =========================================
echo   Library Management System - Web Demo
echo         Educational Java Project
echo =========================================
echo.
echo NOTE: This is a demonstration project for educational purposes.
echo All data is simulated and stored in memory only.
echo.
echo Since Maven is not installed, we'll use the console version:
echo.
cd /d "c:\Users\ADMIN\Library-System"
echo Starting Console Version...
echo.
echo Login Credentials:
echo Username: librarian
echo Password: admin123
echo.
java lims.Main
echo.
echo.
echo =========================================
echo For Web Interface, you would need:
echo 1. Install Maven (apache-maven.org)
echo 2. Run: mvn spring-boot:run
echo 3. Open: http://localhost:8080
echo =========================================
pause
