package domain.brujulaweb.entities;

import domain.brujulaweb.entities.user.UserStatus;

public enum RowStatus {
    INCONSISTENT,
    ACTIVE,
    DELETED,
    UNDER_REVIEW;

    public static RowStatus lookup(String status){
        for(RowStatus v : values()){
            if( v.name().equalsIgnoreCase(status)){
                return v;
            }
        }
        return null;
    }
}
