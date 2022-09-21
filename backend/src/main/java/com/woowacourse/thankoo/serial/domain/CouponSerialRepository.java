package com.woowacourse.thankoo.serial.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponSerialRepository extends JpaRepository<CouponSerial, Long> {

    Optional<CouponSerial> findBySerialCodeValue(String serialCode);
}
