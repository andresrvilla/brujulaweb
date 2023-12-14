package domain.brujulaweb.usecases;

import domain.brujulaweb.entities.user.AuthRequest;
import domain.brujulaweb.entities.user.AuthResponse;
import domain.brujulaweb.entities.user.User;
import domain.brujulaweb.exceptions.UnauthorizedException;
import domain.brujulaweb.repository.UserRepository;
import domain.brujulaweb.security.TokenManager;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UserManagementUseCase {

    private final TokenManager tokenManager;

    private final UserRepository userRepository;

    public AuthResponse signup (AuthRequest request){
        String email = request.getEmail();
        String password = request.getPassword();
        User user = userRepository.signup(email, password);
        String userId = user.getUserId();
        String token = tokenManager.issueToken(userId);
        return new AuthResponse(userId, token);
    }

    public AuthResponse login (AuthRequest request){
        String email = request.getEmail();
        String password = request.getPassword();
        Optional<User> result = userRepository.findByEmail(email);
        if (result.isPresent()){
            User user = result.get();
            String passwordInDatabase = user.getPassword();
            if (password.equalsIgnoreCase(passwordInDatabase)) {
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
