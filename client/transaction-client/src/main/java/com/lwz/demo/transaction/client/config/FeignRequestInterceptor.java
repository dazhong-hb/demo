package com.lwz.demo.transaction.client.config;

import com.lwz.demo.common.utils.SecurityUtil;
import com.lwz.demo.common.utils.http.HttpServletUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration("transactionFeign")
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String userId = HttpServletUtil.getHeader(SecurityUtil.USER_ID);
        String appVersion = HttpServletUtil.getHeader(SecurityUtil.APP_VERSION);
        String deviceId = HttpServletUtil.getHeader(SecurityUtil.DEVICE_ID);
        requestTemplate.header(SecurityUtil.USER_ID, userId).header(SecurityUtil.APP_VERSION, appVersion)
                .header(SecurityUtil.DEVICE_ID, deviceId);
    }

}