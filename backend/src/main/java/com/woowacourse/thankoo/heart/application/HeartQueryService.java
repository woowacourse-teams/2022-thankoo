package com.woowacourse.thankoo.heart.application;

import static java.util.stream.Collectors.toList;

import com.woowacourse.thankoo.heart.domain.HeartQueryRepository;
import com.woowacourse.thankoo.heart.domain.MemberHeart;
import com.woowacourse.thankoo.heart.presentation.dto.ReceivedHeartResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeartQueryService {

    private final HeartQueryRepository heartQueryRepository;

    public List<ReceivedHeartResponse> getReceivedHeart(final Long memberId) {
        List<MemberHeart> memberHearts = heartQueryRepository.findByReceiverIdAndIsFinal(memberId, true);
        return memberHearts.stream()
                .map(ReceivedHeartResponse::from)
                .collect(toList());
    }
}
