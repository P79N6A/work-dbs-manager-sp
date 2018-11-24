/*
CREATE DATABASE IF NOT EXISTS dbs_server_saudi  DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
GRANT ALL ON dbs_server_saudi_test.* TO 'dbsadmin'@'localhost' IDENTIFIED BY 'dbsadmin_dcdz';
*/
USE master
GO 
IF NOT EXISTS(SELECT 1 FROM sysdatabases WHERE NAME=N'dbs_server_saudi') 
CREATE DATABASE dbs_server_saudi 
ON 
PRIMARY --创建主数据库文件 
( 
NAME='dbs_server_saudi', 
FILENAME='E:\Databases\dbs_server_saudi.dbf', 
SIZE=5MB, 
MaxSize=20MB, 
FileGrowth=1MB 
) 
LOG ON --创建日志文件 
( 
NAME='dbs_server_saudiLog', 
FileName='E:\Databases\dbs_server_saudi.ldf', 
Size=2MB, 
MaxSize=20MB, 
FileGrowth=1MB 
) 
GO 

--drop user dbsadmin;
--drop login dbsadmin;
IF NOT EXISTS(
select 1 from sysusers 
where gid = 0
 and hasdbaccess = 1
 and islogin = 1
 and issqluser = 1
 and name='dbsadmin'
) 
BEGIN
create login dbsadmin with password='dbsadmin_dcdz', default_database=dbs_server_saudi;
create user dbsadmin for login dbsadmin with default_schema=dbo;
END
exec sp_addrolemember 'db_owner', 'dbsadmin'
GO