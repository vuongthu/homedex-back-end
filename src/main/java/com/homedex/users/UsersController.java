package com.homedex.users;

import com.homedex.users.models.LoginRequest;
import com.homedex.users.models.User;
import com.homedex.users.models.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.createUser(request.username(), request.email()));
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.findUser(request.username(), request.password()));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getUsers());
    }

    @GetMapping("{user-id}")
    public ResponseEntity<User> getUser(@PathVariable("user-id") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getUserById(userId));
    }

    @DeleteMapping("{user-id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user-id") UUID userId) {
        usersService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{user-id}")
    public ResponseEntity<Void> updateUserInfo(@RequestBody UserRequest request, @PathVariable("user-id") UUID userId) {
        usersService.updateUserInfo(request.username(), request.email(), userId);
        return ResponseEntity.noContent().build();
    }
}

