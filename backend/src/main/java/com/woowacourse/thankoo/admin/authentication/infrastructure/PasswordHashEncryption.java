package com.woowacourse.thankoo.admin.authentication.infrastructure;

import com.woowacourse.thankoo.admin.authentication.domain.PasswordEncryption;
import com.woowacourse.thankoo.admin.authentication.exception.InvalidEncryptionAlgorithmException;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashEncryption implements PasswordEncryption {

    private final String salt;
    private final int iterationCount;
    private final int keyLength;
    private static final String PBKDF2_WITH_SHA1 = "PBKDF2WithHmacSHA1";

    public PasswordHashEncryption(@Value("${admin.encryption.pbkdf2.salt}") final String salt,
                                  @Value("${admin.encryption.pbkdf2.iteration-count}") final int iterationCount,
                                  @Value("${admin.encryption.pbkdf2.key-length}") final int keyLength) {
        this.salt = salt;
        this.iterationCount = iterationCount;
        this.keyLength = keyLength;
    }

    @Override
    public String encrypt(final String plainPassword) {
        try {
            KeySpec spec = new PBEKeySpec(plainPassword.toCharArray(), salt.getBytes(), iterationCount, keyLength);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBKDF2_WITH_SHA1);
            byte[] encodedPassword = keyFactory.generateSecret(spec)
                    .getEncoded();
            return Base64.encodeBase64String(encodedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InvalidEncryptionAlgorithmException(AdminErrorType.CANNOT_ENCRYPT_PASSWORD);
        }
    }
}
