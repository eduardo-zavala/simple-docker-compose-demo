#!/bin/sh

MYSQL_HOST=${MYSQL_HOST:-mysql}
MYSQL_PORT=${MYSQL_PORT:-3306}
MYSQL_USER=${MYSQL_USER:-root}
MYSQL_PASSWORD=${MYSQL_PASSWORD:-alterego}

echo "Waiting for MySQL to be ready on $MYSQL_HOST:$MYSQL_PORT as $MYSQL_USER..."

until mysql -h"$MYSQL_HOST" -P"$MYSQL_PORT" -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "SELECT 1;" >/dev/null 2>&1; do
  echo "MySQL is unavailable - sleeping"
  sleep 2
done

echo "✅ MySQL is ready - waiting a few seconds more for TCP socket..."
sleep 5

echo "🚀 Launching Spring Boot"
exec java -jar app.jar

