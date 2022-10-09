package com.woowacourse.thankoo.heart.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.heart.application.dto.HeartRequest;
import com.woowacourse.thankoo.heart.domain.Heart;
import com.woowacourse.thankoo.heart.domain.HeartRepository;
import com.woowacourse.thankoo.heart.exception.InvalidHeartException;
import com.woowacourse.thankoo.heart.presentation.dto.HeartResponses;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationMemberException;
import java.util.List;
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
    private final OrganizationRepository organizationRepository;

    public void send(final Long organizationId, final Long senderId, final HeartRequest heartRequest) {
        Member sender = getMember(senderId);
        Member receiver = getMember(heartRequest.getReceiverId());

        Organization organization = getOrganization(organizationId);
        validateOrganizationMembers(sender, receiver, organization);

        Optional<Heart> foundHeart = heartRepository.findBySenderIdAndReceiverId(sender.getId(), receiver.getId());
        Optional<Heart> foundOppositeHeart = heartRepository.findBySenderIdAndReceiverId(receiver.getId(),
                sender.getId());

        if (foundHeart.isEmpty()) {
            create(organization, sender, receiver, foundOppositeHeart);
            return;
        }
        Heart heart = getHeart(foundHeart);
        heart.send(getHeart(foundOppositeHeart));
    }

    private void validateOrganizationMembers(final Member sender,
                                             final Member receiver,
                                             final Organization organization) {
        if (!organization.containsMembers(List.of(sender, receiver))) {
            throw new InvalidOrganizationMemberException(ErrorType.NOT_JOINED_MEMBER_OF_ORGANIZATION);
        }
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private Organization getOrganization(final Long organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new InvalidOrganizationException(ErrorType.NOT_FOUND_ORGANIZATION));
    }

    private Heart getHeart(final Optional<Heart> heart) {
        return heart.orElseThrow(() -> new InvalidHeartException(ErrorType.NOT_FOUND_HEART));
    }

    private void create(final Organization organization,
                        final Member sender,
                        final Member receiver,
                        final Optional<Heart> foundOppositeHeart) {
        if (foundOppositeHeart.isPresent()) {
            heartRepository.save(
                    Heart.firstReply(organization.getId(), sender.getId(), receiver.getId(), foundOppositeHeart.get()));
            return;
        }
        heartRepository.save(Heart.start(organization.getId(), sender.getId(), receiver.getId()));
    }

    @Transactional(readOnly = true)
    public HeartResponses getEachHeartsLast(final Long memberId) {
        Member member = getMember(memberId);
        List<Heart> sentHeart = heartRepository.findBySenderIdAndLast(member.getId(), true);
        List<Heart> receivedHeart = heartRepository.findByReceiverIdAndLast(member.getId(), true);

        return HeartResponses.of(sentHeart, receivedHeart);
    }
}
