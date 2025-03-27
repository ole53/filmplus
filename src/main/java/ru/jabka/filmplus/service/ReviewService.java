package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final FilmService filmService;

    public Review create(final Review review) {
        validate(review);

        return reviewRepository.insert(review);
    }

    public Review update(final Review review) {
        validate(review);

        return reviewRepository.update(review);
    }

    private void validate(final Review review) {
        if (review.getUserId() == null) {
            throw new BadRequestException("Необходимо указать id пользователя!");
        }

        if (review.getFilmId() == null) {
            throw new BadRequestException("Необходимо указать id фильма!");
        }

        if (review.getComment() == null) {
            throw new BadRequestException("Необходимо указать комментарий!");
        }
        User userExists = userService.getById(review.getUserId());
        Film filmExists = filmService.getById(review.getFilmId());
        Review revExists = reviewRepository.exists(review);
    }
}