package gb.library.admin.books.wishlist;

import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.admin.utils.paging.PagingAndSortingParam;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wished-books")
public class WishedBooksController {

    private final WishedBooksService service;

    @GetMapping("")
    public String showFirstPage() {
        return "redirect:/wished-books/page/1?sortField=book.title&sortDir=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(@PagingAndSortingParam(moduleURL = "/wished-books") PagingAndSortingHelper helper,
                             @PathVariable(name = "pageNum") int pageNum) {

        service.listByPage(pageNum, helper);

        return "wished_books/books_list";
    }

    @GetMapping("/delete/{id}")
    public String deleteWishedBook(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {

        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "Запись успешно удалена");
        } catch (ObjectInDBNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/wished-books";
    }
}
