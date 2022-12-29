package com.numble.backend.user.auth.init;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

import com.numble.backend.user.auth.application.RoleHierarchyService;

@Component
@AllArgsConstructor
public class SecurityInitializer implements ApplicationRunner {

	private RoleHierarchyService roleHierarchyService;
	private RoleHierarchyImpl roleHierarchy;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String allHierarchy = roleHierarchyService.AllHierarchyToString();
		roleHierarchy.setHierarchy(allHierarchy);
	}
}
