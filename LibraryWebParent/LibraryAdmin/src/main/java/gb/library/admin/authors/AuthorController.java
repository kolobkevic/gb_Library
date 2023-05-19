package gb.library.admin.authors;

import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.admin.utils.paging.PagingAndSortingParam;
import gb.library.common.entities.Author;
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
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;

    @GetMapping("")
    public String showFirstPage() {
        return "redirect:/authors/page/1?sortField=lastName&sortDir=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(@PagingAndSortingParam(moduleURL = "/authors")PagingAndSortingHelper helper,
                           @PathVariable(name = "pageNum") int pageNum) {
        service.listByPage(pageNum, helper);

        return "authors/authors_list";
    }

    @GetMapping("/new")
    public String newAuthor(Model model){

        model.addAttribute("author", new Author());
        model.addAttribute("pageTitle", "Добавить нового Автора");
        return "authors/author_form";
    }

    @PostMapping("/save")
    public String saveAuthor(Author author, RedirectAttributes redirectAttributes) {
        service.save(author);

        redirectAttributes.addFlashAttribute("message", "Автор был успешно сохранён.");
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String editAuthor(@PathVariable(name = "id") Integer id, Model model,
                            RedirectAttributes redirectAttributes){
        try {
            Author author = service.getById(id);
            model.addAttribute("author", author);
            model.addAttribute("pageTitle", "Редактирование автора (ID=" + id + ")");

            return "authors/author_form";
        }  catch (ObjectInDBNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/authors";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {

        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "Запись успешно удалена");
        } catch (ObjectInDBNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/authors";
    }
}
