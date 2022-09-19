package com.woowacourse.thankoo.serial.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponSerialRepository extends JpaRepository<CouponSerial, Long> {

    @Query("SELECT count(c) > 0 FROM CouponSerial AS c WHERE c.serialCode.value IN (:codes)")
    boolean existsBySerialCodeValue(@Param("codes") List<String> codes);

    CouponSerial findBySerialCodeValue(String serialCode);
}
