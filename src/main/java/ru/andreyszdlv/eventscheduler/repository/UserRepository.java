package ru.andreyszdlv.eventscheduler.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.andreyszdlv.eventscheduler.model.User;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public boolean existsByEmail(String email) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email=?",
                new Object[]{email},
                Integer.class
        );
        return count != null && count > 0;
    }

    public User save(User user){
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole().name());
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE email=?",
                new Object[]{email},
                new BeanPropertyRowMapper<>(User.class)
        ));
    }
}
