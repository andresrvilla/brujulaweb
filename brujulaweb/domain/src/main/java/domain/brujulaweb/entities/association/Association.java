package domain.brujulaweb.entities.association;

import domain.brujulaweb.entities.RowStatus;
import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Association {
    private int id;
    private String name;
    private ZonedDateTime creationDate;
    private int creationUser;
    private ZonedDateTime modificationDate;
    private int modificationUser;
    private RowStatus status;
    private String taxonomy;
}
