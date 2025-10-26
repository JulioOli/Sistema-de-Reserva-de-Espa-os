#!/bin/bash

# Script para executar diretamente a interface gráfica
# Sem seletor - vai direto para a GUI

# Verificar se o projeto foi compilado
if [ ! -d "bin" ]; then
    echo "Projeto não compilado. Execute ./compilar.sh primeiro."
    exit 1
fi

echo "╔════════════════════════════════════════════════════════╗"
echo "║           Interface Gráfica - Reserva Espaços          ║"
echo "╚════════════════════════════════════════════════════════╝"
echo ""

# Executa diretamente a interface gráfica
java -cp bin reserva.LauncherSimples --gui
