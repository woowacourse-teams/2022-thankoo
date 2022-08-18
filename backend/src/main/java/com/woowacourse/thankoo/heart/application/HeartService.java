package com.woowacourse.thankoo.heart.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.heart.application.dto.HeartRequest;
import com.woowacourse.thankoo.heart.domain.Heart;
import com.woowacourse.thankoo.heart.domain.HeartRepository;
import com.woowacourse.thankoo.heart.exception.InvalidHeartException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;

    public void send(final Long senderId, final HeartRequest heartRequest) {
        Member sender = getMember(senderId);
        Member receiver = getMember(heartRequest.getReceiverId());
        Optional<Heart> foundHeart = heartRepository.findBySenderIdAndReceiverId(sender.getId(), receiver.getId());
        Optional<Heart> foundOppositeHeart = heartRepository.findBySenderIdAndReceiverId(receiver.getId(),
                sender.getId());

        if (foundHeart.isEmpty()) {
            create(sender, receiver, foundOppositeHeart);
            return;
        }
        Heart heart = getHeart(foundHeart);
        heart.send(getHeart(foundOppositeHeart));
    }

    private void create(final Member sender, final Member receiver, final Optional<Heart> foundOppositeHeart) {
        if (foundOppositeHeart.isPresent()) {
            heartRepository.save(Heart.firstReply(sender.getId(), receiver.getId(), foundOppositeHeart.get()));
            return;
        }
        heartRepository.save(Heart.start(sender.getId(), receiver.getId()));
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private Heart getHeart(final Optional<Heart> heart) {
        return heart.orElseThrow(() -> new InvalidHeartException(ErrorType.NOT_FOUND_HEART));
    }
}