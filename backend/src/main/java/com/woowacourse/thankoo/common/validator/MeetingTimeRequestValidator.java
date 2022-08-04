package com.woowacourse.thankoo.common.validator;

import com.woowacourse.thankoo.common.validator.annotations.ValidMeetingTime;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import java.time.LocalDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MeetingTimeRequestValidator implements ConstraintValidator<ValidMeetingTime, ReservationRequest> {

    @Override
    public void initialize(final ValidMeetingTime constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final ReservationRequest value, final ConstraintValidatorContext context) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(value.getStartAt()) || now.getYear() != value.getStartAt().getYear()) {
            return false;
        }
        return true;
    }
}
