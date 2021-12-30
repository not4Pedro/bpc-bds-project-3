pushd %~dp0
set today=%date:~10,4%%date:~4,2%%date:~7,2%
set PGPASSWORD=postgres
"C:\Program Files\PostgreSQL\14\bin\pg_dump.exe" -h localhost -U postgres -p 5432 bpc-db-projekt1 > D:/_VUT/2.rocnik/1.semester/bds/bpc-bds-project-3/backup/bckp_%today%.dump
popd