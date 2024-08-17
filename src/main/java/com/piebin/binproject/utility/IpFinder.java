package com.piebin.binproject.utility;

import jakarta.servlet.http.HttpServletRequest;

public class IpFinder {
    public static String getClientOP(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null || ip.length() == 0)
            ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0)
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0)
            ip = request.getHeader("HTTP_CLIENT_IP");
        if (ip == null || ip.length() == 0)
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null || ip.length() == 0)
            ip = request.getRemoteAddr();
        return ip;
    }
}
