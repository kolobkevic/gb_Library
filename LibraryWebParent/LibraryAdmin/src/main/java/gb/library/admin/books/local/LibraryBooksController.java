package gb.library.admin.books.local;

import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.admin.utils.paging.PagingAndSortingParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/library-books")
public class LibraryBooksController {

    private final LibraryBooksService lbService;


    @GetMapping("")
    public String listFirstPage(){
        return "redirect:/library-books/page/1?sortField=worldBook&sortDir=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PagingAndSortingParam(moduleURL = "/library-books") PagingAndSortingHelper helper,
                             @PathVariable(name = "pageNum") int pageNum){
        lbService.listByPage(pageNum, helper);

        return "library_books/books_list";
    }
}
