#!/bin/bash

# Script de execução do Sistema de Reserva de Espaços
# RF01 e RF02 implementados

# Verifica se o projeto está compilado
if [ ! -d "bin" ]; then
    echo "Projeto não compilado. Execute ./compilar.sh primeiro."
    exit 1
fi

echo "╔════════════════════════════════════════════════════════╗"
echo "║        Executando Sistema de Reserva de Espaços        ║"
echo "╚════════════════════════════════════════════════════════╝"
echo ""

# Executa o sistema (com seletor de modo)
java -cp bin reserva.LauncherSimples

