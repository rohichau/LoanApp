package com.aspire.loan.main.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.aspire.loan.main.model.User;
import com.aspire.loan.main.repository.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById_ExistingId_ReturnsUser() {
        User user = new User(1L, "John Doe", "johndoe@example.com", "password");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);
        assertThat(result).isNotNull();

        User retrievedUser = result;
        assertThat(retrievedUser.getId()).isEqualTo(1L);
        assertThat(retrievedUser.getName()).isEqualTo("John Doe");
        assertThat(retrievedUser.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(retrievedUser.getPassword()).isEqualTo("password");
    }

    @Test
    void testGetUserById_NonExistingId_ReturnsEmptyOptional() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        User result = userService.getUserById(2L);
        assertThat(result).isNull();
    }

    @Test
    void testGetAllUsers_ReturnsAllUsers() {
        User user1 = new User(1L, "John Doe", "johndoe@example.com", "password");
        User user2 = new User(2L, "Jane Smith", "janesmith@example.com", "password");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userService.getAllUsers();
        assertThat(result).hasSize(2);

        User retrievedUser1 = result.get(0);
        assertThat(retrievedUser1.getId()).isEqualTo(1L);
        assertThat(retrievedUser1.getName()).isEqualTo("John Doe");
        assertThat(retrievedUser1.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(retrievedUser1.getPassword()).isEqualTo("password");

        User retrievedUser2 = result.get(1);
        assertThat(retrievedUser2.getId()).isEqualTo(2L);
        assertThat(retrievedUser2.getName()).isEqualTo("Jane Smith");
        assertThat(retrievedUser2.getEmail()).isEqualTo("janesmith@example.com");
        assertThat(retrievedUser2.getPassword()).isEqualTo("password");
    }

    @Test
    void testCreateUser_ReturnsCreatedUser() {
        User user = new User(null, "John Doe", "johndoe@example.com", "password");
        User createdUser = new User(1L, "John Doe", "johndoe@example.com", "password");

        when(userRepository.save(any(User.class))).thenReturn(createdUser);

        User result = userService.registerUser(user);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(result.getPassword()).isEqualTo("password");
    }

    @Test
    void testUpdateUser_ExistingId_ReturnsUpdatedUser() {
        User user = new User(1L, "John Doe", "johndoe@example.com", "password");
        User updatedUser = new User(1L, "John Doe", "johndoe@example.com", "newpassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
      //  when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, user);
        assertThat(result).isNotNull();

        User retrievedUser = result;
        assertThat(retrievedUser.getId()).isEqualTo(1L);
        assertThat(retrievedUser.getName()).isEqualTo("John Doe");
        assertThat(retrievedUser.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(retrievedUser.getPassword()).isEqualTo("newpassword");
    }

    @Test
    void testUpdateUser_NonExistingId_ReturnsEmptyOptional() {
        User user = new User(2L, "Jane Smith", "janesmith@example.com", "password");

		when(userRepository.findById(2L)).thenReturn(Optional.empty());

        User result = userService.updateUser(2L, user);
        assertThat(result).isNull();
    }

    @Test
    void testDeleteUser_ExistingId_DeletesUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
