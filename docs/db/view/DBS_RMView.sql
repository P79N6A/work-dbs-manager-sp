
/* ===================================================================
*  远程求助日志
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_ApplealLog')
DROP VIEW V_ApplealLog;
GO
CREATE VIEW  V_ApplealLog AS
SELECT
	a.TerminalNo,
	b.TerminalName,
	b.MaintMobile,
	b.MaintEmail,
	a.BoxNo,
	c.BoxStatus,
	d.DictName AS BoxStatusName,
	CASE WHEN c.BoxStatus IN('2','3') THEN '1' ELSE '0' END AS FaultStatus,
	a.PackageID,
	a.Mobile AS CustomerMobile,
	a.AppealUser,
	a.AppealType,
	e.DictName AS AppealTypeName,
	a.AppealNo,
	a.AppealTime,
	a.AppealStatus,
	f.DictName AS AppealStatusName,
	a.OpenBoxKey,
	a.AppealContent,
	a.FaultReason
FROM RMAppealLog a,TBTerminal b,TBServerBox c,PADictionary d,PADictionary e,PADictionary f
WHERE b.TerminalNo = a.TerminalNo
AND c.TerminalNo = a.TerminalNo
AND c.BoxNo = a.BoxNo
AND d.DictID = c.BoxStatus
AND d.DictTypeID = 21011
AND e.DictID = a.AppealType
AND e.DictTypeID = 55001
AND f.DictID = a.AppealStatus
AND f.DictTypeID = 55011;
GO

