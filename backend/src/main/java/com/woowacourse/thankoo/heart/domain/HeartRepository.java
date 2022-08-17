package com.woowacourse.thankoo.heart.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
