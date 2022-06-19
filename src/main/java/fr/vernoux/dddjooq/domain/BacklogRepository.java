package fr.vernoux.dddjooq.domain;

import java.util.List;
import java.util.UUID;

public interface BacklogRepository {

    Backlog findById(UUID id);

    List<Backlog> findAll();

    void save(Backlog backlog);
}
