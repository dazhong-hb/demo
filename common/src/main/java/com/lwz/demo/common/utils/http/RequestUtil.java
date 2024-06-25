package com.lwz.demo.common.utils.http;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RequestUtil {

    /**
     * 获取完整URL，包含查询参数
     * @param request
     * @return
     */
    public static String getQueryUrl(HttpServletRequest request) {
        String result = getUrl(request);
        String query = getQueryString(request);
        result += query != null
                ? "?" + query
                : "";
        return result;
    }

    /**
     * 获取URI，除协议、域名、端口部分
     * @param request
     * @return
     */
    public static String getUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * 获取完整URL，不包含查询参数
     * @param request
     * @return
     */
    public static String getUrl(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    /**
     * 获取工程名
     * @param request
     * @return
     */
    public static String getContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    /**
     * 获取协议、域名、端口URL
     * @param request
     * @return
     */
    public static String getHostUrl(HttpServletRequest request) {
        return getUrl(request).replace(getUri(request), "");
    }

    /**
     * 获取工程路径，协议、域名、端口、工程名URL
     * @param request
     * @return
     */
    public static String getContextUrl(HttpServletRequest request) {
        return getHostUrl(request) + getContextPath(request);
    }

    /**
     * 获取查询参数
     * @param request
     * @return
     */
    public static String getQueryString(HttpServletRequest request) {
        return request.getQueryString();
    }

    /**
     * 获取POST请求中Body参数
     * @param request
     * @return 字符串
     */
    public static String getBody(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取远程IP
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
