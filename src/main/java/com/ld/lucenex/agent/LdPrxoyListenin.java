package com.ld.lucenex.agent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.cglib.proxy.Callback;

/**
 * 回调支持
 * @author Administrator
 *
 */
public class LdPrxoyListenin {
	
	private Map<String, Callback> prxoyListenin;
	
	private static LdPrxoyListenin kit = new LdPrxoyListenin();
	
	public static LdPrxoyListenin build() {
		synchronized (kit) {
			if(kit.prxoyListenin == null) {
				kit.prxoyListenin = new ConcurrentHashMap<>();
			}
			return kit;
		}
	}
	
	public void addListenin(String objectName,PrxoyInterceptor callback) {
		prxoyListenin.put(objectName, callback);
	}
	
	public Callback getListenin(String objectName) {
		return prxoyListenin.get(objectName);
	}
}
