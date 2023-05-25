package gb.library.admin.books.global;

import gb.library.admin.authors.AuthorService;
import gb.library.admin.genres.GenreService;
import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.admin.utils.paging.PagingAndSortingParam;
import gb.library.common.entities.AgeRating;
import gb.library.common.entities.WorldBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@RequestMapping("/world-books")
public class WorldBooksController {

    private final WorldBooksService service;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("")
    public String showFirstPage(){
        return "redirect:/world-books/page/1?sortField=title&sortDir=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(@PagingAndSortingParam(moduleURL = "/world-books")PagingAndSortingHelper helper,
                             @PathVariable(name = "pageNum") int pageNum){

        service.listByPage(pageNum, helper);

        return "world_books/books_list";
    }

    @GetMapping("/new")
    public String newWorldBook(Model model){
        model.addAttribute("book", new WorldBook());
        model.addAttribute("authorsList", authorService.getAllList());
        model.addAttribute("genresList", genreService.getAllList());
        model.addAttribute("ratingsList", Arrays.stream(AgeRating.values()).toList());
        model.addAttribute("pageTitle", "Добавить новую книгу");
        return "world_books/book_form";
    }

    @PostMapping("/save")
    public String saveWorldBook(WorldBook worldBook, RedirectAttributes redirectAttributes){
        service.save(worldBook);

        redirectAttributes.addFlashAttribute("message", "Книга была успешно сохранена.");
        return "redirect:/world-books";
    }

    @GetMapping("/edit/{id}")
    public String editWorldBook(@PathVariable(name = "id") Integer id, Model model,
                             RedirectAttributes redirectAttributes){
        try {
            WorldBook book = service.getById(id);
            model.addAttribute("book", book);
            model.addAttribute("pageTitle", "Редактирование книги (ID=" + id + ")");
            model.addAttribute("authorsList", authorService.getAllList());
            model.addAttribute("genresList", genreService.getAllList());
            model.addAttribute("ratingsList", Arrays.stream(AgeRating.values()).toList());

            return "world_books/book_form";
        }  catch (ObjectInDBNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/world-books";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteWorldBook(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {

        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "Запись с ID=" + id + " успешно удалена");
        } catch (ObjectInDBNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/world-books";
    }
}
