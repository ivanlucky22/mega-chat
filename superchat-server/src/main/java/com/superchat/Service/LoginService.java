package com.superchat.Service;

import com.superchat.Entity.User;
import com.superchat.repository.UserRepository;

public class LoginService {



    private UserRepository userRepository= new UserRepository();

    public int authorised(final User user){
        return userRepository.exists(user);
    }
}
