package com.woowacourse.thankoo.heart.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.heart.application.dto.HeartSelectCommand;
import com.woowacourse.thankoo.heart.application.dto.HeartSendCommand;
import com.woowacourse.thankoo.heart.domain.Heart;
import com.woowacourse.thankoo.heart.domain.HeartRepository;
import com.woowacourse.thankoo.heart.exception.InvalidHeartException;
import com.woowacourse.thankoo.heart.presentation.dto.HeartExchangeResponse;
import com.woowacourse.thankoo.heart.presentation.dto.HeartResponses;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
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

    public void send(final HeartSendCommand heartSendCommand) {
        Member sender = getMember(heartSendCommand.getSenderId());
        Member receiver = getMember(heartSendCommand.getReceiverId());

        Organization organization = getOrganization(heartSendCommand.getOrganizationId());
        validateOrganizationMembers(sender, receiver, organization);

        Optional<Heart> foundHeart = heartRepository.findBySenderIdAndReceiverIdAndOrganizationId(sender.getId(),
                receiver.getId(),
                organization.getId());
        Optional<Heart> foundOppositeHeart = heartRepository.findBySenderIdAndReceiverIdAndOrganizationId(
                receiver.getId(),
                sender.getId(),
                organization.getId());

        if (foundHeart.isEmpty()) {
            create(organization, sender, receiver, foundOppositeHeart);
            return;
        }
        Heart heart = getHeart(foundHeart);
        heart.send(getHeart(foundOppositeHeart));
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private Organization getOrganization(final Long organizationId) {
        return organizationRepository.findWithMemberById(organizationId)
                .orElseThrow(() -> new InvalidOrganizationException(ErrorType.NOT_FOUND_ORGANIZATION));
    }

    private void validateOrganizationMembers(final Member sender,
                                             final Member receiver,
                                             final Organization organization) {
        if (!organization.containsMembers(List.of(sender, receiver))) {
            throw new InvalidOrganizationException(ErrorType.NOT_JOINED_MEMBER_OF_ORGANIZATION);
        }
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
    public HeartResponses getEachHeartsLast(final Long organizationId, final Long memberId) {
        Member member = getMember(memberId);
        Organization organization = getOrganization(organizationId);
        validateOrganizationMember(member, organization);

        List<Heart> sentHeart = heartRepository.findByOrganizationIdAndSenderIdAndLast(organization.getId(),
                member.getId(), true);
        List<Heart> receivedHeart = heartRepository.findByOrganizationIdAndReceiverIdAndLast(organization.getId(),
                member.getId(), true);

        return HeartResponses.of(sentHeart, receivedHeart);
    }

    private void validateOrganizationMember(final Member member, final Organization organization) {
        if (!organization.containsMember(member)) {
            throw new InvalidOrganizationException(ErrorType.NOT_JOINED_MEMBER_OF_ORGANIZATION);
        }
    }

    public HeartExchangeResponse getSentReceivedHeart(final HeartSelectCommand heartSelectCommand) {
        Heart sentHeart = getHeartByReceiverAndSender(heartSelectCommand.getSenderId(),
                heartSelectCommand.getReceiverId(),
                heartSelectCommand.getOrganizationId());

        Heart receivedHeart = getHeartByReceiverAndSender(heartSelectCommand.getReceiverId(),
                heartSelectCommand.getSenderId(),
                heartSelectCommand.getOrganizationId());

        return HeartExchangeResponse.of(sentHeart, receivedHeart);
    }

    private Heart getHeartByReceiverAndSender(final Long senderId, final Long receiverId, final Long orgaznizationId) {
        return heartRepository.findBySenderIdAndReceiverIdAndOrganizationId(senderId, receiverId, orgaznizationId)
                .orElseThrow(() -> new InvalidHeartException(ErrorType.NOT_FOUND_HEART));
    }
}
