package gb.library.admin.storages;

import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.admin.utils.paging.PagingAndSortingParam;
import gb.library.common.entities.AgeRating;
import gb.library.common.entities.Storage;
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
@RequestMapping("/storages")
public class StorageController {
    private final StorageService service;

    @GetMapping("")
    public String showFirstPage() {
        return "redirect:/storages/page/1?sortField=zone&sortDir=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(@PagingAndSortingParam(moduleURL = "/storages")PagingAndSortingHelper helper,
                             @PathVariable(name = "pageNum") int pageNum){
        service.listByPage(pageNum, helper);

        return "storages/storage_list";
    }

    @GetMapping("/new")
    public String newStorage(Model model){
        model.addAttribute("storage", new Storage());
        model.addAttribute("pageTitle", "Добавить новое место");

        return "storages/storage_form";
    }

    @PostMapping("/save")
    public String saveStorage(Storage storage, RedirectAttributes redirectAttributes) {
        service.save(storage);

        redirectAttributes.addFlashAttribute("message", "Запись успешно была добавлена");
        return "redirect:/storages";
    }

    @GetMapping("/edit/{id}")
    public String editStorage(@PathVariable(name = "id") Integer id, Model model,
                                RedirectAttributes redirectAttributes){
        try {
            Storage storage = service.getById(id);
            model.addAttribute("storage", storage);
            model.addAttribute("pageTitle", "Редактирование места (ID=" + id + ")");

            return "storages/storage_form";
        }  catch (ObjectInDBNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/storages";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteStorage(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {

        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "Запись успешно удалена");
        } catch (ObjectInDBNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/storages";
    }

    @GetMapping("/{id}/enabled/{status}")
    public String updateAvailableStatus(@PathVariable(name = "id") Integer id,
                                        @PathVariable(name = "status") boolean available,
                                        RedirectAttributes redirectAttributes) {
        service.updateAvailableStatus(id, available);
        String status = available ? "используется" : "недоступно";
        String message = "Место хранения ID=" + id + " теперь " + status;

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/storages";
    }
}
