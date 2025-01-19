package ru.andreyszdlv.eventscheduler.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.andreyszdlv.eventscheduler.model.Event;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Repository
@RequiredArgsConstructor
public class EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public Event save(Event event) {

        String sql = "INSERT INTO events (title, description, category_id, author_id, time) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setInt(3, event.getCategoryId());
            ps.setLong(4, event.getAuthorId());
            ps.setTimestamp(5, Timestamp.valueOf(event.getTime()));
            return ps;
        }, keyHolder);

        event.setId(keyHolder.getKey().longValue());
        return event;
    }
}
