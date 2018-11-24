-- =============================================
-- Author:		<zhengxy e>
-- Create date: <2016-12-07>
-- Description:	<Description,,>
-- =============================================
if exists (select * from sysobjects where id = object_id(N'[dbs_stat_procedure]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop PROCEDURE dbs_stat_procedure
go
CREATE PROCEDURE dbs_stat_procedure
	-- Add the parameters for the stored procedure here
	@mode int
AS
BEGIN  
   DECLARE @printMsg VARCHAR(100) 
   DECLARE @v_datetime VARCHAR(30);
   Select @v_datetime=CONVERT(varchar(100), GETDATE(), 21)
   --print @v_datetime
   set @printMsg = @mode
   print @printMsg
   set @printMsg = @v_datetime+N':dbs_stat_drop_order start'
   print @printMsg
   exec dbs_stat_drop_order
   set @printMsg = @v_datetime+N':dbs_stat_drop_order end'
   print @printMsg
END
GO
--exec dbs_stat_procedure 1
GO

if exists (select * from sysobjects where id = object_id(N'[dbs_stat_drop_order]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop PROCEDURE dbs_stat_drop_order
go
CREATE PROCEDURE dbs_stat_drop_order
	-- Add the parameters for the stored procedure here
AS
BEGIN  -- 投递数量统计
   DECLARE @v_TerminalNo VARCHAR(20) 
   DECLARE @v_OccurDate DATE;
   DECLARE @v_PostmanID VARCHAR(32);
   DECLARE @v_CompanyID VARCHAR(32);
   DECLARE @v_ZoneID VARCHAR(32);
   DECLARE @v_DropNum INT = 0;
   DECLARE @v_count INT;
   DECLARE @done INT = 0;   
   DECLARE @v_StatStartDate DATE;
   select @v_StatStartDate = DATEADD(day,-300,GETDATE())
   declare rset1 cursor for SELECT TerminalNo,OccurDate,PostmanID,CompanyID,ZoneID,COUNT(PackageID) AS DropNum  FROM _V_OrderStat0  WHERE OccurDate>@v_StatStartDate  GROUP BY TerminalNo,OccurDate,PostmanID,CompanyID,ZoneID;
   
   print 'stat start date:'
   print @v_StatStartDate
   --print @@fetch_status
   
   --打开游标 
	open rset1 
	FETCH next from rset1 INTO @v_TerminalNo, @v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID, @v_DropNum --获取一条记录
		
	while @@fetch_status=0 
	begin
	--读取游标 
		-- 插入或更新
		--print @@fetch_status
		SET @v_count =0 
		SELECT  @v_count=COUNT(*) FROM _QYDropOrderStat WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID
		if(@v_count>0)
			begin
			print 'UPDATE'
			UPDATE _QYDropOrderStat SET DropNum=@v_DropNum, LastModifyTime=GetDate() WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID;
			end
	    else
			begin
			print 'INSERT'
			INSERT INTO _QYDropOrderStat(TerminalNo,OccurDate,PostmanID,CompanyID,ZoneID,DropNum,ExpiredNum,InBoxNum,TakeOutNum,TakeBackNum,ManagerOutNum,CreateTime,LastModifyTime,InBoxNumLast,ExpiredNumLast) 
		      VALUES(@v_TerminalNo,@v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID,@v_DropNum,0,0,0,0,0,GetDate(),GetDate(),0,0)
			end
		--print @v_count
		--print @v_TerminalNo
		--print @v_OccurDate
		FETCH next from rset1 INTO @v_TerminalNo, @v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID, @v_DropNum --获取一条记录
	end
	close rset1 
	--摧毁游标 
	deallocate rset1
END
BEGIN  -- 在箱数量统计
   DECLARE @v_InBoxNum INT = 0;
   DECLARE @v_ExpiredNum INT = 0;
   declare rset1 cursor for SELECT TerminalNo,OccurDate,PostmanID,CompanyID,ZoneID,SUM(InBoxNum) AS InBoxNum,SUM(ExpiredNum) AS ExpiredNum  FROM _V_OrderStat1 GROUP BY TerminalNo,OccurDate,PostmanID,CompanyID,ZoneID;
   
   --print @@fetch_status
   
    update _QYDropOrderStat set InBoxNumLast =0,ExpiredNumLast =0;
   --打开游标 
	open rset1 
	FETCH next from rset1 INTO @v_TerminalNo, @v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID, @v_InBoxNum,@v_ExpiredNum --获取一条记录
		
	while @@fetch_status=0 
	begin
	--读取游标 
		-- 插入或更新
		--print @@fetch_status
		SET @v_count =0 
		SELECT  @v_count=COUNT(*) FROM _QYDropOrderStat WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID
		if(@v_count>0)
			begin
			print 'UPDATE'
			UPDATE _QYDropOrderStat SET InBoxNumLast=@v_InBoxNum,ExpiredNumLast=@v_ExpiredNum, LastModifyTime=GetDate() WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID;
			end
	    else
			begin
			print 'INSERT'
			INSERT INTO _QYDropOrderStat(TerminalNo,OccurDate,PostmanID,CompanyID,ZoneID,DropNum,ExpiredNum,InBoxNum,TakeOutNum,TakeBackNum,ManagerOutNum,CreateTime,LastModifyTime,InBoxNumLast,ExpiredNumLast) 
		      VALUES(@v_TerminalNo,@v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID,0,0,0,0,0,0,GetDate(),GetDate(),@v_InBoxNum,@v_ExpiredNum)
			end
		--print @v_count
		--print @v_TerminalNo
		--print @v_OccurDate
		FETCH next from rset1 INTO @v_TerminalNo, @v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID, @v_InBoxNum,@v_ExpiredNum --获取一条记录
	end
	close rset1 
	--摧毁游标 
	deallocate rset1
END
BEGIN  -- 取件数量统计
   DECLARE @v_TakeOutNum INT = 0;
   
   declare rset1 cursor for SELECT TerminalNo,StoredDate AS OccurDate,DropAgentID AS PostmanID,CompanyID,ZoneID,COUNT(PackageID) AS TakeOutNum  FROM PTDeliverHistory  WHERE StoredDate IS NOT NULL AND ItemStatus in('7') AND StoredDate>@v_StatStartDate  GROUP BY TerminalNo,StoredDate,DropAgentID,CompanyID,ZoneID;
   
   --打开游标 
	open rset1 
	FETCH next from rset1 INTO @v_TerminalNo, @v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID, @v_TakeOutNum --获取一条记录
	print 'Stat takeout:'
	--print @@fetch_status
	while @@fetch_status=0 
	begin
	--读取游标 
		-- 插入或更新
		--print @@fetch_status
		SET @v_count =0 
		SELECT  @v_count=COUNT(*) FROM _QYDropOrderStat WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID
		if(@v_count>0)
			begin
			print 'UPDATE' 
			print @v_TakeOutNum
			UPDATE _QYDropOrderStat SET TakeOutNum=@v_TakeOutNum, LastModifyTime=GetDate() WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID;
			end
	    else
			begin
			print 'INSERT' 
			print @v_TakeOutNum
			INSERT INTO _QYDropOrderStat(TerminalNo,OccurDate,PostmanID,CompanyID,ZoneID,DropNum,ExpiredNum,InBoxNum,TakeOutNum,TakeBackNum,ManagerOutNum,CreateTime,LastModifyTime,InBoxNumLast,ExpiredNumLast) 
		      VALUES(@v_TerminalNo,@v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID,0,0,0,@v_TakeOutNum,0,0,GetDate(),GetDate(),0,0)
			end
		--print @v_count
		--print @v_TerminalNo
		--print @v_OccurDate
		FETCH next from rset1 INTO @v_TerminalNo, @v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID, @v_TakeOutNum --获取一条记录
	end
	close rset1 
	--摧毁游标 
	deallocate rset1
END
BEGIN  -- 回收件数量统计
   DECLARE @v_TakeBackNum INT = 0;
   declare rset1 cursor for SELECT TerminalNo,StoredDate AS OccurDate,DropAgentID AS PostmanID,CompanyID,ZoneID,COUNT(PackageID)  AS TakeBackNum  FROM PTDeliverHistory  WHERE StoredDate IS NOT NULL AND ItemStatus not in('7') AND StoredDate>@v_StatStartDate  GROUP BY TerminalNo,StoredDate,DropAgentID,CompanyID,ZoneID;
   
   --打开游标 
	open rset1 
	FETCH next from rset1 INTO @v_TerminalNo, @v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID, @v_TakeBackNum --获取一条记录
	print 'Stat TakeBack:'
	--print @@fetch_status
	while @@fetch_status=0 
	begin
	--读取游标 
		-- 插入或更新
		--print @@fetch_status
		SET @v_count =0 
		SELECT  @v_count=COUNT(*) FROM _QYDropOrderStat WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID
		if(@v_count>0)
			begin
			print 'UPDATE' 
			print @v_TakeOutNum
			UPDATE _QYDropOrderStat SET TakeBackNum=@v_TakeBackNum, LastModifyTime=GetDate() WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID;
			end
	    else
			begin
			print 'INSERT' 
			print @v_TakeOutNum
			INSERT INTO _QYDropOrderStat(TerminalNo,OccurDate,PostmanID,CompanyID,ZoneID,DropNum,ExpiredNum,InBoxNum,TakeOutNum,TakeBackNum,ManagerOutNum,CreateTime,LastModifyTime,InBoxNumLast,ExpiredNumLast) 
		      VALUES(@v_TerminalNo,@v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID,0,0,0,0,@v_TakeBackNum,0,GetDate(),GetDate(),0,0)
			end
		--print @v_count
		--print @v_TerminalNo
		--print @v_OccurDate
		FETCH next from rset1 INTO @v_TerminalNo, @v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID, @v_TakeBackNum --获取一条记录
	end
	close rset1 
	--摧毁游标 
	deallocate rset1
END
BEGIN  -- 当天在箱数量统计
   declare rset1 cursor for SELECT TerminalNo,DropAgentID AS PostmanID,CompanyID,ZoneID,COUNT(PackageID) AS InBoxNum  FROM PTInBoxPackage  WHERE 1=1  GROUP BY TerminalNo,DropAgentID,CompanyID,ZoneID;
   
   Select @v_OccurDate = CONVERT(varchar(100), GETDATE(), 23)
   
   --打开游标 
	open rset1 
	FETCH next from rset1 INTO @v_TerminalNo, @v_PostmanID,@v_CompanyID,@v_ZoneID, @v_InBoxNum --获取一条记录
	
	print @v_OccurDate
	print 'Stat Inbox:'
	--print @@fetch_status
	while @@fetch_status=0 
	begin
	--读取游标 
		-- 插入或更新
		--print @@fetch_status
		SET @v_count =0 
		SELECT  @v_count=COUNT(*) FROM _QYDropOrderStat WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID
		if(@v_count>0)
			begin
			print 'UPDATE:'
			print @v_InBoxNum
			UPDATE _QYDropOrderStat SET InBoxNum=@v_InBoxNum, LastModifyTime=GetDate() WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID;
			end
	    else
			begin
			print 'INSERT:'
			print @v_InBoxNum
			INSERT INTO _QYDropOrderStat(TerminalNo,OccurDate,PostmanID,CompanyID,ZoneID,DropNum,ExpiredNum,InBoxNum,TakeOutNum,TakeBackNum,ManagerOutNum,CreateTime,LastModifyTime,InBoxNumLast,ExpiredNumLast) 
		      VALUES(@v_TerminalNo,@v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID,0,0,@v_InBoxNum,0,0,0,GetDate(),GetDate(),0,0)
			end
		--print @v_count
		--print @v_TerminalNo
		--print @v_OccurDate
		FETCH next from rset1 INTO @v_TerminalNo, @v_PostmanID,@v_CompanyID,@v_ZoneID, @v_InBoxNum --获取一条记录
	end
	close rset1 
	--摧毁游标 
	deallocate rset1
END
BEGIN  -- 当天在箱逾期数量统计
   declare rset1 cursor for SELECT TerminalNo,DropAgentID AS PostmanID,CompanyID,ZoneID,COUNT(PackageID) AS ExpiredNum  FROM PTInBoxPackage  WHERE  ItemStatus='8' OR ItemStatus='9' GROUP BY TerminalNo,DropAgentID,CompanyID,ZoneID;
   
   Select @v_OccurDate = CONVERT(varchar(100), GETDATE(), 23)
   
   --打开游标 
	open rset1 
	FETCH next from rset1 INTO @v_TerminalNo, @v_PostmanID,@v_CompanyID,@v_ZoneID,@v_ExpiredNum --获取一条记录
	
	print @v_OccurDate
	print 'Stat Expired Inbox:'
	--print @@fetch_status
	while @@fetch_status=0 
	begin
	--读取游标 
		-- 插入或更新
		--print @@fetch_status
		SET @v_count =0 
		SELECT  @v_count=COUNT(*) FROM _QYDropOrderStat WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID
		if(@v_count>0)
			begin
			print 'UPDATE:' 
			print @v_ExpiredNum
			UPDATE _QYDropOrderStat SET ExpiredNum=@v_ExpiredNum, LastModifyTime=GetDate() WHERE TerminalNo=@v_TerminalNo AND OccurDate=@v_OccurDate AND PostmanID=@v_PostmanID AND CompanyID=@v_CompanyID AND ZoneID=@v_ZoneID;
			end
	    else
			begin
			print 'INSERT:'
			print @v_ExpiredNum
			INSERT INTO _QYDropOrderStat(TerminalNo,OccurDate,PostmanID,CompanyID,ZoneID,DropNum,ExpiredNum,InBoxNum,TakeOutNum,TakeBackNum,ManagerOutNum,CreateTime,LastModifyTime,InBoxNumLast,ExpiredNumLast) 
		      VALUES(@v_TerminalNo,@v_OccurDate,@v_PostmanID,@v_CompanyID,@v_ZoneID,0,@v_ExpiredNum,0,0,0,0,GetDate(),GetDate(),0,0)
			end
		--print @v_count
		--print @v_TerminalNo
		--print @v_OccurDate
		FETCH next from rset1 INTO @v_TerminalNo, @v_PostmanID,@v_CompanyID,@v_ZoneID,@v_ExpiredNum --获取一条记录
	end
	close rset1 
	--摧毁游标 
	deallocate rset1
END
GO

