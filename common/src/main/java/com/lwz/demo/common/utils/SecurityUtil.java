package com.lwz.demo.common.utils;

import cn.hutool.core.util.StrUtil;
import com.lwz.demo.common.utils.http.HttpServletUtil;

public class SecurityUtil {

    public final static String TOKEN_KEY = "996.ICU";

    public final static String USER_ID = "userId";

    public final static String APP_VERSION = "appVersion";

    public final static String DEVICE_ID = "deviceId";

    /**
     * 获取用户id
     */
    public static Integer getUserId() {
        String uid = HttpServletUtil.getHeader(USER_ID);
        Integer userId = null;
        if (StrUtil.isNotBlank(uid)) {
            userId = Integer.valueOf(uid);
        }
        return userId;
    }

    /**
     * 获取app版本号
     */
    public static String getAppVersion() {
        String appVersion = HttpServletUtil.getHeader(APP_VERSION);
        return appVersion;
    }

    /**
     * 获取设备ID
     */
    public static String getDeviceId() {
        String deviceId = HttpServletUtil.getHeader(DEVICE_ID);
        return deviceId;
    }

}
