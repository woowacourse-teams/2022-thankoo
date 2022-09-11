package com.woowacourse.thankoo.admin.coupon.domain;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminCouponRepository extends JpaRepository<Coupon, Long> {

}
