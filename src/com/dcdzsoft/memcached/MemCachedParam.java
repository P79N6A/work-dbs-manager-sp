package com.dcdzsoft.memcached;

import com.dcdzsoft.sda.db.DataSourceUtils;

import com.dcdzsoft.util.NumberUtils;
/**
 * 
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2015</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author zhengxy
 * @version 1.0
 */
public class MemCachedParam {
	private String[] servers = { "127.0.0.1:11211"};
	private Integer[] weights = { 3 };
	
	private int initConn = 10;
    private int minConn  = 10;
    private int maxConn  = 100;
    private int maxIdle  = 3600000;
    
    private MemCachedParam(){
    	
    }
    private static class SingletonHolder {
    	private static MemCachedParam instance = new MemCachedParam();
    }
    
    public static MemCachedParam getInstance()
    {
        return SingletonHolder.instance;
    }
	public  String[] getServers() {
		return servers;
	}
	public void setServers(String servers) {
		String[] serversList = servers.split(",");
		if(serversList.length > 0){
			this.servers = serversList;
		}
	}
	public void setServers(String[] servers) {
		this.servers = servers;
	}
	public Integer[] getWeights() {
		return weights;
	}
	public void setWeights(Integer[] weights) {
		this.weights = weights;
	}
	public void setWeights(String weights) {
		String[] weightsStr = weights.split(",");
		if(weightsStr.length>0){
			Integer[] ws = new Integer[weightsStr.length];
			for(int i=0; i< weightsStr.length;i++){
				int weight = NumberUtils.parseInt(weightsStr[i]);
				if(weight<=0){
					weight=1;
				}
				ws[i] = weight;
			}
			this.weights = ws;
		}else{
			this.weights[0] = 1;
		}
	}
	public int getInitConn() {
		return initConn;
	}
	public void setInitConn(int initConn) {
		if(initConn>0){
			this.initConn = initConn;
		}
		
	}
	public int getMinConn() {
		return minConn;
	}
	public void setMinConn(int minConn) {
		if(minConn>0){
			this.minConn = minConn;
		}
	}
	public int getMaxConn() {
		return maxConn;
	}
	public void setMaxConn(int maxConn) {
		if(maxConn> this.minConn){
			this.maxConn = maxConn;
		}
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		if(maxIdle>(1000*60*10)){
			this.maxIdle = maxIdle;
		}
	}
}
