package com.perficient.service.dao;

import java.util.Date;

import com.perficient.service.adaptor.CountryServiceAdaptor;
import com.perficient.service.adaptor.QuoateAdaptor;
import com.perficient.service.types.UserInfo;
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
    private final CountryServiceAdaptor serviceAdaptor;
    private QuoateAdaptor quoateAdaptor;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,  final BCryptPasswordEncoder bCryptPasswordEncoder,
                           final CountryServiceAdaptor serviceAdaptor, QuoateAdaptor quoateAdaptor) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.serviceAdaptor = serviceAdaptor;
        this.quoateAdaptor = quoateAdaptor;
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
        String countryCurrency = serviceAdaptor.getCountryCurrencyDetails(user.getCountryCode());
        String quote = quoateAdaptor.getQuote();
        userRepository.save(mapToUser(user,countryCurrency,quote));
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

    private UserInfo mapToUserInfo(User user) {
        UserInfo entityUser = new UserInfo();
        if(user != null) {
            entityUser.setUsername(user.getUsername());
            entityUser.setPassword(user.getPassword());
            entityUser.setDateCreated(user.getDateCreated());
            entityUser.setFirstName(user.getFirstName());
            entityUser.setLastName(user.getLastName());
            entityUser.setCountryCode(user.getCountryCode());
            entityUser.setCountryCurrency(user.getCountryCurrency());
            entityUser.setQuote(user.getQuote());
        }

        return entityUser;
    }

    private User mapToUser(User user, String currency, String quote) {
        User entityUser = new User();
        if(user != null) {
            entityUser.setUsername(user.getUsername());
            entityUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            entityUser.setDateCreated(new Date());
            entityUser.setFirstName(user.getFirstName());
            entityUser.setLastName(user.getLastName());
            entityUser.setCountryCode(user.getCountryCode());
            entityUser.setCountryCurrency(currency);
            entityUser.setQuote(quote);
        }
        return entityUser;
    }

    private Iterable<UserInfo> mapToUserInfoList(Iterable<User> userList) {

        return null;
    }



}