package gb.library.backend.common.services;

import gb.library.backend.common.repositories.RolesRepository;
import gb.library.common.entities.Role;
import gb.library.common.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RolesRepository repository;

    public Role getUserRoleByRoleType(RoleType roleType){
        return repository.findRoleByRoleType(roleType);
    }
}
