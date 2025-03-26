package ru.jabka.filmplus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Friend;
import ru.jabka.filmplus.repository.FriendRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FriendServiceTest {

    @Mock
    private FriendRepository friendRepository;

    @InjectMocks
    private FriendService friendService;

    @Test
    void addFriend_valid() {
        Friend friend = getFriend();
        Mockito.when(friendRepository.insert(friend)).thenReturn(friend);

        Friend result = friendService.create(friend);

        assertThat(result).isEqualTo(friend);
        verify(friendRepository.insert(friend));
    }

    @Test
    void addFriend_withInvalidData_sameUserId_throwsBadRequestException() {
        Friend friend = getFriend();
        friend.setFriendId(friend.getUserId());

        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> friendService.create(friend)
        );

        assertEquals("Невозможно добавить себя в друзья!", exception.getMessage());
        verify(friendRepository, never()).insert(any());
    }

    private Friend getFriend() {
        return Friend.builder()
                .friendId(2L)
                .userId(1L)
                .build();
    }
}