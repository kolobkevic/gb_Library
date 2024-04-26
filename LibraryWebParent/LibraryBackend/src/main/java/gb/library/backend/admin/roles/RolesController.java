package gb.library.backend.admin.roles;

import gb.library.backend.admin.utils.paging.PagingAndSortingHelper;
import gb.library.backend.admin.utils.paging.PagingAndSortingParam;
import gb.library.common.entities.Role;
import gb.library.common.enums.RoleType;
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
@RequestMapping("/roles")
public class RolesController {

    private final RolesService service;

    @GetMapping("")
    public String showFirstPage(){
        return "redirect:/roles/page/1?sortField=name&sortDir=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(@PagingAndSortingParam(moduleURL = "/roles")PagingAndSortingHelper helper,
                           @PathVariable(name = "pageNum") int pageNum){
        service.listByPage(pageNum, helper);

        return "roles/roles_list";
    }

    @GetMapping("/new")
    public String newRole(Model model){
        model.addAttribute("role", new Role());
        model.addAttribute("typesList", Arrays.stream(RoleType.values()).toList());
        model.addAttribute("pageTitle", "Добавить новую роль");

        return "roles/role_form";
    }

    @PostMapping("/save")
    public String saveRole(Role role, RedirectAttributes redirectAttributes){
        service.save(role);

        redirectAttributes.addFlashAttribute("message", "Роль была успешно сохранена");
        return "redirect:/roles";
    }

    @GetMapping("/edit/{id}")
    public String editRole(@PathVariable(name = "id") Integer id, Model model,
                           RedirectAttributes redirectAttributes){
        try {
            Role role = service.getById(id);
            model.addAttribute("role", role);
            model.addAttribute("typesList", Arrays.stream(RoleType.values()).toList());
            model.addAttribute("pageTitle", "Редактирование роли (ID=" + id + ")");

            return "roles/role_form";
        }catch (ObjectInDBNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/roles";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes){
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "Запись с ID=" + id + " успешно удалена");
        } catch (ObjectInDBNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/roles";
    }
}
