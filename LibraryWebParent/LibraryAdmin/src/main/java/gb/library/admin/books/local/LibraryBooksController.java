package gb.library.admin.books.local;

import gb.library.admin.books.global.WorldBooksService;
import gb.library.admin.storages.StorageService;
import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.admin.utils.paging.PagingAndSortingParam;
import gb.library.common.entities.LibraryBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/library-books")
public class LibraryBooksController {

    private final LibraryBooksService lbService;
    private final WorldBooksService wbService;
    private final StorageService storageService;

    @GetMapping("")
    public String showFirstPage(){
        return "redirect:/library-books/page/1?sortField=worldBook&sortDir=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(@PagingAndSortingParam(moduleURL = "/library-books") PagingAndSortingHelper helper,
                             @PathVariable(name = "pageNum") int pageNum){
        lbService.listByPage(pageNum, helper);

        return "library_books/books_list";
    }

    @GetMapping("/new")
    public String newLibBook(Model model){
        model.addAttribute("book", new LibraryBook());
        model.addAttribute("worldBooksList", wbService.getAllList());
        model.addAttribute("storagesList", storageService.getAllList());
        model.addAttribute("pageTitle", "Добавить новую книгу");

        return "library_books/book_form";
    }

    @PostMapping("/save")
    public String saveLibraryBook(LibraryBook libraryBook, RedirectAttributes redirectAttributes){
        lbService.save(libraryBook);

        redirectAttributes.addFlashAttribute("message", "Книга была успешно сохранена");
        return "redirect:/library-books";
    }

    @GetMapping("/edit/{id}")
    public String editLibraryBook(@PathVariable(name = "id") Integer id, Model model,
                                  RedirectAttributes redirectAttributes){
        try{
            LibraryBook book = lbService.getById(id);
            model.addAttribute("book", book);
            model.addAttribute("worldBooksList", wbService.getAllList());
            model.addAttribute("storagesList", storageService.getAllList());
            model.addAttribute("pageTitle", "Редактирование книги (ID=" + id + ")");

            return "library_books/book_form";
        } catch (ObjectInDBNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/library-books";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteLibraryBook(@PathVariable(name = "id")Integer id, RedirectAttributes redirectAttributes){
        try{
            lbService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Запись с ID=" + id + " успешно удалена");
        } catch (ObjectInDBNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/library-books";
    }

    @GetMapping("/{id}/enabled/{status}")
    public String updateAvailableStatus(@PathVariable(name = "id")Integer id,
                                        @PathVariable(name = "status") boolean available,
                                        RedirectAttributes redirectAttributes){
        lbService.updateAvailableStatus(id, available);
        String status = available ? "доступна" : "недоступна";
        String message = "Книга с ID=" + id + " теперь " + status;

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/library-books";
    }
}
