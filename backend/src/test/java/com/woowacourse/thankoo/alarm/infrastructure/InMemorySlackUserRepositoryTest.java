package com.woowacourse.thankoo.alarm.infrastructure;

import static com.woowacourse.thankoo.alarm.infrastructure.SlackUserFixture.HOHO;
import static com.woowacourse.thankoo.alarm.infrastructure.SlackUserFixture.HUNI;
import static com.woowacourse.thankoo.alarm.infrastructure.SlackUserFixture.LALA;
import static com.woowacourse.thankoo.alarm.infrastructure.SlackUserFixture.SKRR;
import static com.woowacourse.thankoo.alarm.infrastructure.SlackUserFixture.SKRR_USER_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("InMemorySlackUserRepository 는 ")
@ExtendWith(MockitoExtension.class)
class InMemorySlackUserRepositoryTest {

    private static final int EMAIL = 0;
    private static final int USER_TOKEN = 1;

    @Mock
    private SlackClient client;

    @Test
    @DisplayName("저장소에 토큰이 있는 경우 클라이언트를 호출하지 않고 토큰을 조회한다.")
    void findUserTokenInStore() {
        Map<String, String> store = givenStore(HOHO, HUNI, LALA, SKRR);
        InMemorySlackUserRepository repository = new InMemorySlackUserRepository(store, client);

        String token = repository.findUserToken(SKRR_EMAIL);

        verify(client, never()).getUserToken(SKRR_EMAIL);
        assertThat(token).isEqualTo(SKRR_USER_TOKEN);
    }

    @Test
    @DisplayName("저장소에 토큰이 없는 경우 클라이언트를 호출하여 토큰을 조회한다.")
    void findUserTokenInClient() {
        Map<String, String> store = givenStore(HOHO, HUNI, LALA);
        InMemorySlackUserRepository repository = new InMemorySlackUserRepository(store, client);

        when(client.getUserToken(SKRR_EMAIL)).thenReturn(SKRR_USER_TOKEN);

        String token = repository.findUserToken(SKRR_EMAIL);

        verify(client, times(1)).getUserToken(SKRR_EMAIL);
        assertThat(token).isEqualTo(SKRR_USER_TOKEN);
    }

    @Test
    @DisplayName("클라이언트를 호출하여 토큰을 조회했을 때 반환값이 null인 경우 예외를 발생한다.")
    void findUserTokenInClientIsNull() {
        Map<String, String> store = givenStore(HOHO, HUNI, LALA);
        InMemorySlackUserRepository repository = new InMemorySlackUserRepository(store, client);

        when(client.getUserToken(SKRR_EMAIL)).thenReturn(null);

        assertThatThrownBy(() -> repository.findUserToken(SKRR_EMAIL))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("알람이 불가능한 이메일입니다.");
        verify(client, times(1)).getUserToken(SKRR_EMAIL);
    }

    private Map<String, String> givenStore(String[]... users) {
        Map<String, String> store = new ConcurrentHashMap<>();
        for (String[] user : users) {
            store.put(user[EMAIL], user[USER_TOKEN]);
        }
        return store;
    }
}
