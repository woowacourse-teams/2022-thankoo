package com.woowacourse.thankoo.common.support;

import com.woowacourse.thankoo.alarm.support.AlarmManager;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AlarmThreadClearExtension implements AfterEachCallback {

    @Override
    public void afterEach(final ExtensionContext context) {
        AlarmManager.clear();
    }
}
