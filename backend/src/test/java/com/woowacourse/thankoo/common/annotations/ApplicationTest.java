package com.woowacourse.thankoo.common.annotations;

import com.woowacourse.thankoo.common.support.AlarmThreadClearExtension;
import com.woowacourse.thankoo.common.support.DataClearExtension;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({DataClearExtension.class, AlarmThreadClearExtension.class})
public @interface ApplicationTest {
}
