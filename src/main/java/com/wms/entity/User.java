package com.wms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"authorities"},ignoreUnknown = true)
public class User implements UserDetails {
    private Integer id;
    private String number;
    private String name;
    private String password;
    private Integer age;
    private Integer sex;
    private String phone;
    private Integer roleId;

    private Collection<SimpleGrantedAuthority> authorities;
    //权限设置
    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> collection =new HashSet<>();
        if(this.authorities!=null){
            return authorities;
        }
        if(this.roleId==null){
            //有一次请求调用这个函数的user值都是null，不清楚如何处理
            return collection;
        }
        if(this.roleId==0){
            SimpleGrantedAuthority simpleGrantedAuthority =new SimpleGrantedAuthority("superManager");
            collection.add(simpleGrantedAuthority);
            this.authorities=collection;
        }else {
            SimpleGrantedAuthority simpleGrantedAuthority =new SimpleGrantedAuthority("user");
            collection.add(simpleGrantedAuthority);
            this.authorities=collection;
        }
        return authorities;
    }

    /*
    * 基于我们的需求，简单设置方法返回值
    * */
    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
