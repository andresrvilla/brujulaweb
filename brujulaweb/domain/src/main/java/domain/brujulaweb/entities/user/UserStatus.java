package domain.brujulaweb.entities.user;

public enum UserStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED;

    public static UserStatus lookup(String status){
        for(UserStatus v : values()){
            if( v.name().equalsIgnoreCase(status)){
                return v;
            }
        }
        return null;
    }
}
