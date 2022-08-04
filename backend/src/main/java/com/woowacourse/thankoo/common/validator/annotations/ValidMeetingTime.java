package com.woowacourse.thankoo.common.validator.annotations;

import com.woowacourse.thankoo.common.validator.MeetingTimeRequestValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = MeetingTimeRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMeetingTime {

    String message() default "예약 시간을 다시 설정해주세요.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
