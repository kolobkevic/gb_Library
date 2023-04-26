package gb.library.common;

import java.util.List;

public interface AbstractDaoService <E, K>{

    public List<E> getAllList();

    public E getById(K id);

    public E create(E entity);

    public E update(E entity);

    public void delete(K id);
}
