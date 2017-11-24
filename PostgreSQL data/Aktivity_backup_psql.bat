"D:/Programy/PostgreSQL/9.4/bin\pg_dump.exe" --host localhost --port 5432 --username "postgres" --no-password  --format plain --create --clean --section pre-data --section data --section post-data --inserts --column-inserts --verbose --file "D:\sosyn\Desktop\aktivity\aktivitySQL.backup" "aktivity" > "d:\sosyn\Desktop\Aktivity\Aktivity_backup_psql.log"

PAUSE