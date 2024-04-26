package gb.library.backend.admin.roles;

import gb.library.backend.admin.utils.CheckUniqueResponseStatusHelper;
import gb.library.backend.admin.utils.paging.PagingAndSortingHelper;
import gb.library.common.AbstractDaoService;
import gb.library.common.entities.Role;
import gb.library.common.enums.RoleType;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesService implements AbstractDaoService<Role, Integer> {
    private final RolesRepository repository;

    private static final int ROLES_PER_PAGE = 6;

    @Override
    public List<Role> getAllList() {
        return repository.findAll();
    }

    @Override
    public Role getById(Integer id) throws ObjectInDBNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectInDBNotFoundException("Запись с id=" + id + " в базе не найдена.",
                        "Roles"));
    }

    @Override
    public Role create(Role entity) {
        return repository.save(entity);
    }

    @Override
    public Role update(Role entity) throws ObjectInDBNotFoundException {
        Role existedRole = repository.findById(entity.getId())
                .orElseThrow(() -> new ObjectInDBNotFoundException("Невозможно обновить запись с id="
                        + entity.getId()
                        + ", т.к. она не найдена в базе.",
                        "Role"));
        existedRole.setRoleType(entity.getRoleType());
        existedRole.setName(entity.getName());
        existedRole.setDescription(entity.getDescription());

        return repository.save(existedRole);
    }

    @Override
    public void delete(Integer id) throws ObjectInDBNotFoundException {
        if (!repository.existsById(id)) {
            throw new ObjectInDBNotFoundException("Невозможно удалить запись с id=" + id
                    + ", т.к. она не найдена в базе.", "Role");
        }
        repository.deleteById(id);
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, ROLES_PER_PAGE, repository);
    }

    @Transactional
    public void save(Role role){
        if (role.getId() == null) {
            create(role);
        } else {
            update(role);
        }
    }

    public String checkUnique(Integer id, String name, String type) {

        try {
            RoleType roleType = RoleType.valueOf(type);
            Role role = repository.findByNameAndRoleType(name, roleType);

            return CheckUniqueResponseStatusHelper.getCheckStatus(role, id);
        } catch (IllegalArgumentException ex) {
            return CheckUniqueResponseStatusHelper.ERROR;
        }

    }
}
