package net.brujulaweb.repository.association;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import domain.brujulaweb.entities.RowStatus;
import domain.brujulaweb.entities.association.Association;
import domain.brujulaweb.entities.user.User;
import domain.brujulaweb.entities.user.UserStatus;
import domain.brujulaweb.repository.AssociationRepository;
import net.brujulaweb.repository.DBRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static domain.brujulaweb.util.DateUtils.fromTimestamp;

@Singleton
public class AssociacionDBRepository extends DBRepository<Association> implements AssociationRepository {

    private static final Logger logger = LoggerFactory.getLogger(AssociacionDBRepository.class);
    private static final String GET_ASSOCIATION_BY_ID = "SELECT " +
            "id, name, creation_date, creation_user, modification_date, modification_user, status, taxonomy " +
            "FROM association " +
            "WHERE id = ? AND status = ? ";

    private static final String UPDATE_ASSOCIATION = "UPDATE association SET " +
            "name = ?, " +
            "modification_date = ?, " +
            "modification_user = ?, " +
            "status = ?, " +
            "taxonomy = ? " +
            "WHERE id = ?";
    private final Cache<String, Association> associationCache;

    @Inject
    public AssociacionDBRepository(DataSource dataSource, Cache<String, Association> associationCache) {
        super(dataSource);
        this.associationCache = associationCache;
    }

    @Override
    public Association getById(Integer id) {
        Association association = associationCache.getIfPresent(id.toString());
        if (Objects.isNull(association)) {
            association = executeSelect(GET_ASSOCIATION_BY_ID, this::handler, id, RowStatus.ACTIVE.name());
            associationCache.put(id.toString(), association);
            logger.info("Get association {} from database", association.getName());
        }

        return association;
    }


    @Override
    public Association update(int id, Association association) {

        executeUpdate(UPDATE_ASSOCIATION,
                association.getName(),
                association.getModificationDate(),
                association.getModificationUser(),
                association.getStatus().name(),
                association.getTaxonomy(),
                id);

        // Clear the cache for the updated association
        associationCache.invalidate(String.valueOf(id));

        // Return the updated association
        return getById(id);
    }

    private Association handler(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Association.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .creationDate(fromTimestamp(resultSet.getTimestamp("creation_date")))
                    .creationUser(resultSet.getInt("creation_user"))
                    .modificationDate(fromTimestamp(resultSet.getTimestamp("modification_date")))
                    .modificationUser(resultSet.getInt("modification_user"))
                    .status(RowStatus.lookup(resultSet.getString("status")))
                    .taxonomy(resultSet.getString("taxonomy"))
                    .build();
        }
        return null;
    }
}