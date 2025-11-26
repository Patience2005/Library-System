# Add this to your PowerShell profile to permanently hide .class files
function Hide-ClassFiles {
    param(
        [string]$Path = "."
    )
    
    Get-ChildItem -Path $Path -Filter "*.class" -File | ForEach-Object {
        $_.Attributes += [System.IO.FileAttributes]::Hidden
    }
    
    Write-Host "Class files hidden in: $Path" -ForegroundColor Green
}

# Auto-hide class files in current directory
Hide-ClassFiles
