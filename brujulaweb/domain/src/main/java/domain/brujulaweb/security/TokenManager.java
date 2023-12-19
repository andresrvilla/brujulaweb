package domain.brujulaweb.security;

public interface TokenManager {
    String issueToken (String password);

    boolean authorize (String token, String password);
}
