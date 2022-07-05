package com.woowacourse.thankoo.coupon.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {

    List<CouponHistory> findByReceiverId(Long id);
}
