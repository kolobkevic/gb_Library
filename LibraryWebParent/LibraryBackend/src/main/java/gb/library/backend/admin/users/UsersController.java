package gb.library.backend.admin.users;

import gb.library.backend.admin.roles.RolesService;
import gb.library.backend.admin.utils.paging.PagingAndSortingHelper;
import gb.library.backend.admin.utils.paging.PagingAndSortingParam;
import gb.library.common.dtos.UserDataDTO;
import gb.library.common.entities.User;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.pd.openapi.client.pd.model.ReaderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final RolesService rolesService;
    private final UsersPersonalDataService personalDataService;
    private final UserDataMapper userDataMapper;

    @GetMapping("")
    public String showFirstPage(){
        return "redirect:/users/page/1?sortField=email&sortDir=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String showPage(@PagingAndSortingParam(moduleURL = "/users")PagingAndSortingHelper helper,
                           @PathVariable(name = "pageNum") int pageNum){
        usersService.listByPage(pageNum, helper);

        return "users/users_list";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        UserDataDTO newUser = new UserDataDTO();
        newUser.setEnabled(true);
        model.addAttribute("user", newUser);
        model.addAttribute("listRoles", rolesService.getAllList());
        model.addAttribute("pageTitle", "Добавить нового пользователя");

        return "users/user_form";
    }

    @PostMapping("/save")
    public String saveUser(UserDataDTO user, RedirectAttributes redirectAttributes) {
        usersService.save(user);

        redirectAttributes.addFlashAttribute("message", "Пользователь был успешно добавлен в базу");

        return getRedirectURItoAffectedUser(user);
    }

    private String getRedirectURItoAffectedUser(UserDataDTO user) {
        String firstPartOfEmail = user.getEmail().split("@")[0];

        return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfEmail;
    }

    @GetMapping("/info/{id}")
    public String infoUser(@PathVariable(name = "id") Integer id, Model model,
                           RedirectAttributes redirectAttributes){
        try {
            User user = usersService.getById(id);
            ReaderResponse reader = personalDataService.getUserById(Long.valueOf(id));
            UserDataDTO userDataDTO = userDataMapper.toDto(user, reader);
            model.addAttribute("user", userDataDTO);
            model.addAttribute("pageTitle", "Информация о пользователе");

            return "users/user_info";
        } catch (ObjectInDBNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id, Model model,
                           RedirectAttributes redirectAttributes){
        try {
            User user = usersService.getById(id);
            ReaderResponse reader = personalDataService.getUserById(Long.valueOf(id));
            UserDataDTO userDataDTO = userDataMapper.toDto(user, reader);
            model.addAttribute("user", userDataDTO);
            model.addAttribute("listRoles", rolesService.getAllList());
            model.addAttribute("pageTitle", "Редактирование пользователя (ID: " + id + ")");

            return "users/user_form";
        } catch (ObjectInDBNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes){
        try {
            usersService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Запись с ID=" + id + " успешно удалена");
        } catch (ObjectInDBNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/users";
    }

    @GetMapping("/{id}/enabled/{status}")
    public String updateAvailableStatus(@PathVariable(name = "id")Integer id,
                                        @PathVariable(name = "status") boolean available,
                                        RedirectAttributes redirectAttributes){
        usersService.updateUserEnabledStatus(id, available);
        String status = available ? "доступен" : "недоступен";
        String message = "Пользователь с ID=" + id + " теперь " + status;

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/users";
    }

}
