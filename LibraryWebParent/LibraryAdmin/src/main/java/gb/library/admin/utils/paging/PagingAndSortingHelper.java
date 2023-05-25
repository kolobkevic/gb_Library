package gb.library.admin.utils.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

public class PagingAndSortingHelper {

    private final ModelAndViewContainer model;
    private final String sortField;
    private final String sortDir;
    private final String keyword;

    public PagingAndSortingHelper(ModelAndViewContainer model, String sortField, String sortDir, String keyword){
        this.model      = model;
        this.sortDir    = sortDir;
        this.sortField  = sortField;
        this.keyword    = keyword;
    }

    public String getSortField() {
        return sortField;
    }

    public String getSortDir() {
        return sortDir;
    }

    public String getKeyword() {
        return keyword;
    }

    public void updateModelAttributes(int pageNum, Page<?> page){
        List<?> listItems = page.getContent();
        int pageSize = page.getSize();

        long startCount = (long) (pageNum - 1) * pageSize + 1;
        long endCount = startCount + pageSize - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("entitiesList", listItems);
    }

    public void listEntities(int pageNum, int pageSize,
                             SearchRepository<?, Integer> repo){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        if (pageNum<1) pageNum = 1;

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        Page<?> page = null;
        if (keyword != null) {
            page =  repo.getAllWithFilter(keyword, pageable);
        } else {
            page = repo.findAll(pageable);
        }

        updateModelAttributes(pageNum, page);
    }

    public Pageable createPageable(int pageSize, int pageNum){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        return PageRequest.of(pageNum - 1, pageSize, sort);
    }
}
