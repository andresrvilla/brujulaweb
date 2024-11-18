package domain.brujulaweb.entities.user;

import domain.brujulaweb.entities.Entity;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User extends Entity {
    private Integer userId;
    private String email;
    private String password;
    private ZonedDateTime lastLogin;
    private UserStatus status;
    private ZonedDateTime lockoutDate;
    public Integer lockoutCount;
}
