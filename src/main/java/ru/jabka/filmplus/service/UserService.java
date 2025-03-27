package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.UserRepository;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(final User user) {
        validate(user);

        return userRepository.insert(user);
    }

    public User getById(final Long id) {
        return userRepository.getById(id);
    }

    public User update(final User user) {
        validate(user);

        return userRepository.update(user);
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
}