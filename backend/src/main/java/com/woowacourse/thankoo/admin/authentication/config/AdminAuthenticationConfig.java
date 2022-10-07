package com.woowacourse.thankoo.admin.authentication.config;

import com.woowacourse.thankoo.admin.authentication.presentation.AdminSignInInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AdminAuthenticationConfig implements WebMvcConfigurer {

    private final AdminSignInInterceptor signInInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(signInInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/sign-in");
    }
}
