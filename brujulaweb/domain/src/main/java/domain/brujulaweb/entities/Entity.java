package domain.brujulaweb.entities;

import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Entity {
    public int associationId;
    private ZonedDateTime creationDate;
    private int creationUser;
    private ZonedDateTime modificationDate;
    private int modificationUser;
    private RowStatus rowStatus;
}
