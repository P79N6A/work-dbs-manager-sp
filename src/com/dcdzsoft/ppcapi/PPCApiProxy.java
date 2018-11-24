package com.dcdzsoft.ppcapi;

import com.dcdzsoft.EduException;

public interface PPCApiProxy {
    public String sendItemInfo(SendInfo itemInfo) throws EduException;
}
