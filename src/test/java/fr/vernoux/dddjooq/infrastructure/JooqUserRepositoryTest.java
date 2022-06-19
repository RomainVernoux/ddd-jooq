package fr.vernoux.dddjooq.infrastructure;

import fr.vernoux.dddjooq.domain.User;
import fr.vernoux.dddjooq.domain.UserRepository;
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
public class JooqUserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void find_a_user_by_id() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test@example.com");
        userRepository.save(user);

        User storedUser = userRepository.findById(userId);

        assertThat(storedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void find_all_users() {
        UUID user1Id = UUID.randomUUID();
        UUID user2Id = UUID.randomUUID();
        User user1 = new User(user1Id, "test1@example.com");
        User user2 = new User(user2Id, "test2@example.com");
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> storedUsers = userRepository.findAll();

        assertThat(storedUsers).usingRecursiveComparison().isEqualTo(List.of(user1, user2));
    }

    @Test
    void save_is_an_upsert() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test@example.com");
        userRepository.save(user);

        assertThatNoException().isThrownBy(() -> userRepository.save(user));

        User storedUser = userRepository.findById(userId);
        assertThat(storedUser).usingRecursiveComparison().isEqualTo(user);
    }
}
