package com.woowacourse.thankoo.heart.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    Optional<Heart> findBySenderIdAndReceiverIdAndOrganizationId(Long senderId, Long receiverId, Long organizationId);

    List<Heart> findByOrganizationIdAndSenderIdAndLast(Long organizationId, Long senderId, boolean last);

    List<Heart> findByOrganizationIdAndReceiverIdAndLast(Long organizationId, Long receiverId, boolean last);
}
