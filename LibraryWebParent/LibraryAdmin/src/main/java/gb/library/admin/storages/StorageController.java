package gb.library.admin.storages;

import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.admin.utils.paging.PagingAndSortingParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/storages")
public class StorageController {
    private final StorageService service;

    @GetMapping("")
    public String listFirstPage() {
        return "redirect:/storages/page/1?sortField=zone&sortDir=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PagingAndSortingParam(moduleURL = "/storages")PagingAndSortingHelper helper,
                             @PathVariable(name = "pageNum") int pageNum){
        service.listByPage(pageNum, helper);

        return "/storages/storage_list";
    }
}
