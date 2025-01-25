package ru.andreyszdlv.eventscheduler.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.andreyszdlv.eventscheduler.dto.category.CategoryDto;
import ru.andreyszdlv.eventscheduler.model.Category;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public Category save(Category category) {
        String sql = "INSERT INTO categories (name, author_id) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, category.getName());
            ps.setLong(2, category.getAuthorId());
            return ps;
        }, keyHolder);

        category.setId(keyHolder.getKey().intValue());
        return category;
    }

    public Category update(Category category) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";

        jdbcTemplate.update(sql, category.getName(), category.getId());

        return category;
    }

    public Optional<Category> findById(long id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        "SELECT * FROM categories WHERE id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Category.class)
                )
        );
    }

    public boolean existsByName(String name) {

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM categories WHERE name = ?",
                new Object[]{name},
                Integer.class
        );

        return count != null && count > 0;
    }

    public boolean existsById(int id) {

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM categories WHERE name = ?",
                new Object[]{id},
                Integer.class
        );

        return count != null && count > 0;
    }

    public List<Category> findAll(){

        String sql = "SELECT * FROM categories";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    public List<Category> findAllByAuthorId(long authorId) {
        String sql = "SELECT * FROM categories WHERE author_id = ?";

        return jdbcTemplate.query(sql, new Object[]{authorId}, new BeanPropertyRowMapper<>(Category.class));
    }

    public void deleteById(int id) {
        String sql ="DELETE FROM categories WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
