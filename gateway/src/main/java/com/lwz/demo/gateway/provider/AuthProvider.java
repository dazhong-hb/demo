package com.lwz.demo.gateway.provider;

import java.util.ArrayList;
import java.util.List;

public class AuthProvider {

    public static String TARGET = "/**";
    public static String REPLACEMENT = "";

    /**
     * 默认无需鉴权的API
     */
    private static final List<String> defaultSkipUrl = new ArrayList<>();

    /**
     * 默认拒绝访问的API
     */
    private static final List<String> defaultRefuseUrl = new ArrayList<>();

    // ================================== 白名单 ==================================

    static {
        defaultSkipUrl.add("/abc/hello");
        defaultSkipUrl.add("/abc/nacos");
        defaultSkipUrl.add("/abc/datasource");
        defaultSkipUrl.add("/api-docs");
        defaultSkipUrl.add("/user/login");
        defaultSkipUrl.add("/user/refresh");
    }

    public static boolean isSkip(String path) {
        return defaultSkipUrl.stream().map(url ->
                url.replace(TARGET, REPLACEMENT)).anyMatch(path::contains);
    }

    // ================================== 黑名单 ==================================

    static {
        defaultRefuseUrl.add("/xxx");
    }

    public static boolean isRefuse(String path) {
        return defaultRefuseUrl.stream().map(url ->
                url.replace(TARGET, REPLACEMENT)).anyMatch(path::contains);
    }

}
