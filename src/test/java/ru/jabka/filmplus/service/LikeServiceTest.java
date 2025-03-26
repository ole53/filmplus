package ru.jabka.filmplus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.repository.LikeRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeService likeService;

    @Test
    void addLike() {
        Like like = getLike();
        Mockito.when(likeRepository.insert(like)).thenReturn(like);

        Like result = likeService.create(like);

        assertThat(result).isEqualTo(like);
        verify(likeRepository.insert(like));
    }

    @Test
    void addLike_withInvalidData_nullFilm_throwsBadRequestException() {
        Like like = getLike();
        like.setFilmId(null);

        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> likeService.create(like)
        );

        assertEquals("Необходимо указать id фильма!", exception.getMessage());
        verify(likeRepository, never()).insert(any());
    }

    private Like getLike() {
        return Like.builder()
                .filmId(11L)
                .userId(3L)
                .build();
    }
}