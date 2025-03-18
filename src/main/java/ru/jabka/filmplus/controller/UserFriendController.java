package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.service.UserService;

@RestController
@RequestMapping("/api/v1/friends")
@Tag(name = "Добавление пользователей в друзья")
public class UserFriendController {

    private final UserService userService;

    public UserFriendController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    @Operation(summary = "Добавить пользователя в друзья")
    public User addUserToFriend(@RequestBody final Long userId, @RequestBody final Long userFriendId) {
        return userService.addUserToFriend(userId, userFriendId);
    }
}
