package fr.vernoux.dddjooq.domain;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    User findById(UUID id);

    List<User> findAll();

    void save(User user);
}
