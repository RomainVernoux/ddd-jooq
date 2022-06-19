package fr.vernoux.dddjooq.infrastructure;

import fr.vernoux.dddjooq.domain.Backlog;
import fr.vernoux.dddjooq.domain.BacklogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
@Transactional
public class JooqBacklogRepositoryTest {

    @Autowired
    BacklogRepository backlogRepository;

    @Test
    void find_a_backlog_by_id() {
        UUID backlogId = UUID.randomUUID();
        Backlog backlog = Backlog.empty(backlogId, "my_backlog");
        backlog.addUserStory("US1", 1);
        backlog.addUserStory("US2", 2);
        backlogRepository.save(backlog);

        Backlog storedBacklog = backlogRepository.findById(backlogId);

        assertThat(storedBacklog).usingRecursiveComparison().isEqualTo(backlog);
    }

    @Test
    void find_all_backlogs() {
        UUID backlog1Id = UUID.randomUUID();
        UUID backlog2Id = UUID.randomUUID();
        Backlog backlog1 = Backlog.empty(backlog1Id, "my_backlog_1");
        backlog1.addUserStory("US1", 1);
        Backlog backlog2 = Backlog.empty(backlog2Id, "my_backlog_2");
        backlog1.addUserStory("US2", 2);
        backlog2.addUserStory("US3", 3);
        backlogRepository.save(backlog1);
        backlogRepository.save(backlog2);

        List<Backlog> storedBacklogs = backlogRepository.findAll();

        assertThat(storedBacklogs).usingRecursiveComparison().isEqualTo(List.of(backlog1, backlog2));
    }

    @Test
    void save_is_an_upsert() {
        UUID backlogId = UUID.randomUUID();
        Backlog backlog = Backlog.empty(backlogId, "my_backlog");
        backlog.addUserStory("US1", 1);
        backlogRepository.save(backlog);

        assertThatNoException().isThrownBy(() -> backlogRepository.save(backlog));

        Backlog storedBacklog = backlogRepository.findById(backlogId);
        assertThat(storedBacklog).usingRecursiveComparison().isEqualTo(backlog);
    }
}
