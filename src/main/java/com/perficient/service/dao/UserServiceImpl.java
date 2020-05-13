package com.perficient.service.dao;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.perficient.service.exception.DuplicateEntityException;
import com.perficient.service.types.User;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,  final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void save(final User user) throws DuplicateEntityException {
        if (user == null) throw new IllegalArgumentException("User parameter must not be null.");
        if (StringUtils.isEmpty(user.getUsername())) throw new IllegalArgumentException("Username field must be neither null nor empty.");
        if (StringUtils.isEmpty(user.getPassword())) throw new IllegalArgumentException("Password field must be neither null nor empty.");

        final User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null) throw new DuplicateEntityException("Username " + user.getUsername() + " already exists.");

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setDateCreated(new Date());
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(final String username) {
        if (StringUtils.isEmpty(username)) throw new IllegalArgumentException("Username parameter may neither be null nor empty.");
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public boolean delete(final String username) {
        if (StringUtils.isEmpty(username)) throw new IllegalArgumentException("Username parameter may neither be null nor empty.");
        final User user = userRepository.findByUsername(username);
        if (user == null)  {
            LOGGER.debug("Could not delete user {} as it does not exist", username);
        }
        else {
            LOGGER.debug("Found user {} to delete", username);
            userRepository.delete(user);
            LOGGER.info("Deleted user {}", username);
        }
        return user != null;
    }

}