package gb.library.admin.users;

import gb.library.admin.roles.RolesRepository;
import gb.library.admin.utils.CheckUniqueResponseStatusHelper;
import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.common.AbstractDaoService;
import gb.library.common.entities.RegistrationType;
import gb.library.common.entities.Role;
import gb.library.common.entities.User;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService implements AbstractDaoService<User, Integer> {

    private final UsersRepository usersRepo;
    private final PasswordEncoder passwordEncoder;

    private static final int USERS_PER_PAGE = 10;

    @Override
    public List<User> getAllList() {
        return usersRepo.findAll();
    }


    @Override
    public User getById(Integer id) throws ObjectInDBNotFoundException {
        return usersRepo.findById(id)
                .orElseThrow(() -> new ObjectInDBNotFoundException("Запись с id=" + id + " в базе не найдена.",
                        "Users"));
    }

    @Override
    public User create(User entity) {
        entity.setEnabled(true);
        entity.setRegistrationType(RegistrationType.MANUAL);
        encodeUserPassword(entity);
        return usersRepo.save(entity);
    }

    @Override
    public User update(User entity) throws ObjectInDBNotFoundException {
        User existedUser = usersRepo.findById(entity.getId())
                            .orElseThrow(() -> new ObjectInDBNotFoundException("Невозможно обновить запись с id="
                                    + entity.getId()
                                    + ", т.к. она не найдена в базе.",
                                    "Users"));
        if (!entity.getPassword().isEmpty()){
            existedUser.setPassword(entity.getPassword());
            encodeUserPassword(existedUser);
        }
        existedUser.setRoles(entity.getRoles());
        existedUser.setFirstName(entity.getFirstName());
        existedUser.setLastName(entity.getLastName());

        return usersRepo.save(existedUser);
    }

    @Override
    public void delete(Integer id) throws ObjectInDBNotFoundException {
        if (!usersRepo.existsById(id)) {
            throw new ObjectInDBNotFoundException("Невозможно удалить запись с id=" + id
                    + ", т.к. она не найдена в базе.", "Users");
        }
        usersRepo.deleteById(id);
    }

    private void encodeUserPassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper){
        helper.listEntities(pageNum, USERS_PER_PAGE, usersRepo);
    }

    @Transactional
    public void save(User user){
        if (user.getId() == null){
            create(user);
        } else {
            update(user);
        }
    }

    @Transactional
    public void updateUserEnabledStatus(Integer id, boolean enabled){
        usersRepo.updateEnabledStatus(id, enabled, Instant.now());
    }

    public String checkUnique(Integer id, String email) {
        User user = usersRepo.findByEmail(email);

        return CheckUniqueResponseStatusHelper.getCheckStatus(user, id);
    }
}
