package gb.library.backend.admin.configs;

import gb.library.backend.admin.utils.paging.PagingAndSortingArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PagingAndSortingArgumentResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
