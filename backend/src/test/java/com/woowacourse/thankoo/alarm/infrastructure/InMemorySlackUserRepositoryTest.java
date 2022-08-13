package com.woowacourse.thankoo.alarm.infrastructure;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
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

@ExtendWith(MockitoExtension.class)
class InMemorySlackUserRepositoryTest {

    @Mock
    private SlackClient client;

    @Test
    @DisplayName("저장소에 토큰이 있는 경우 클라이언트를 호출하지 않고 토큰을 조회한다.")
    void findUserTokenInStore() {
        Map<String, String> store = new ConcurrentHashMap<>();
        store.put(HOHO_EMAIL, "UN1203OFJ12");
        store.put(HUNI_EMAIL, "UN3403OFJ12");
        store.put(LALA_EMAIL, "UN5603OFJ12");
        store.put(SKRR_EMAIL, "UN7830401");
        InMemorySlackUserRepository repository = new InMemorySlackUserRepository(store, client);

        String token = repository.findUserToken(SKRR_EMAIL);

        verify(client, never()).getUserToken(SKRR_EMAIL);
        assertThat(token).isEqualTo("UN7830401");
    }

    @Test
    @DisplayName("저장소에 토큰이 없는 경우 클라이언트를 호출하여 토큰을 조회한다.")
    void findUserTokenInClient() {
        Map<String, String> store = new ConcurrentHashMap<>();
        store.put(HOHO_EMAIL, "UN1203OFJ12");
        store.put(HUNI_EMAIL, "UN3403OFJ12");
        store.put(LALA_EMAIL, "UN5603OFJ12");
        InMemorySlackUserRepository repository = new InMemorySlackUserRepository(store, client);

        when(client.getUserToken(SKRR_EMAIL)).thenReturn("UN7830401");

        String token = repository.findUserToken(SKRR_EMAIL);

        verify(client, times(1)).getUserToken(SKRR_EMAIL);
        assertThat(token).isEqualTo("UN7830401");
    }

    @Test
    @DisplayName("클라이언트를 호출하여 토큰을 조회했을 때 반환값이 null인 경우 예외를 발생한다.")
    void findUserTokenInClientIsNull() {
        Map<String, String> store = new ConcurrentHashMap<>();
        store.put(HOHO_EMAIL, "UN1203OFJ12");
        store.put(HUNI_EMAIL, "UN3403OFJ12");
        store.put(LALA_EMAIL, "UN5603OFJ12");
        InMemorySlackUserRepository repository = new InMemorySlackUserRepository(store, client);

        when(client.getUserToken(SKRR_EMAIL)).thenReturn(null);

        assertThatThrownBy(() -> repository.findUserToken(SKRR_EMAIL))
                .hasMessage("알람이 불가능한 이메일입니다. 관리자에게 문의주세요.")
                .isInstanceOf(RuntimeException.class);
        verify(client, times(1)).getUserToken(SKRR_EMAIL);
    }
}
