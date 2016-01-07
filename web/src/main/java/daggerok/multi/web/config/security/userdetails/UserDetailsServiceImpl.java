package daggerok.multi.web.config.security.userdetails;

import daggerok.multi.data.user.User;
import daggerok.multi.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (null == user) throw new UsernameNotFoundException(
                String.format("user for '%s' wasn't found.", username)
        );

        return new UserDetailsImpl(user);
    }
}
