# Library System Cleanup Script
# Removes all .class files and organizes them

Write-Host "🧹 Cleaning up Java class files..." -ForegroundColor Green

# Go to lims directory
Set-Location "$PSScriptRoot\lims"

# Option 1: Delete all .class files
$classFiles = Get-ChildItem -Filter "*.class" -ErrorAction SilentlyContinue
if ($classFiles) {
    Write-Host "Found $($classFiles.Count) .class files to delete..." -ForegroundColor Yellow
    $classFiles | Remove-Item -Force
    Write-Host "✅ All .class files deleted!" -ForegroundColor Green
} else {
    Write-Host "✅ No .class files found!" -ForegroundColor Green
}

# Option 2: Move to class_files folder (uncomment if you prefer this)
# if (!(Test-Path "class_files")) {
#     New-Item -ItemType Directory -Name "class_files" | Out-Null
# }
# Get-ChildItem -Filter "*.class" -ErrorAction SilentlyContinue | Move-Item -Destination "class_files\"
# Write-Host "✅ .class files moved to class_files folder!" -ForegroundColor Green

Write-Host ""
Write-Host "🎉 Cleanup complete!" -ForegroundColor Cyan
Write-Host "Your directory is now clean!" -ForegroundColor Cyan
