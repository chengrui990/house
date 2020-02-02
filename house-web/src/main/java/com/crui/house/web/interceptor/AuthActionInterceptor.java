package com.crui.house.web.interceptor;

import com.crui.house.common.model.User;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * VM Args:
 *
 * @author crui
 */
@Component
public class AuthActionInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = UserContext.getUser();
        if (user==null){
            String msg = URLEncoder.encode("请先登录", "utf-8");
            String target = URLEncoder.encode(String.valueOf(request.getRequestURL()),"utf-8");
            if ("GET".equalsIgnoreCase(request.getMethod())){
                response.sendRedirect("/accounts/signin?errorMsg=" + msg + "&target=" + target);

            }else {
                response.sendRedirect("/accounts/signin?errorMsg=" + msg );
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
