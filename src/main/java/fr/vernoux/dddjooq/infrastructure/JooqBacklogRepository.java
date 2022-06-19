package fr.vernoux.dddjooq.infrastructure;

import fr.vernoux.dddjooq.domain.Backlog;
import fr.vernoux.dddjooq.domain.BacklogRepository;
import fr.vernoux.dddjooq.domain.UserStory;
import fr.vernoux.dddjooq.infrastructure.types.tables.records.UserStoryRecord;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static fr.vernoux.dddjooq.infrastructure.types.Keys.UNIQ_BACKLOG_ID_NAME;
import static fr.vernoux.dddjooq.infrastructure.types.tables.Backlog.BACKLOG;
import static fr.vernoux.dddjooq.infrastructure.types.tables.UserStory.USER_STORY;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

@Repository
public class JooqBacklogRepository implements BacklogRepository {

    private final DSLContext create;

    public JooqBacklogRepository(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public Backlog findById(UUID id) {
        return create
                .select(
                        BACKLOG.asterisk(),
                        multiset(
                                select(
                                        USER_STORY.NAME,
                                        USER_STORY.COMPLEXITY
                                )
                                        .from(USER_STORY)
                                        .where(USER_STORY.BACKLOG_ID.eq(BACKLOG.ID))
                        ).as("userStories").convertFrom(r -> r.into(UserStory.class))
                )
                .from(BACKLOG)
                .where(BACKLOG.ID.eq(id))
                .fetchSingleInto(Backlog.class);
    }

    @Override
    public List<Backlog> findAll() {
        return create
                .select(
                        BACKLOG.asterisk(),
                        multiset(
                                select(
                                        USER_STORY.NAME,
                                        USER_STORY.COMPLEXITY
                                )
                                        .from(USER_STORY)
                                        .where(USER_STORY.BACKLOG_ID.eq(BACKLOG.ID))
                        ).as("userStories").convertFrom(r -> r.into(UserStory.class))
                )
                .from(BACKLOG)
                .fetchInto(Backlog.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void save(Backlog backlog) {
        UUID backlogId;
        List<UserStory> userStories;
        try {
            backlogId = (UUID) FieldUtils.readField(backlog, "id", true);
            userStories = (List<UserStory>) FieldUtils.readField(backlog, "userStories", true);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unable to save backlog", e);
        }
        create.newRecord(BACKLOG, backlog).merge();
        create.batched(cfg ->
                userStories.forEach(userStory -> {
                    UserStoryRecord record = cfg.dsl().newRecord(USER_STORY, userStory);
                    record.setBacklogId(backlogId);
                    cfg.dsl()
                            .insertInto(USER_STORY)
                            .set(record)
                            .onConflictOnConstraint(UNIQ_BACKLOG_ID_NAME)
                            .doUpdate()
                            .set(record)
                            .execute();
                })
        );
    }
}
