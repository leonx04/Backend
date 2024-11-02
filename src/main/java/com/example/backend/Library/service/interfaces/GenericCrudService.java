package com.example.backend.Library.service.interfaces;

import java.util.List;
import java.util.Optional;

public interface GenericCrudService<T, ID> {
    T create(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    T update(T entity);


    boolean existsById(ID id);

    //Other
    void changeStatus(int id, int status);
}