package gb.library.admin.genres;

import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.admin.utils.paging.PagingAndSortingParam;
import gb.library.common.entities.Genre;
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
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping("")
    public String showFirstPage() {
        return "redirect:/genres/page/1?sortField=name&sortDir=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(@PagingAndSortingParam(moduleURL = "/genres")PagingAndSortingHelper helper,
                             @PathVariable(name = "pageNum") int pageNum) {
        genreService.listByPage(pageNum, helper);

        return "genres/genres_list";
    }

    @GetMapping("/new")
    public String newGenre(Model model){

        model.addAttribute("genre", new Genre());
        model.addAttribute("pageTitle", "Добавить новый Жанр");
        return "genres/genre_form";
    }

    @PostMapping("/save")
    public String saveGenre(Genre genre, RedirectAttributes redirectAttributes) {
        genreService.save(genre);

        redirectAttributes.addFlashAttribute("message", "Жанр был успешно сохранён.");
        return "redirect:/genres";
    }

    @GetMapping("/edit/{id}")
    public String editGenre(@PathVariable(name = "id") Integer id, Model model,
                            RedirectAttributes redirectAttributes){
        try {
            Genre genre = genreService.getById(id);
            model.addAttribute("genre", genre);
            model.addAttribute("pageTitle", "Редактирование жанра (ID=" + id + ")");

            return "genres/genre_form";
        }  catch (ObjectInDBNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/genres";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteGenre(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {

        try {
            genreService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Запись успешно удалена");
        } catch (ObjectInDBNotFoundException ex) {   //поменять перехват ошибки
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/genres";
    }
}
