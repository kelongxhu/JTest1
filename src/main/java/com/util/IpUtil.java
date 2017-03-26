package com.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IpUtil {
	private final static Logger logger = LoggerFactory.getLogger(IpUtil.class);

	/**
	 * 获取IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		logger.info("x-forwarded-for:" + ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.info("Proxy-Client-IP:" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.info("WL-Proxy-Client-IP:" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			logger.info("getRemoteAddr:" + ip);
		}
		
		if (StringUtils.isEmpty(ip)) {
			return "";

		}
		
		String[] ips = ip.split(",");
		
		//过滤内网IP
		List<String> ipList = filterInnerIP(ips);
		if(CollectionUtils.isEmpty(ipList)){
			return ip;
		}
		
		return ipList.get(0);
		
	}

	/**
	 * 比较两个ip 大小
	 * if sourceIp = desIp return null
	 * if sourceIp > desIp return true
	 * if sourceIp < desIp return false
	 * 
	 * @param sourceIp
	 * @param desIp
	 * @return
	 */
	public static Boolean compressIp(String sourceIp, String desIp) {
		if (sourceIp.equals(desIp)) {
			return null;
		}
		try {
			String[] sourceIpEles = sourceIp.split("\\.");
			String[] desIpEles = desIp.split("\\.");
			for (int i = 0; i < desIpEles.length; i++) {
                if (Integer.valueOf(sourceIpEles[i]) > Integer
                        .valueOf(desIpEles[i])) {
                    return true;
                } else if (Integer.valueOf(sourceIpEles[i]) < Integer
                        .valueOf(desIpEles[i])) {
                    return false;
                }
            }
		} catch (NumberFormatException e) {
		}
		return false;
	}

	/**
	 * 检查是否是内网IP
	 * 	是 true
	 * 	否 false
	 *  内网IP区段： A类 10.0.0.0--10.255.255.255 B类
	 * 172.16.0.0--172.31.255.255 C类 192.168.0.0--192.168.255.255
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean checkInnerIP(String ip) {
		String ips[] = new String[] { "10.0.0.0", "10.255.255.255",
				"172.16.0.0", "172.31.255.255", "192.168.0.0",
				"192.168.255.255" };
		List<String> ipList = Arrays.asList(ips);
		if (ipList.contains(ip)) {
			return true;
		}
		for (int i = 0; i < ips.length; i = i + 2) {
			if (compressIp(ip, ips[i]) && !compressIp(ip, ips[i + 1])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 过滤内网IP
	 * 
	 * @param ips
	 * @return
	 */
	public static List<String> filterInnerIP(String[] ips) {
		List<String> ipList = new ArrayList<String>();
		for (int i = 0; i < ips.length; i++) {
			String ip = ips[i].replaceAll("\\s", "");
			if (checkInnerIP(ip)) {
				continue;
			}
			if (!ipList.contains(ip)) {
				ipList.add(ip);
			}
		}
		return ipList;
	}
	public static void main(String[] args){
		
	}
}