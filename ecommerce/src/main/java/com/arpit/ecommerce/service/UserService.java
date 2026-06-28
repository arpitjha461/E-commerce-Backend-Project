package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.UserRequestDTO;
import com.arpit.ecommerce.dto.UserResponseDTO;
import com.arpit.ecommerce.entity.User;
import com.arpit.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

//    public User register(User user)
    public UserResponseDTO register (UserRequestDTO requestDTO){
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());

        user = userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getName());

        return userResponseDTO;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        UserResponseDTO responseDTO = new UserResponseDTO();
        if(user == null){
            return null;
        }
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setId(user.getId());
        return responseDTO;
    }

    public User updateUser(Long id,User user){
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null){
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setRole(user.getRole());

            return userRepository.save(existingUser);
        }
        return null;
    }
    public String deleteUser(Long id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return "User deleted successfully having id: "+id;
        }
        return "user not found";
    }
}
