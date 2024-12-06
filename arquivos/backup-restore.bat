@echo off
call docker exec mysql-db /usr/bin/mysqldump -u root --password=example --no-tablespaces contabilidade-prod > backup-49.sql
call docker exec -i mysql-db mysql -u root --password=example contabilidade-dev < backup-49.sql