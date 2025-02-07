package com.authentication.login.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.authentication.login.entities.User;
import com.authentication.login.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(username);
		//user.setRoles(roleService.findByRoleId(0));
		System.out.println("User Details:"+user);
		UserDetails userDetails = new UserDetails() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getUsername() {
				// TODO Auto-generated method stub
				return user.getEmail();
			}
			
			@Override
			public String getPassword() {
				// TODO Auto-generated method stub
				return user.getPassword();
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				return user.getRoles().stream().map(n->new SimpleGrantedAuthority("ROLE_"+n.getName())).toList();
			}
		};
		return userDetails;
	}
	
	
	
}
