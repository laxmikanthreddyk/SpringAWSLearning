package com.mycom.SpringAWSLearning.backend.persistence.domain.backend;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.mycom.SpringAWSLearning.enums.RolesEnum;

@Entity	
public class Role implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	
	private String name;
	
	public Role()
	{
		
	}
	
	@OneToMany(mappedBy="role", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	 private Set<UserRole> userRoles = new HashSet<>();
	
	
	public Role(RolesEnum rolesEnum) {
		// TODO Auto-generated constructor stub
		this.id = rolesEnum.getId();
		this.name = rolesEnum.getRoleName();
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}
