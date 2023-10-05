package server.global.argument_resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;

import static server.global.constant.TextConstant.MEMBER_SESSION;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        boolean isMemberSessionType = parameter.getParameterType().equals(MemberSession.class);
        boolean isLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        return isMemberSessionType && isLoginAnnotation;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory)  {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return request.getAttribute(MEMBER_SESSION.value);
    }
}
