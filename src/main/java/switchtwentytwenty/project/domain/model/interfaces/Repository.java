package switchtwentytwenty.project.domain.model.interfaces;

public interface Repository<T extends Entity, K extends Id> {

    T getById(K id);
}
