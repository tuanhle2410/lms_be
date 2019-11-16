package vn.edu.topica.edumall.security.core.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.edu.topica.edumall.data.model.User;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;
import vn.edu.topica.edumall.security.jdbc.repository.UserRepository;

@Service
@Transactional
@Primary
public class LmsUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserPrincipal loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
				() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
		return UserPrincipal.create(user);
	}

}
