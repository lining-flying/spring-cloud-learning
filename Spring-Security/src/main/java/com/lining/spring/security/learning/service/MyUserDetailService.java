package com.lining.spring.security.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义UserDetailService
 * 实现使用orm框架读取用户
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String sql =  String.format("select * from users where username = '%s'",username) ;

        List<UserDetails> list = jdbcTemplate.query(sql, new RowMapper<UserDetails>() {
            @Override
            public UserDetails mapRow(ResultSet resultSet, int i) throws SQLException {
                String sql = String.format("select authority from authorities where username = '%s'",username);

                List<String> authorities = jdbcTemplate.query(sql, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getString(1);
                    }
                });

                UserDetails user = new User(resultSet.getString(1),resultSet.getString(2),
                        authorities.stream().map(str -> new SimpleGrantedAuthority(str)).collect(Collectors.toList()));

                return user;
            }
        });

        return list == null ? null : list.get(0);
    }
}
