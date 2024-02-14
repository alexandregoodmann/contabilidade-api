#!/bin/bash
docker exec contabilidade-db /usr/bin/mysqldump -u root --password=example --no-tablespaces contabilidade-prod > backup-$1.sql
docker exec -i contabilidade-db mysql -u root --password=example contabilidade-dev < backup-$1.sql
