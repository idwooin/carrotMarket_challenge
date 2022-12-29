package com.numble.backend.user.auth.vo;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

import com.numble.backend.user.auth.domain.CustomUser;

@Getter
public class UserContext extends User {

	private CustomUser user;

	public UserContext(CustomUser user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), authorities);
		this.user = user;
	}
}
