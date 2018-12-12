package com.nam.crm.web.identity;

import java.util.List;

import org.springframework.security.core.authority.AuthorityUtils;

import com.nam.crm.model.jpa.User;

public class TokenUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -7596902770543017594L;
	private User user;

    //For returning a normal user
    public TokenUser(User user) {
        super( user.getUserId(), user.getPassword(), AuthorityUtils.createAuthorityList(TokenUtil.rolesArray(user.getUserRoles()) ));
        //super(user.getUserName(), user.getPassword(), true, true, true, true,  AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<String> getRoles() {
        return TokenUtil.rolesList(user.getUserRoles());
    }
    
}
