package switchtwentytwenty.project.domain.model.interfaces;

public interface AggregateRoot<T extends Id> extends Entity {

    boolean hasId(T id);
}
