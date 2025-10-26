#!/bin/bash

# Script para executar diretamente o modo console
# Demonstração original dos RFs

# Verificar se o projeto foi compilado
if [ ! -d "bin" ]; then
    echo "Projeto não compilado. Execute ./compilar.sh primeiro."
    exit 1
fi

echo "╔════════════════════════════════════════════════════════╗"
echo "║            Modo Console - Reserva Espaços              ║"
echo "╚════════════════════════════════════════════════════════╝"
echo ""

# Executa diretamente o modo console
java -cp bin reserva.LauncherSimples --console
