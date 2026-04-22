#!/bin/sh
# 后端容器启动脚本
# 检查必要环境变量并以合理默认值启动

set -e

# ── 关键变量校验（生产必须覆盖） ──────────────────────────
warn_if_default() {
    var_name="$1"
    var_val=$(eval "echo \$$var_name")
    default_val="$2"
    if [ "$var_val" = "$default_val" ]; then
        echo "[WARN] $var_name 使用默认值，生产环境请通过环境变量覆盖！"
    fi
}

warn_if_default "JWT_SECRET"     "abcdefghijklmnopqrstuvwxyz"
warn_if_default "DB_PASSWORD"    ""
warn_if_default "DRUID_PASSWORD" "ruoyi@druid2026"

# ── ip2region 数据库检查 ───────────────────────────────────
XDB_PATH="${UPLOAD_PATH:-/app/uploadPath}/ip2region.xdb"
if [ ! -f "$XDB_PATH" ]; then
    echo "[WARN] ip2region.xdb 未找到：$XDB_PATH"
    echo "[INFO] 下载：https://github.com/lionsoul2014/ip2region/raw/master/data/ip2region.xdb"
    echo "[INFO] 放到宿主机挂载目录并重启容器"
fi

# ── JVM 参数（根据容器内存动态调整）────────────────────────
JAVA_OPTS="${JAVA_OPTS:--Xms512m -Xmx1g -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0}"

echo "[INFO] 启动 ruoyi-admin..."
echo "[INFO] JAVA_OPTS=$JAVA_OPTS"
echo "[INFO] DB_URL=${DB_URL:-localhost:3306/ry_vue}"

exec java $JAVA_OPTS -jar app.jar
