package switchtwentytwenty.project.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.repositories.PersonRepository;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    PersonRepository personRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            Person person = personRepository.getByEmail(new Email(s));
            return UserDetailsImpl.build(person);
        } catch (Exception e) {
            throw new ObjectDoesNotExistException("User Not Found with username: " + s);
        }
    }
}
