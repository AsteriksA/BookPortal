package com.gold.service.impl;

import com.gold.dto.User;
import com.gold.form.ChangePasswordForm;
import com.gold.form.UpdateUserForm;
import com.gold.model.State;
import com.gold.model.UserEntity;
import com.gold.repository.UserRepository;
import com.gold.service.interfaces.UserService;
import com.gold.util.EntityMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private final String email = "test@gm.com";
    private String userName = "testName";
    private String password = "password";
    private Long userId = 1L;

    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EntityMapper mapper;
    private UserEntity userEntity;
    private User user;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository, passwordEncoder, mapper);
        userEntity = new UserEntity();
        user = new User();
    }

    @Test
    public void successfulGetAllUsers() {
        List<UserEntity> entities = asList(new UserEntity(), new UserEntity());
        when(userRepository.findAll()).thenReturn(entities);
        when(mapper.convertToDto(entities, User.class)).thenReturn(asList(new User(), new User()));

        assertEquals(2, userService.findAll().size());
    }

    @Test
    public void successfulGetUserByEmail() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(mapper.convertToDto(userEntity, User.class)).thenReturn(user);

        assertNotNull(userService.findByEmail(email));
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldEntityNotFoundExceptionWithNotExistEmail() {
        when(userRepository.findByEmail(email)).thenThrow(EntityNotFoundException.class);
        userService.findByEmail(email);
    }

    @Test
    public void successfulGetUserByName() {
        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(userEntity));
        when(mapper.convertToDto(userEntity, User.class)).thenReturn(user);

        assertNotNull(userService.findByName(userName));
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldEntityNotFoundExceptionWithNotExistName() {
        when(userRepository.findByUsername(userName)).thenThrow(EntityNotFoundException.class);
        userService.findByName(userName);
    }

    @Test
    public void successfulGetUserById() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(mapper.convertToDto(userEntity, User.class)).thenReturn(user);

        assertNotNull(userService.findOne(userId));
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldEntityNotFoundExceptionWithNotExistId() {
        when(userRepository.findById(userId)).thenThrow(EntityNotFoundException.class);
        userService.findOne(userId);
    }

    @Test
    public void successfulDeleteUserById() {
        userService.remove(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void successfulUpdateUser() {
        String mail = "tt@gm.com";
        UpdateUserForm userForm = new UpdateUserForm();
        userForm.setEmail(mail);
        user.setEmail(mail);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(mapper.convertToDto(userEntity, User.class)).thenReturn(user);

        User user = userService.update(userId, userForm);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userEntity);

        assertEquals(mail, user.getEmail());
    }

    @Test
    public void successfulChangePasswordUser() {
        userEntity.setPassword(password);
        String newPassword = "newPassword";
        ChangePasswordForm passwordForm = new ChangePasswordForm(password, newPassword);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(password, password)).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);

        userService.changePassword(userId, passwordForm);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userEntity);

        assertEquals(newPassword, userEntity.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failedPasswordCheckTillChangePassword() {
        userEntity.setPassword(password);

        String wrongPassword = "wrongPassword";
        ChangePasswordForm passwordForm = new ChangePasswordForm(wrongPassword, null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(wrongPassword, password)).thenReturn(false);

        userService.changePassword(userId, passwordForm);
    }

    @Test
    public void successfulBannedUserById() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        
        userService.bannedById(userId);
        
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userEntity);
        
        assertEquals(State.BANNED, userEntity.getState());
    }
}