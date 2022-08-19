package com.woowacourse.thankoo.coupon.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Coupon c SET c.couponStatus = :status WHERE c.id IN (:couponIds)")
    void updateCouponStatus(@Param("status") CouponStatus status, @Param("couponIds") List<Long> couponIds);
}
