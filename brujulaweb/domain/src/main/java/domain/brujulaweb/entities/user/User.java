package domain.brujulaweb.entities.user;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {
    private String userId;
    private String email;
    private String password;
    private Date lastLogin;
    private UserStatus status;
    private Date lockoutDate;
    public Integer lockoutCount;
}
