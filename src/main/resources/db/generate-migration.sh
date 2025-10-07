#!/bin/bash

# Script para gerar migrations Liquibase
# Uso: ./generate-migration.sh nome-da-migration

set -e

# Diretório fixo das migrations
MIGRATIONS_DIR="./migrations"

# Verifica se o nome da migration foi fornecido
if [ -z "$1" ]; then
    echo "Erro: Nome da migration não fornecido"
    echo "Uso: $0 <nome-da-migration>"
    exit 1
fi

MIGRATION_NAME=$1

# Gera o timestamp em milissegundos
TIMESTAMP=$(date +%s%3N)

# Nome do arquivo
FILENAME="${TIMESTAMP}-${MIGRATION_NAME}.sql"
FILEPATH="${MIGRATIONS_DIR}/${FILENAME}"

# Cria o arquivo com o template
cat > "$FILEPATH" << EOF
--liquibase formatted sql
--changeset author:${TIMESTAMP}
EOF

echo "✅ Migration criada com sucesso!"
echo "📁 Arquivo: $FILEPATH"