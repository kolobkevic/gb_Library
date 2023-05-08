package gb.library.common;

import gb.library.common.exceptions.ObjectInDBNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

public interface AbstractDaoService <E, K>{

    public List<E> getAllList();

    public E getById(K id) throws ObjectInDBNotFoundException;

    public E create(E entity);

    @Transactional
    public E update(E entity) throws ObjectInDBNotFoundException;

    public void delete(K id) throws ObjectInDBNotFoundException;
}
