package ru.sberbank.vkr.microblog.webuiservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

//        String token = request.getHeader("Authorization");
//
//        String servletPath = request.getServletPath();
//
//        // Информация пользователя сохранена в Session
//        // (После успешного входа в систему).
//        UserAccount loginedUser = AppUtils.getLoginedUser(request.getSession());
//
//        if (servletPath.equals("/login")) {
//            chain.doFilter(request, response);
//            return;
//        }
//        HttpServletRequest wrapRequest = request;
//
//        if (loginedUser != null) {
//            // User Name
//            String userName = loginedUser.getUserName();
//
//            // Старый пакет request с помощью нового Request с информацией userName и Roles.
//            wrapRequest = new UserRoleRequestWrapper(userName, roles, request);
//        }
//
//        // Страницы требующие входа в систему.
//        if (SecurityUtils.isSecurityPage(request)) {
//
//            // Если пользователь еще не вошел в систему,
//            // Redirect (перенаправить) к странице логина.
//            if (loginedUser == null) {
//
//                String requestUri = request.getRequestURI();
//
//                // Сохранить текущую страницу для перенаправления (redirect) после успешного входа в систему.
//                int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);
//
//                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
//                return;
//            }
//        }
//
//        chain.doFilter(wrapRequest, response);
        return super.preHandle(request, response, handler);
    }
}