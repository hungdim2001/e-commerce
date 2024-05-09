package com.example.core.service;

import com.example.core.entity.User;
import com.example.core.repository.UserRepository;
import com.example.core.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder encoder;
    public User save(User user) {
       User userSave = userRepository.getById(user.getId());
       userSave.setAreaCode(user.getAreaCode());
       userSave.setFirstName(user.getFirstName());
       userSave.setLastName(user.getLastName());
     return userRepository.save(userSave);
    }
/*


    public String updatePassword(PasswordUpdate passwordUpdate, HttpServletRequest httpServletRequest) {
        String jwt = BaseUtils.parseJwt(httpServletRequest);
        Long id = Long.valueOf(jwtUtils.getIdFromJwtToken(jwt, true));
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, "Not  User with " + id)
        );
        boolean isValidPassword = encoder.matches(passwordUpdate.getOldPassword(), user.getPassword());
        if (!isValidPassword) {
            throw new InvalidLoginException(HttpStatus.UNAUTHORIZED, "Mật khẩu vừa nhập không đúng ");
        }
        String newPassword = encoder.encode(passwordUpdate.getNewPassword());
        userRepository.updatePassword(user.getId(), newPassword);
        return null;
    }*/


}
