package com.chankin.system.security.ip;

import com.chankin.service.SystemService;
import com.chankin.system.security.exception.ForbiddenIpException;
import com.chankin.util.ResponseCode;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Order(1)
public class ForbiddenIpAOP {
    private static Logger log = LoggerFactory.getLogger(ForbiddenIpAOP.class);

    @Autowired
    private SystemService systemService;

    @Before("@within(org.springframework.web.bind.annotation.RequestMapping)")
    public void forbiddenIp() throws ForbiddenIpException {
        if ("true".equals(systemService.selectDataItemByKey("ip_forbidden", 3L))) {
            log.debug("open ip intercepter: {}", true);
            //取到当前请求的request对象
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String remoteAddr = request.getRemoteAddr();
            if (systemService.isForbiddenIp(remoteAddr)) {
                log.error("this {} ip is forbidden", remoteAddr);
                throw new ForbiddenIpException(ResponseCode.forbidden_ip.getMsg());
            }
        }
    }
}
