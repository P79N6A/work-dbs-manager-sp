/***************************************************************************/
DROP INDEX IDX_READYITEMS_ID_CREATETIME ON V_PTReadyItems;
CREATE INDEX IDX_READYITEMS_ID_CREATETIME ON V_PTReadyItems (PackageID,CreateTime); 

/***************************************************************************/
DROP INDEX IDX_PTDELIVERHISTORY_STOREDDATE ON PTDeliverHistory;
CREATE INDEX IDX_PTDELIVERHISTORY_STOREDDATE ON PTDeliverHistory (StoredDate); 

DROP INDEX IDX_PTDELIVERHISTORY_MOBILE ON PTDeliverHistory;
CREATE INDEX IDX_PTDELIVERHISTORY_MOBILE ON PTDeliverHistory (CustomerMobile); 

DROP INDEX IDX_PTITEMLIFECYCLE_PACKAGE ON PTItemLifeCycle;
CREATE INDEX IDX_PTITEMLIFECYCLE_PACKAGE ON PTItemLifeCycle (PackageID,LastModifyTime); 

DROP INDEX IDX_PTITEMLIFECYCLE_LASTMODIFYTIME ON PTItemLifeCycle;
CREATE INDEX IDX_PTITEMLIFECYCLE_LASTMODIFYTIME ON PTItemLifeCycle (LastModifyTime); 

/***************************************************************************/
DROP INDEX IDX_PTINBOXPACKAGE_STOREDDATE ON PTInBoxPackage;
CREATE INDEX IDX_PTINBOXPACKAGE_STOREDDATE ON PTInBoxPackage (StoredDate); 

/***************************************************************************/
DROP INDEX IDX_PTINBOXPACKAGE_POSTMANID ON PTInBoxPackage;
CREATE INDEX IDX_PTINBOXPACKAGE_POSTMANID ON PTInBoxPackage (PostmanID,TerminalNo); 

/***************************************************************************/
DROP INDEX IDX_PTINBOXPACKAGE_OPENBOXKEY ON PTInBoxPackage;
CREATE INDEX IDX_PTINBOXPACKAGE_OPENBOXKEY ON PTInBoxPackage (OpenBoxKey); 


/***************************************************************************/
DROP INDEX IDX_RMOPENBOXLOG_PACKAGEID ON RMOpenBoxLog;
CREATE INDEX IDX_RMOPENBOXLOG_PACKAGEID ON RMOpenBoxLog (PackageID,CustomerMobile); 

/***************************************************************************/
DROP INDEX IDX_RMAPPEALLOG_APPEALTIME ON RMOpenBoxLog;
CREATE INDEX IDX_RMAPPEALLOG_APPEALTIME ON RMAppealLog(AppealTime DESC); 


/***************************************************************************/
DROP INDEX IDX_MBREMINDERWATER_STOREDDATE ON MBReminderWater;
CREATE INDEX IDX_MBREMINDERWATER_STOREDDATE ON MBReminderWater (StoredDate); 

DROP INDEX IDX_MBREMINDERWATER_MOBILE ON MBReminderWater;
CREATE INDEX IDX_MBREMINDERWATER_MOBILE ON MBReminderWater (CustomerMobile); 

DROP INDEX IDX_MBPWDSHORTMSG_STOREDDATE ON MBPwdShortMsg;
CREATE INDEX IDX_MBPWDSHORTMSG_STOREDDATE ON MBPwdShortMsg (StoredDate); 

DROP INDEX IDX_MBPWDSHORTMSG_MOBILE ON MBPwdShortMsg;
CREATE INDEX IDX_MBPWDSHORTMSG_MOBILE ON MBPwdShortMsg (CustomerMobile);

DROP INDEX IDX_MBPWDSHORTMSG_PACKAGEID ON MBPwdShortMsg;
CREATE INDEX IDX_MBPWDSHORTMSG_PACKAGEID ON MBPwdShortMsg (PackageID,TerminalNo,StoredTime); 
