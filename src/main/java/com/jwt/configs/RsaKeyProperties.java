//package com.jwt.configs;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.GsonBuilderUtils;
//
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//
//
//
//@ConfigurationProperties(prefix = "rsa")
//public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey rsaPrivateKey) {
//}


package com.jwt.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


@ConfigurationProperties(prefix = "rsa")
public class RsaKeyProperties {

    private Resource publicKey;
    private Resource privateKey;

    private RSAPublicKey rsaPublicKey;
    private RSAPrivateKey rsaPrivateKey;

    @PostConstruct
    private void init() throws GeneralSecurityException, IOException {
        this.rsaPublicKey = getPublicKey(publicKey);
        this.rsaPrivateKey = getPrivateKey(privateKey);
    }

    private RSAPublicKey getPublicKey(Resource resource) throws GeneralSecurityException, IOException {
        String publicKeyPEM = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()), StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");
        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private RSAPrivateKey getPrivateKey(Resource resource) throws GeneralSecurityException, IOException {
        String privateKeyPEM = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()), StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public RSAPublicKey getRsaPublicKey() {
        return rsaPublicKey;
    }

    public RSAPrivateKey getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setPublicKey(Resource publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(Resource privateKey) {
        this.privateKey = privateKey;
    }
}
