package Service;

import Model.PrivateUser;
import Model.CompanyUser;
import Model.User;
import Model.Role;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean createPrivateUser(
            String username,
            String password,
            String name
    ) {

        if(userRepository.findByUsername(username).isPresent()){
            return false;
        }

        PrivateUser user = new PrivateUser();

        user.setUsername(username);

        user.setPassword(
                passwordEncoder.encode(password)
        );

        user.setName(name);

        user.setRole(Role.PRIVATE);

        userRepository.save(user);

        return true;
    }

    public boolean createCompanyUser(
            String username,
            String password,
            String name,
            String companyAddress,
            String companyWebsiteURL
    ) {

        // check if username already exists
        if(userRepository.findByUsername(username).isPresent()){
            return false;
        }

        CompanyUser user = new CompanyUser();

        user.setUsername(username);

        // HASH PASSWORD
        user.setPassword(
                passwordEncoder.encode(password)
        );

        user.setName(name);

        user.setRole(Role.COMPANY);

        user.setCompanyAddress(companyAddress);

        user.setCompanyWebsiteURL(companyWebsiteURL);

        userRepository.save(user);

        return true;
    }

    public User login(String username, String password){

        if(userRepository.findByUsername(username).isEmpty()){
            return null;
        }

        User user = userRepository.findByUsername(username).get();

        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());

        if(passwordMatches){
            return user;
        }
        return null;
    }
}
