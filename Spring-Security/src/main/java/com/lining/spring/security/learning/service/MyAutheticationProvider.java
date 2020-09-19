package com.lining.spring.security.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyAutheticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailService userDetailService ;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //自定义密码校验

        System.out.println("开始自定义验证~~~~");
        System.out.println(authentication);

        UserDetails userDetails = userDetailService.loadUserByUsername(authentication.getName());

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(userDetails.getPassword(),authentication.getCredentials().toString())){
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
            return authenticationToken;
        }else{
            throw new BadCredentialsException("用户名或密码错误");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
