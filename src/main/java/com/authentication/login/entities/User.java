package com.authentication.login.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	private int id;
	private String email;
	private String password;
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(name="user_roles",
				joinColumns = @JoinColumn(name="user_id"),
				inverseJoinColumns = @JoinColumn(name="role_id"))
	private List<Role> roles= new ArrayList();
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", roles=" + roles + "]";
	}
	

}
