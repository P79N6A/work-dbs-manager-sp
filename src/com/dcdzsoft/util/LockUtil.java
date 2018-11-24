package com.dcdzsoft.util;


public class LockUtil {
	public class MyLock{
		public int value;
		public MyLock(int value){
			this.value = value;
		}
	}
	private java.util.Map<String, MyLock> lockMap;
	public LockUtil(){
		lockMap = new java.util.HashMap<String, MyLock>();
	}
	public MyLock getLock(String key){
		if(key.isEmpty()){
			return null;
		}
		MyLock lockObj = null;
		synchronized (this) {
            if(lockMap.containsKey(key)){
                lockObj = lockMap.get(key);
            }else{
            	lockObj = new MyLock(0);
            }
            lockObj.value += 1;
            lockMap.put(key, lockObj);
		}
		
		return lockObj;
	}
	public void releaseLock(String key){
		if(key.isEmpty()){
			return;
		}
		MyLock lockObj = null;
		synchronized (this) {
            if(lockMap.containsKey(key)){
                lockObj = lockMap.get(key);
                if(lockObj.value>0){
                	lockObj.value -= 1;
                }else{
                	lockObj.value = 0;
                }
                lockMap.put(key, lockObj);
            }
		}
		return;
	}
	public void clear(){
		synchronized (this) {
			lockMap.clear();
		}
	}
	public int remove(){
		int count = 0;
		synchronized (this) {
			java.util.Set<String> keys = lockMap.keySet();
			for(String key:keys){
				MyLock lockObj = lockMap.get(key);
				if(lockObj.value == 0){//删除失效的锁
					lockMap.remove(key);
				}
			}
		}
		return count;
	}
}
