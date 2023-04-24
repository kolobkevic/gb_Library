package gb.library.admin.utils.paging;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PagingAndSortingArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(PagingAndSortingParam.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer model,
                                  NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {

        PagingAndSortingParam annotation = parameter.getParameterAnnotation(PagingAndSortingParam.class);

        String sortDir      = request.getParameter("sortDir");
        String sortField    = request.getParameter("sortField");
        String keyword      = request.getParameter("keyword");

        sortDir = sortDir == null ? "asc" : sortDir;
        sortField = sortField == null ? "id" : sortField;

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        assert model != null;
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        assert annotation != null;
        model.addAttribute("moduleURL", annotation.moduleURL());

        return new PagingAndSortingHelper(model, sortField, sortDir, keyword);
    }
}
