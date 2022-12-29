package com.numble.backend.user.auth.application;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.backend.user.auth.domain.RoleHierarchy;
import com.numble.backend.user.auth.repository.RoleHierarchyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

	private final RoleHierarchyRepository roleHierarchyRepository;

	@Transactional
	@Override
	public String AllHierarchyToString() {
		List<RoleHierarchy> rolesHierarchy = roleHierarchyRepository.findAll();

		Iterator<RoleHierarchy> itr = rolesHierarchy.iterator();
		StringBuffer concatedRoles = new StringBuffer();
		while (itr.hasNext()) {
			RoleHierarchy model = itr.next();
			if (model.getParentName() != null) {
				concatedRoles.append(model.getParentName().getChildName());
				concatedRoles.append(" > ");
				concatedRoles.append(model.getChildName());
				concatedRoles.append("\n");
			}
		}
		return concatedRoles.toString();
	}
}
