package domain.brujulaweb.usecases;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import domain.brujulaweb.entities.user.AuthRequest;
import domain.brujulaweb.entities.user.AuthResponse;
import domain.brujulaweb.entities.user.User;
import domain.brujulaweb.entities.user.UserStatus;
import domain.brujulaweb.exceptions.EntityConflictException;
import domain.brujulaweb.exceptions.UnauthorizedException;
import domain.brujulaweb.repository.UserRepository;
import domain.brujulaweb.security.TokenManager;
import domain.brujulaweb.util.DateUtils;
import domain.brujulaweb.util.Encrypter;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor(onConstructor = @__({ @Inject}))
@Singleton
public class UserManagementUseCase {

    private static final Integer MAX_LOCKOUT_COUNT = 3;
    private static final long LOCKOUT_MINUTES = 60L;

    private static final Logger logger = LoggerFactory.getLogger(UserManagementUseCase.class);

    private final TokenManager tokenManager;

    private final Encrypter encrypter;

    private final UserRepository userRepository;

    public AuthResponse signup(AuthRequest request) {
        String email = request.getEmail().trim();
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            throw new EntityConflictException();
        }
        String password = encrypter.encrypt(request.getPassword().trim());
        userRepository.signup(email, password, request.getAssociation());
        String token = tokenManager.issueToken(email);
        return new AuthResponse(email, token);
    }

    public AuthResponse login(AuthRequest request) {
        String email = request.getEmail().trim();
        String password = request.getPassword().trim();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (user.getStatus() != UserStatus.ACTIVE) {
                verifyLookout(user);
                throw new UnauthorizedException();
            }

            String hash = user.getPassword();
            if (encrypter.verify(password, hash)) {
                String token = tokenManager.issueToken(user.getEmail());
                userRepository.updateSignup(email, user.getUserId(), user.getStatus().name(), 0, null, DateUtils.now());
                return new AuthResponse(email, token);
            } else {
                logger.warn(String.format("User %s is trying to use an invalid password", email));
                verifyLookout(user);
                throw new UnauthorizedException();
            }
        } else {
            logger.warn(String.format("User %s does not exist or invalid status", email));
            throw new UnauthorizedException();
        }
    }

    private void verifyLookout(User user) {
        if (user.getLockoutCount() > MAX_LOCKOUT_COUNT && user.getLockoutDate().plusMinutes(LOCKOUT_MINUTES).isAfter(DateUtils.now())) {
            userRepository.updateSignup(user.getEmail(), user.getUserId(), UserStatus.BLOCKED.name(), user.getLockoutCount() + 1, DateUtils.now(), user.getLastLogin());
        } else {
            userRepository.updateSignup(user.getEmail(), user.getUserId(), user.getStatus().name(), (user.getLockoutCount() == null ? 0 : user.getLockoutCount()) + 1, DateUtils.now(), user.getLastLogin());
        }
    }

    public boolean authorize(String token, String userId) {
        boolean isAuthorized = tokenManager.authorize(token, userId);
        if(!isAuthorized){
            logger.warn(String.format("User %s is trying to use an invalid token", userId));
        }
        return isAuthorized;
    }

    public User findByEmail(String email) {
       return userRepository.findByEmail(email);
    }
}
