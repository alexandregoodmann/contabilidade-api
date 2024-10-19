#!/bin/bash
docker exec mysql-db /usr/bin/mysqldump -u root --password=example --no-tablespaces contabilidade-prod > backup-46.sql
docker exec -i mysql-db mysql -u root --password=example contabilidade-dev < backup-45.sql
