databasePWD:SageCRM72   kv65ema6bw.database.windows.net
elaammal@112233




证书解压Password : SP_cert

https://servicesprd.sp.com.sa/ParcelStation/ParcelStationService.svc
User name: extrparcelstation
Password : 123456

https://185.12.166.63/ItemsService/SubmitItemsService.svc 
User name: intrpos
Password: 123456
https://servicesstg.sp.com.sa/ItemsService/SubmitItemsService.svc



C:\Windows\System32\drivers\etc\hosts
#185.12.166.67      servicesprd.sp.com.sa
#185.12.166.63      servicesprd.sp.com.sa
185.12.166.86      servicesprd.sp.com.sa
185.12.166.63      servicesstg.sp.com.sa

https://185.12.166.67/ParcelStation/ParcelStationService.svc    -- Unavailable 2017-07-06
https://185.12.166.63/ParcelStation/ParcelStationService.svc    -- Test environment
https://185.12.166.86/ParcelStation/ParcelStationService.svc    -- Production environment
https://185.12.166.63/ItemsService/SubmitItemsService.svc       -- Test environment



1、本地安装证书：
keytool -list -keystore "C:\Program Files\Java\jdk1.7.0_79\jre\lib\security\cacerts" | findstr /i SubmitItemsService 
keytool -delete -alias SaudiPostRootService    -keystore "C:\Program Files\Java\jdk1.7.0_79\jre\lib\security\cacerts"
keytool -import -keystore "C:\Program Files\Java\jdk1.7.0_79\jre\lib\security\cacerts" -storepass changeit -keypass changeit -alias SubmitItemsService -file D:\RD\01.4SoftwareDept\01.4.1ProductDevelopment\04_BGG\80_沙特邮政\B_相关资料\servicesstg.sp.com.sa.cer
keytool -import -keystore "C:\Program Files\Java\jdk1.7.0_79\jre\lib\security\cacerts" -storepass changeit -keypass changeit -alias ParcelStationService  -file D:\RD\01.4SoftwareDept\01.4.1ProductDevelopment\04_BGG\80_沙特邮政\B_相关资料\servicesprd.sp.com.sa.cer
keytool -import -keystore "C:\Program Files\Java\jdk1.7.0_79\jre\lib\security\cacerts" -storepass changeit -keypass changeit -alias SaudiPostRootService  -file D:\RD\01.4SoftwareDept\01.4.1ProductDevelopment\04_BGG\80_沙特邮政\B_相关资料\RootCert_SP.cer


keytool -list -keystore "C:\Program Files\Java\jdk1.7.0_79\jre\lib\security\cacerts" | findstr /i Service 


2、服务端安装证书：
keytool -list -keystore "C:\Program Files\Java\jre7\lib\security\cacerts" | findstr /i Service 
keytool -delete -alias SubmitItemsService    -keystore "C:\Program Files\Java\jre7\lib\security\cacerts"
keytool -import -keystore "C:\Program Files\Java\jre7/lib/security/cacerts" -storepass changeit -keypass changeit -alias SubmitItemsService -file  C:\elocker_server\sp_ppcserver_submitItems.cer

pause





