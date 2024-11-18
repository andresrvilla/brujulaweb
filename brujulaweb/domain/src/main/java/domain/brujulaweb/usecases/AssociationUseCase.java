package domain.brujulaweb.usecases;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import domain.brujulaweb.entities.association.Association;
import domain.brujulaweb.entities.user.User;
import domain.brujulaweb.repository.AssociationRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor(onConstructor = @__({ @Inject}))
@Singleton
public class AssociationUseCase {

    private final AssociationRepository associationRepository;

    public final Association get(User user){
        return associationRepository.getById(user.associationId);
    }
}
