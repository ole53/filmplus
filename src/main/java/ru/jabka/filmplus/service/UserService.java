package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.model.UserExtraInfo.Friend;

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

    public User getById(final Long id) {
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

    public User addUser(final User user, final User userAdd) {
        final User existUser = getById(user.getId());
        final User existUserAdd = getById(userAdd.getId());

        Friend friendFst = new Friend(existUser);
        Friend friendSec = new Friend(existUserAdd);

        if (!existUser.getFriends().contains(existUserAdd)){
            existUser.addFriend(friendSec);
            existUserAdd.addFriend(friendFst);
        } else {
            throw new BadRequestException("Пользователи находятся в друзьях!");
        }
        return existUser;
    }

    public User removeUser(final User user, final User userRemove) {
        final User existUser = getById(user.getId());
        final User existUserRemove = getById(userRemove.getId());

        if (existUser.getFriends().contains(existUserRemove)){
            existUser.getFriends().remove(existUserRemove);
            existUserRemove.getFriends().remove(existUser);
        } else {
            throw new BadRequestException("Пользователи не находятся друг у друга в друзьях!");
        }
        return existUser;
    }
}