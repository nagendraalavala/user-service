package com.perficient.service;

import javax.validation.Valid;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.perficient.service.dao.UserService;
import com.perficient.service.exception.DuplicateEntityException;
import com.perficient.service.types.User;

@RestController
@RequestMapping("/users")
public class UserAPIController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAPIController.class);

    private final UserService userService;

    public UserAPIController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * @param username of the user to search for.
     * @return a UserAPI object with the matching username, and password field set to null, or a 404 HTTP
     * status code if a matching user does not exist in the system.
     */
    @GetMapping("{username}")
    public ResponseEntity<?> getUser(final @PathVariable String username) {
        LOGGER.debug("Looking for user with username {}", username);
        final User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(final @RequestBody @Valid User user) {
        LOGGER.debug("Adding user with username {}.", user.getUsername());
        try {
            userService.save(user);
            LOGGER.info("Added user with username {}.", user.getUsername());
        } catch (DuplicateEntityException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(user.getUsername())
                .toUri()).build();
    }
    
    /**
     * @param username the username of the user to delete
     * @return an HTTP status code of 204 (NO_CONTENT) if the user was deleted or 404 (NOT_FOUND) if
     * the user did not exist in the system.
     */
    @DeleteMapping("{username}")
    public ResponseEntity<?> deleteUser(final @PathVariable String username) {
        LOGGER.debug("Deleting user with username {}.", username);
        return userService.delete(username) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
