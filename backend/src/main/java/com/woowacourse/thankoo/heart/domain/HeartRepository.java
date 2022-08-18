package com.woowacourse.thankoo.heart.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Heart> findBySenderIdAndLast(Long senderId, boolean last);

    List<Heart> findByReceiverIdAndLast(Long receiverId, boolean last);
}
