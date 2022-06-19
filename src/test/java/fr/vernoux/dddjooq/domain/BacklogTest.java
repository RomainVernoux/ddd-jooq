package fr.vernoux.dddjooq.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BacklogTest {

    @Test
    void computes_total_complexity() {
        UUID backlogId = UUID.randomUUID();
        Backlog backlog = Backlog.empty(backlogId, "my_backlog");

        backlog.addUserStory("US1", 1);
        backlog.addUserStory("US2", 2);

        assertThat(backlog.totalComplexity()).isEqualTo(3);
    }
}
