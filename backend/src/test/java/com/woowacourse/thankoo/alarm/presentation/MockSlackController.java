package com.woowacourse.thankoo.alarm.presentation;

import static com.woowacourse.thankoo.alarm.infrastructure.SlackUserFixture.HOHO_USER_TOKEN;
import static com.woowacourse.thankoo.alarm.infrastructure.SlackUserFixture.HUNI_USER_TOKEN;
import static com.woowacourse.thankoo.alarm.infrastructure.SlackUserFixture.LALA_USER_TOKEN;
import static com.woowacourse.thankoo.alarm.infrastructure.SlackUserFixture.SKRR_USER_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;

import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackMessageRequest;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUserResponse;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUserResponse.Profile;
import com.woowacourse.thankoo.alarm.infrastructure.dto.SlackUsersResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockSlackController {

    @GetMapping(path = "/users/list")
    public ResponseEntity<SlackUsersResponse> getUsers(@RequestHeader("Authorization") String header) {
        List<SlackUserResponse> slackUsers = new ArrayList<>();
        slackUsers.add(new SlackUserResponse(HOHO_USER_TOKEN, new Profile(HOHO_EMAIL)));
        slackUsers.add(new SlackUserResponse(LALA_USER_TOKEN, new Profile(LALA_EMAIL)));
        slackUsers.add(new SlackUserResponse(HUNI_USER_TOKEN, new Profile(HUNI_EMAIL)));
        slackUsers.add(new SlackUserResponse(SKRR_USER_TOKEN, new Profile(SKRR_EMAIL)));
        return ResponseEntity.ok().body(new SlackUsersResponse(slackUsers));
    }

    @PostMapping(path = "/chat/postMessage")
    public ResponseEntity<Void> sendMessage(@RequestBody final SlackMessageRequest request) {
        return ResponseEntity.ok().build();
    }
}
