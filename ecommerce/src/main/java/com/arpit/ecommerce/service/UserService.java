package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.UserRequestDTO;
import com.arpit.ecommerce.dto.UserResponseDTO;
import com.arpit.ecommerce.entity.User;
import com.arpit.ecommerce.repository.UserRepository;
import org.hibernate.internal.util.collections.ArrayHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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

    public List<UserResponseDTO> getUsers(){
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> responseDTOList = new ArrayList<>();
        for (User user:users){
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setName(user.getName());
            responseDTOList.add(dto);
        }
        return responseDTOList;
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

//    public User updateUser(Long id,User user)
    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO){
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser ==null) return null;
        else {
            existingUser.setName(requestDTO.getName());
            existingUser.setEmail(requestDTO.getEmail());
            existingUser.setPassword(requestDTO.getPassword());
            existingUser = userRepository.save(existingUser);

            UserResponseDTO responseDTO = new UserResponseDTO();
            responseDTO.setId(existingUser.getId());
            responseDTO.setName(existingUser.getName());
            responseDTO.setEmail(existingUser.getEmail());
            return responseDTO;
        }
    }
    public String deleteUser(Long id){
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null){
            return "user not found";
        }
        userRepository.deleteById(id);
        return "User deleted successfully having id: "+id;

    }
}
