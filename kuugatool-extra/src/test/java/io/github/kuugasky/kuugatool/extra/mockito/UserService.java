package io.github.kuugasky.kuugatool.extra.mockito;

/**
 * UserService
 *
 * @author kuuga
 * @since 2023/12/8 23:41
 */
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserInfo(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
    }
}

