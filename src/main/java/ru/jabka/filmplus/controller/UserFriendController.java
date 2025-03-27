package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.Friend;
import ru.jabka.filmplus.service.FriendService;

@RestController
@RequestMapping("/api/v1/friends")
@Tag(name = "Добавление пользователей в друзья")
public class UserFriendController {

    private final FriendService friendService;

    public UserFriendController(final FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/add")
    @Operation(summary = "Добавить пользователя в друзья")
    public Friend addUserToFriend(@RequestBody final Friend friend) {
        return friendService.create(friend);
    }
}