package com.example.estore.services;

import com.example.estore.dto.request.AddUserRequest;
import com.example.estore.dto.UserDTO;
import com.example.estore.dto.response.AuthenticationResponse;
import com.example.estore.entity.User;
import com.example.estore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO registerUser(AddUserRequest addUserRequest) {
        User user = new User();
        user.setUsername(addUserRequest.getUsername());
        user.setPassword(addUserRequest.getPassword()); // Consider hashing the password
        user.setEmail(addUserRequest.getEmail());
        user.setFirstName(addUserRequest.getFirstName());
        user.setLastName(addUserRequest.getLastName());
        user.setRole(addUserRequest.getRole()); // Set role to SELLER
        user.setActive(addUserRequest.isActive());

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    public User validateUser(String username, String password) {
        User user = findByUsername(username);

        if (user != null && checkPassword(user, password)) {
            return user;
        }

        return null;
    }

    public UserDTO login(String username, String password) {
        User user = findByUsername(username);

        if (user != null && checkPassword(user, password)) {
            return mapToDTO(user);
        }

        return null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setRole(user.getRole());
        userDTO.setActive(user.isActive());
        return userDTO;
    }

    public AuthenticationResponse mapToAuthenticationResponse(String token, User user) {
        return new AuthenticationResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.isActive()
        );
    }


     private Collection<GrantedAuthority> getAuthorities(User user) {
         return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
     }
}
