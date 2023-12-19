package net.brujulaweb.util;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import domain.brujulaweb.util.Encrypter;

public final class BcryptEncrypter implements Encrypter {
    @Override
    public String encrypt(String password) {
        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

        Hash hash = Password.hash(password)
                .addPepper("shared-secret")
                .with(bcrypt);

        return hash.getResult();
    }

    @Override
    public boolean verify(String password, String hash){
        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

        return Password.check(password, hash)
                .addPepper("shared-secret")
                .with(bcrypt);
    }
}
