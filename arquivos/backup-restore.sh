#!/bin/bash
#BACKUP
docker exec contabilidade-db /usr/bin/mysqldump -u root --password=example --no-tablespaces contabilidade-dev > backup-$1.sql

#RESTORE
docker exec -i contabilidade-db mysql -u root --password=example contabilidade-dev < backup-$1.sql
