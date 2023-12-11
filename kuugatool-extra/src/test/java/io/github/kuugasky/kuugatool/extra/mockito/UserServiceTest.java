package io.github.kuugasky.kuugatool.extra.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * UserServiceTest
 *
 * @author kuuga
 * @since 2023/12/8 23:42
 */
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        // 是 Mockito 提供的一个方法，用于初始化测试类中的模拟对象（mocks）和间谍对象（spies）。
        // 这个方法会寻找在你的测试类中使用了 @Mock、@Spy 或 @InjectMocks 注解的字段，并为它们创建相应的模拟或间谍对象。
        // initMocks() 方法会查找用 @Mock 和 @InjectMocks 注解的字段。
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void testGetUserInfoSuccess() {
        // Given a mock repository that returns a user when the 'findById' method is called with '1'
        User user = new User("1", "John Doe");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        // When calling 'getUserInfo' on the service
        User result = userService.getUserInfo("1");

        // Then the returned user should match the expected one
        assertEquals(user, result);
    }

    @Test
    void testGetUserInfoFailure() {
        // Given a mock repository that does not find a user when the 'findById' method is called with '2'
        when(userRepository.findById("2")).thenReturn(Optional.empty());

        // When calling 'getUserInfo' on the service
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userService.getUserInfo("2"));

        // Then an IllegalArgumentException should be thrown with the expected message
        assertEquals("Invalid user ID: 2", exception.getMessage());
    }
}
