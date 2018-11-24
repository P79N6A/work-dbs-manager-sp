package com.dcdzsoft.memcached;

import java.io.IOException;
import java.util.Date;

import net.sf.json.JSONException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.dcdzsoft.config.ApplicationConfig;

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
 * @author zxy
 * @version 1.0
 */
public class MemCachedManager {
	private static Log log = org.apache.commons.logging.LogFactory
			.getLog(MemCachedManager.class);
	
	private static MemCachedClient cachedClient = new MemCachedClient(); // memcached客户端单例
	
    // 获取连接池的实例
	private static SockIOPool pool = SockIOPool.getInstance();
	/**
	 * 私有化
	 */
	private MemCachedManager(){
		//获取配置参数
		MemCachedParam param = MemCachedParam.getInstance();
		
		// 设置服务器信息
		pool.setServers(param.getServers());
		pool.setWeights(param.getWeights());
		// 设置初始连接数、最小连接数、最大连接数、最大处理时间
		pool.setInitConn(param.getInitConn());
		pool.setMinConn(param.getMinConn());
		pool.setMaxConn(param.getMaxConn());
		pool.setMaxIdle(param.getMaxIdle());
		//设置主线程睡眠时间，每3秒苏醒一次，维持连接池大小  
		//maintSleep 千万不要设置成30，访问量一大就出问题，单位是毫秒，推荐30000毫秒。
		pool.setMaintSleep(30000);
		// 设置连接心跳监测开关
	    // true:每次通信都要进行连接是否有效的监测，造成通信次数倍增，加大网络负载，
	    // 因此在对HighAvailability要求比较高的场合应该设为true
	    // 默认状态是false，建议保持默认。
	    pool.setAliveCheck(false);
	    // 设置容错开关
	    // true:当当前socket不可用时，程序会自动查找可用连接并返回，否则返回NULL
	    // 默认状态是true，建议保持默认。
	    pool.setFailover(true);
	    // 设置hash算法
	    // alg=0 使用String.hashCode()获得hash code,该方法依赖JDK，可能和其他客户端不兼容，建议不使用
	    // alg=1 使用original 兼容hash算法，兼容其他客户端
	    // alg=2 使用CRC32兼容hash算法，兼容其他客户端，性能优于original算法
	    // alg=3 使用MD5 hash算法
	    // 采用前三种hash算法的时候，查找cache服务器使用余数方法。采用最后一种hash算法查找cache服务时使用consistent方法。
	    // 默认值为0
	    pool.setHashingAlg(0);
	    // 设置是否使用Nagle算法，因为我们的通讯数据量通常都比较大（相对TCP控制数据）而且要求响应及时，
	    // 因此该值需要设置为false（默认是true）
		pool.setNagle(false);  
		// 设置socket的读取等待超时值
	    pool.setSocketTO(3000);
		//连接建立时的超时时间  
		pool.setSocketConnectTO(0); 
		// 初始化并启动连接池
		pool.initialize();
		// 压缩设置，超过指定大小的都压缩
		// cachedClient.setCompressEnable(true);
		// cachedClient.setCompressThreshold(1024*1024);
	}
	private static class SingletonHolder {
		private static final MemCachedManager instance = new MemCachedManager();
	}

	/**
	 * 静态工厂方法，返还此类的惟一实例
	 * 
	 * @return a MemCachedManager instance
	 */
	public static MemCachedManager getInstance() {
		return SingletonHolder.instance;
	}
	/**
	* 新增缓存数据,该KEY值如果没有则插入
	* @param key
	*        键（key）
	* @param value
	* @return
	*/
	public boolean add(String key, Object value) {
		return cachedClient.add(key, value);
	}
	/**
	* 新增缓存数据,该KEY值如果没有则插入
	* @param key
	*        键（key）
	* @param value
	* @param expire
	*        过期时间（单位是秒）
	* @return
	*/
	public boolean add(String key, Object value, Integer expire) {
		return cachedClient.add(key, value, expire);
	}
	/**
	* 新增缓存数据,该KEY值如果没有则插入
	* @param key
	*        键（key）
	* @param value
	* @param expireDate
	*        失效日期      
	* @return
	*/
	public boolean add(String key, Object value,  Date expireDate) {
		log.debug("add key="+key+",value="+value.toString()+",expiredate="+expireDate.toString());
		return cachedClient.add(key, value, expireDate);
	}
	/**
	* 设置缓存中的对象（value），如果没有则插入，如果有则修改。
	* @param key
	* @param value
	* @return
	*/
	public boolean set(String key, Object value) {
		return cachedClient.set(key, value);
	}
	/**
	* 设置缓存中的对象（value），如果没有则插入，如果有则修改。
	* @param key
	* @param value
	* @param expire
	*        过期时间（单位是秒）
	* @return
	*/
	public boolean set(String key, Object value, Integer expire) {
		return cachedClient.set(key, value, expire);
	} 
	/**
	* 设置缓存中的对象（value），如果没有则插入，如果有则修改。
	* @param key
	* @param value
	* @param expireDate
	*        失效日期
	* @return
	*/
	public boolean set(String key, Object value, Date expireDate) {
		//log.debug("set key="+key+",value="+value.toString()+",expiredate="+expireDate.toString());
		return cachedClient.set(key, value, expireDate);
	}
	/**
	* 该键的新值（new value），如果有则修改。
	* @param key
	* @param value
	* @return
	*/
	public boolean replace(String key, Object value) {
		return cachedClient.replace(key, value);
	}
	/**
	* 该键的新值（new value），如果有则修改。
	* @param key
	* @param value
	* @param expire
	*        过期时间（单位是秒）
	* @return
	*/
	public boolean replace(String key, Object value, Integer expire) {
		return cachedClient.replace(key, value, expire);
	}
	/**
	* 该键的新值（new value），如果有则修改。
	* @param key
	* @param value
	* @param expireDate
	*        失效日期
	* @return
	*/
	public boolean replace(String key, Object value, Date expireDate) {
		log.debug("replace key="+key+",value="+value.toString()+",expiredate="+expireDate.toString());
		return cachedClient.replace(key, value, expireDate);
	}
	public Object get(String key) {
		//log.debug("get key="+key);
		Object obj = null;
		try{
			obj = cachedClient.get(key);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return obj;
	}
	/**
	* 清空所有对象
	*/
	public void flushAll(){
		log.debug("flushAll");
		cachedClient.flushAll();
	}
	/*public static void main(String[] args){
		Init.init();
		try {
			ApplicationConfig.getInstance().load("work/WEB-INF/appconfig.xml");
			
			MemCachedManager memCache = MemCachedManager.getInstance();
			boolean result1=memCache.set("00001", "Locker01",new Date(1000 * 2));
			System.out.println("第一次add : " + result1);
		    System.out.println("Value : " + memCache.get("00001"));
			
		    boolean result2 =memCache.add("hello", 12345, new Date(1000 * 10));// add fail
		    System.out.println("第二次add : " + result2);
		    System.out.println("Value : " + memCache.get("hello"));
		    
		    boolean result3 =memCache.set("hello", 123456789, new Date(1000 * 2));// set successes
		    System.out.println("调用set : " + result3);
		     
		    System.out.println("Value : " + memCache.get("hello"));
		 
		    try {
		      Thread.sleep(1000 * 2);
		      System.out.println("已经sleep2秒了....");
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    }
		    System.out.println("Value : " + memCache.get("hello"));
			
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}catch (Exception e){
			e.printStackTrace();
			return;
		}
		
	}*/
}
