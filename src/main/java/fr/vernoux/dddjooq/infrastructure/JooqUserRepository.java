package fr.vernoux.dddjooq.infrastructure;

import fr.vernoux.dddjooq.domain.User;
import fr.vernoux.dddjooq.domain.UserRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static fr.vernoux.dddjooq.infrastructure.types.tables.User.USER;

@Repository
public class JooqUserRepository implements UserRepository {

    private final DSLContext create;

    public JooqUserRepository(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public User findById(UUID id) {
        return create
                .fetchSingle(USER, USER.ID.eq(id))
                .into(User.class);
    }

    @Override
    public List<User> findAll() {
        return create
                .fetch(USER)
                .into(User.class);
    }

    @Override
    public void save(User user) {
        create
                .newRecord(USER, user)
                .merge();
    }
}
