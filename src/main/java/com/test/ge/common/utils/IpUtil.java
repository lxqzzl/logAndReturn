package com.test.ge.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IP操作工具类
 *
 * @author lxq
 */
public class IpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtil.class);

    private static final String IP_PATTERN = "^(?:(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.){3}(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\b";

    /**
     * 获取请求中的ip地址：过了多级反向代理，获取的ip不是唯一的，二是包含中间代理层ip。
     *
     * @return 可能有多个，例如：192.168.1.110， 192.168.1.120
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = "127.0.0.1";
        if (request != null) {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }

            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }

            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        }
        return ip;
    }

    /**
     * 获取客户端请求中的真实的ip地址
     *
     * 获取客户端的IP地址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。
     * 但是在通过了Apache，Squid等反向代理软件就不能获取到客户端的真实IP地址。而且，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，
     * 而是一串ip值，例如：192.168.1.110， 192.168.1.120， 192.168.1.130， 192.168.1.100。其中第一个192.168.1.110才是用户真实的ip
     */
    public static String getRealIpAddr(HttpServletRequest request) {
        String ip = "127.0.0.1";
        if (request == null) {
            return ip;
        }

        ip = request.getHeader("x-forwarded-for");
        if (StringUtil.isEmpty(ip)|| "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ip = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    LOGGER.error("getRealIp occurs error, caused by: ", e);
                }
            }
        }

        if (ip != null && ip.length() > 15) {
            //"***.***.***.***".length() = 15
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }

        return ip;
    }

    /**
     * 获取服务器IP
     */
    public static String getServiceIp() {
        String ips_string = "";
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = networkInterface .getInetAddresses();
                Pattern pattern = Pattern.compile(IP_PATTERN);
                while (ips.hasMoreElements()) {
                    String ip = ips.nextElement().getHostAddress();
                    Matcher matcher = pattern.matcher(ip);
                    if (matcher.matches() && !ip.equals("127.0.0.1")) {
                        ips_string = ip;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("getServiceIp occurs error, caused by: ", e);
        }

        return ips_string;
    }

}
