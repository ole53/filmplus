package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class UserService {

    private static final HashSet<User> users = new HashSet<>();

    public User create(final User user) {
        validate(user);
        user.setId((long) users.size() + 1);
        users.add(user);
        return user;
    }

    public static User getById(final Long id) {
        final User user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        if (user == null) {
            throw new BadRequestException(String.format("Пользователь с id %d не найден", id));
        }
        return user;
    }

    public User update(final User user) {
        validate(user);
        final User existUser = getById(user.getId());
        if (existUser == null) {
            return null;
        }
        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
        existUser.setLogin(user.getLogin());
        existUser.setBirthday(user.getBirthday());
        return existUser;
    }

    public void delete(final Long id) {
        users.remove(getById(id));
    }

    private void validate(final User user) {
        if (user == null) {
            throw new BadRequestException("Введите информацию о пользователе");
        }
        if (!StringUtils.hasText(user.getName())) {
            throw new BadRequestException("Укажите имя пользователя!");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new BadRequestException("Необходимо указать адрес электронной почты пользователя!");
        }
        if (!StringUtils.hasText(user.getLogin())) {
            throw new BadRequestException("Необходимо указать логин!");
        }
        if (user.getBirthday() == null) {
            throw new BadRequestException("Необходимо указать дату рождения!");
        }
    }

    public User addUserToFriend(final Long userId, final Long userFriendId) {
        final User user = getById(userId);
        final User userFriend = getById(userFriendId);
        final ArrayList<Long> firstUserFriends;
        final ArrayList<Long> secondUserFriends;

        if (!user.getFriends().contains(userFriendId)){
            firstUserFriends = user.getFriends();
            firstUserFriends.add(userFriendId);
            user.setFriends(firstUserFriends);

            secondUserFriends = userFriend.getFriends();
            secondUserFriends.add(userId);
            userFriend.setFriends(secondUserFriends);
        } else {
            throw new BadRequestException("Пользователи находятся в друзьях!");
        }
        return user;
    }
}