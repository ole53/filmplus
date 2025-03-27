package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Friend;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.FriendRepository;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserService userService;

    public Friend create(final Friend friend) {
        validate(friend);
        Friend userFriend = Friend.builder()
                .userId(friend.getFriendId())
                .friendId(friend.getUserId())
                .build();
        friendRepository.insert(userFriend);
        return friendRepository.insert(friend);
    }

    private void validate(final Friend friend) {
        if (friend.getUserId() == null) {
            throw new BadRequestException("Необходимо указать id пользователя, который добавляет в друзья!");
        }

        if (friend.getFriendId() == null) {
            throw new BadRequestException("Необходимо указать id добавляемого пользователя!");
        }

        if (friend.getUserId().equals(friend.getFriendId())) {
            throw new BadRequestException("Невозможно добавить себя в друзья!");
        }

        User userExists = userService.getById(friend.getUserId());
        User userFrExists = userService.getById(friend.getFriendId());
        Friend exists = friendRepository.exists(friend);
    }
}