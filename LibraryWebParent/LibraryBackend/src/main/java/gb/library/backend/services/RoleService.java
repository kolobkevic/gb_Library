package gb.library.backend.services;

import gb.library.backend.repositories.RolesRepository;
import gb.library.common.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RolesRepository repository;

    public Role getUserRoleByName(String roleName){
        return repository.findRoleByName(roleName);
    }
}
