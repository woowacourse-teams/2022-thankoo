package com.woowacourse.thankoo.admin.serial.application;

import com.woowacourse.thankoo.admin.serial.application.dto.SerialRequest;
import com.woowacourse.thankoo.admin.serial.domain.Serial;
import com.woowacourse.thankoo.admin.serial.domain.SerialRepository;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminSerialService {

    private final SerialRepository serialRepository;
    private final MemberRepository memberRepository;

    public Long save(final SerialRequest serialRequest) {
        Member coach = getMember(serialRequest.getCoachName());
        Serial serial = serialRequest.toEntity(coach.getId());
        return serialRepository.save(serial).getId();
    }

    private Member getMember(final String name) {
        return memberRepository.findByNameValue(name)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }
}
