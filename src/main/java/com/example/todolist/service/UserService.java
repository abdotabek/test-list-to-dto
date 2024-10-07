package com.example.todolist.service;

import com.example.todolist.dto.UserDTO;
import com.example.todolist.entity.User;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO addUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userRepository.save(user);
        return toUser(user);

    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found {с таким Id user не найден, не получится обновить!}")
        );
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userRepository.save(user);
        return toUser(user);
    }

    public UserDTO getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User with id" + id + "not found {с таким Id user не найден}")
        );
        return toUser(user);
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(this::toUser).toList();
    }

    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return;
        }
        throw new RuntimeException("User with id " + id + " not found {с таким Id user не найден, не получится удалить!}");
    }


    public UserDTO toUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }
}
