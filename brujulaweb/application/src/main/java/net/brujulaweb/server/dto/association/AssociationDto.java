package net.brujulaweb.server.dto.association;

import com.google.inject.Inject;
import domain.brujulaweb.entities.association.Association;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociationDto {
    private int id;
    private String name;
    private List<AssociationGroupDto> groups;
}
