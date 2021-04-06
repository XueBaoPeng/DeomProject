//package com.demo.project.common.aop;
//
//import com.jd.data.server.app.common.annotation.NoRepeatSubmit;
//import com.jd.data.server.app.common.config.SsoConfig;
//import com.jd.data.server.app.service.R2mDistributedLock;
//import com.jd.data.server.common.util.Result;
//import com.jd.ssa.utils.SSOHelper;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Assert;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
///** 拦截添加了防重复拦截注解的方法
// * @Description:
// * @Author: xuebaopeng
// * @Date: 2021/3/18 9:59
// */
//@Slf4j
//@Aspect
//@Component
//public class RepeatSubmitAspect {
//
//    @Autowired
//    private R2mDistributedLock r2mDistributedLock;
//
//    @Autowired
//    SsoConfig ssoConfig;
//
//    @Pointcut("@annotation(noRepeatSubmit)")
//    public void pointCut(NoRepeatSubmit noRepeatSubmit) {
//    }
//
//
//    public HttpServletRequest getRequest(){
//        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        return ra.getRequest();
//    }
//    @Around("pointCut(noRepeatSubmit)")
//    public Object around(ProceedingJoinPoint point,NoRepeatSubmit noRepeatSubmit) throws Throwable{
//        long lockSenconds=noRepeatSubmit.lockTime();
//        HttpServletRequest request = getRequest();
//        Assert.notNull(request, "request can not null");
//        String ticket = this.getCookieValue(request, ssoConfig.getCookiename());
//        String remoteIP = SSOHelper.getRemoteIP(request);
//        String key=getKey(ticket,remoteIP,request.getServletPath());
//
//        if (r2mDistributedLock.isEnd(key)) {// 没有刷新缓存的动作
//            boolean isSuccess = r2mDistributedLock.tryLock(key, lockSenconds);
//            log.info("tryLock key = [{}]]", key);
//
//            if (isSuccess) {
//                log.info("tryLock success, key = [{}]]", key);
//                // 获取锁成功
//                Object result;
//                try {
//                    // 执行进程
//                    result = point.proceed();
//                } finally {
//                    // 解锁
//                    r2mDistributedLock.unlock(key);
//                    log.info("releaseLock success, key = [{}]]", key);
//                }
//                return result;
//            } else {
//                // 获取锁失败，认为是重复提交的请求
//                log.info("tryLock fail, key = [{}],重复请求，请稍后再试", key);
//                return Result.failed("重复请求，请稍后再试");
//            }
//        } else {
//            // 获取锁失败，认为是重复提交的请求
//            log.info("tryLock fail, key = [{}],重复请求，请稍后再试", key);
//            return Result.failed("重复请求，请稍后再试");
//        }
//
//    }
//
//
//    private String getKey(String ticket,String remoteIP,String path){
//        return ticket+remoteIP+path;
//    }
//
//    public String getCookieValue(HttpServletRequest servletRequest, String name) {
//        Cookie[] cookies = servletRequest.getCookies();
//        if (cookies != null && cookies.length > 0) {
//            Cookie[] var4 = cookies;
//            int var5 = cookies.length;
//
//            for(int var6 = 0; var6 < var5; ++var6) {
//                Cookie cookie = var4[var6];
//                String cookieName = cookie.getName();
//                if (cookieName.equals(name)) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null;
//    }
//}
