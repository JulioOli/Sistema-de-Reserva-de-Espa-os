#!/bin/bash

# Script de compilação do Sistema de Reserva de Espaços
# RF01 e RF02 implementados

echo "╔════════════════════════════════════════════════════════╗"
echo "║        Compilando Sistema de Reserva de Espaços        ║"
echo "╚════════════════════════════════════════════════════════╝"
echo ""

# Remove diretório bin anterior
if [ -d "bin" ]; then
    echo "Removendo compilação anterior..."
    rm -rf bin
fi

# Cria diretório bin
echo "Criando diretório bin..."
mkdir -p bin

# Compila todos os arquivos Java
echo "Compilando arquivos Java..."
find src/main/java -name "*.java" > sources.txt
javac -d bin @sources.txt

# Verifica se a compilação foi bem-sucedida
if [ $? -eq 0 ]; then
    echo ""
    echo "✓ Compilação concluída com sucesso!"
    echo ""
    echo "Para executar o sistema, use:"
    echo "  ./executar.sh"
    echo "  ou"
    echo "  java -cp bin reserva.Main"
else
    echo ""
    echo "✗ Erro na compilação!"
    exit 1
fi

# Remove arquivo temporário
rm -f sources.txt

