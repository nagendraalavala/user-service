package com.perficient.service.dao;

import com.perficient.service.exception.DuplicateEntityException;
import com.perficient.service.types.User;

public interface UserService {
    void save(User user) throws DuplicateEntityException;

    User findByUsername(String username);

    Iterable<User> getAll();

    boolean delete(String username);
}

