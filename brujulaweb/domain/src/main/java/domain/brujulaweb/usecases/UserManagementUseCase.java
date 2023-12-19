package domain.brujulaweb.usecases;

import domain.brujulaweb.entities.user.AuthRequest;
import domain.brujulaweb.entities.user.AuthResponse;
import domain.brujulaweb.entities.user.User;
import domain.brujulaweb.entities.user.UserStatus;
import domain.brujulaweb.exceptions.UnauthorizedException;
import domain.brujulaweb.repository.UserRepository;
import domain.brujulaweb.security.TokenManager;
import domain.brujulaweb.util.Encrypter;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UserManagementUseCase {

    private final TokenManager tokenManager;

    private final Encrypter encrypter;

    private final UserRepository userRepository;

    public AuthResponse signup (AuthRequest request){
        String email = request.getEmail();
        String password = encrypter.encrypt(request.getPassword());
        String userId = userRepository.signup(email.trim(), password.trim(), UserStatus.ACTIVE.name());
        String token = tokenManager.issueToken(userId);
        return new AuthResponse(userId, token);
    }

    public AuthResponse login (AuthRequest request){
        String email = request.getEmail();
        String password = request.getPassword();
        User user = userRepository.findByEmail(email);
        if (user != null){
            String hash = user.getPassword();
            if(encrypter.verify(password, hash)) {
                String userId = user.getUserId();
                String token = tokenManager.issueToken(userId);
                return new AuthResponse(userId, token);
            } else {
                throw new UnauthorizedException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    public boolean authorize (String token, String userId) {
        return tokenManager.authorize(token, userId);
    }
}
