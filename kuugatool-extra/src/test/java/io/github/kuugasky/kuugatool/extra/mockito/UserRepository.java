package io.github.kuugasky.kuugatool.extra.mockito;

import java.util.Optional;

/**
 * UserRepository
 *
 * @author kuuga
 * @since 2023/12/8 23:41
 */
public interface UserRepository {
    Optional<User> findById(String id);
}

