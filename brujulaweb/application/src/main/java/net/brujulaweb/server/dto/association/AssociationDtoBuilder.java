package net.brujulaweb.server.dto.association;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import domain.brujulaweb.entities.association.Association;

import java.util.List;

public class AssociationDtoBuilder {

    private final ObjectMapper objectMapper;

    @Inject
    public AssociationDtoBuilder(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public AssociationDto build(Association association) throws JsonProcessingException {
        List<AssociationGroupDto> groups = objectMapper.readValue(association.getTaxonomy(), new TypeReference<>(){});
        return AssociationDto.builder()
                .id(association.getId())
                .name(association.getName())
                .groups(groups)
                .build();

    }
}
