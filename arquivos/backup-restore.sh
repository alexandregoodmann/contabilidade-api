#!/bin/bash
docker exec my-mysql /usr/bin/mysqldump -u root --password=mysqlPW --no-tablespaces contabilidade-prod > backup-$1.sql
docker exec -i my-mysql mysql -u root --password=mysqlPW contabilidade-dev < backup-$1.sql
