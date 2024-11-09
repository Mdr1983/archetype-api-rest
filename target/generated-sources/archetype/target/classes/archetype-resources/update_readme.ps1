# Ruta al README y al archivo de dependencias
$readmePath = "README.md"
$dependenciesPath = "dependencies.txt"

# Leer el contenido del README y del archivo de dependencias
$readmeContent = Get-Content $readmePath
$dependenciesContent = Get-Content $dependenciesPath

# Encontrar el inicio y el fin de la sección de dependencias en el README
$startMarker = "<!-- DEPENDENCIES_START -->"
$endMarker = "<!-- DEPENDENCIES_END -->"

# Crear una nueva sección con las dependencias
$newContent = @()
$insideSection = $false
foreach ($line in $readmeContent) {
    if ($line -eq $startMarker) {
        $insideSection = $true
        $newContent += $line
        $newContent += $dependenciesContent
    } elseif ($line -eq $endMarker) {
        $insideSection = $false
    }
    if (-not $insideSection) {
        $newContent += $line
    }
}

# Escribir el nuevo contenido en el README
$newContent | Set-Content $readmePath
