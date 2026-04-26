#!/bin/bash
# ERP 数据库自动备份脚本
# 用法: ./backup_db.sh [保留天数默认30]
# crontab: 0 2 * * * /app/tools/backup_db.sh 30 >> /var/log/erp_backup.log 2>&1

set -e

DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-3306}"
DB_USER="${DB_USER:-root}"
DB_PASS="${DB_PASS:-erp_root_2026}"
DB_NAME="${DB_NAME:-ry_vue}"
BACKUP_DIR="${BACKUP_DIR:-./backups}"
RETENTION_DAYS="${1:-30}"

mkdir -p "$BACKUP_DIR"

TIMESTAMP=$(date +%Y%m%d_%H%M%S)
FILE="${BACKUP_DIR}/${DB_NAME}_${TIMESTAMP}.sql.gz"

echo "[$(date)] Starting backup: $FILE"
mysqldump -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASS" \
  --single-transaction --routines --triggers --default-character-set=utf8mb4 \
  "$DB_NAME" | gzip > "$FILE"

echo "[$(date)] Backup complete: $(du -h "$FILE" | cut -f1)"

# Clean old backups
DELETED=$(find "$BACKUP_DIR" -name "${DB_NAME}_*.sql.gz" -mtime +"$RETENTION_DAYS" -delete -print | wc -l)
echo "[$(date)] Cleaned $DELETED old backups (retention: ${RETENTION_DAYS}d)"
