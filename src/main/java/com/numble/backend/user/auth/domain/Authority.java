package com.numble.backend.user.auth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.numble.backend.user.auth.vo.Role;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority implements GrantedAuthority {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	@Enumerated(value = EnumType.STRING)
	private Role authority;

	public Authority(Role authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority.getRole();
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "userRoles")
	private Set<CustomUser> customUserDetails = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Authority authority1 = (Authority) o;
		return getAuthority() == authority1.getAuthority();
	}

	// 객체 비교 시간 단축
	@Override
	public int hashCode() {
		return Objects.hash(getAuthority());
	}
}
