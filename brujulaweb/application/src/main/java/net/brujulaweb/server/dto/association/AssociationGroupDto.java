package net.brujulaweb.server.dto.association;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssociationGroupDto {
    private int id;
    private String name;
    private String identifier;
    private Boolean canHavePeople;
    private String status;
    private String authority;
    private List<AssociationGroupDto> groups;
}
