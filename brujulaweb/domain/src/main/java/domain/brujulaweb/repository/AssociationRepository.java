package domain.brujulaweb.repository;

import domain.brujulaweb.entities.association.Association;

import java.util.List;

public interface AssociationRepository {
    Association getById(Integer id);

    Association update(int id, Association association);
}
