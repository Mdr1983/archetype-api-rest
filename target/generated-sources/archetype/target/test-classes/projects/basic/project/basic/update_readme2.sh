#!/bin/bash

# Actualiza el README con el contenido del archivo dependencies.txt
#sed -i 's/\(## Dependencias\)\n(.*)/\1\n\`\`\`\n$(cat dependencies.txt)\n\`\`\`/g' README.md

# Actualiza el README con el contenido del archivo dependencies.txt
(Get-Content README.md) -replace '(?sm)(?<=\n## Dependencias\n).*', "`\`\`\n$(Get-Content dependencies.txt)\n\`\`\`" | Set-Content README.md