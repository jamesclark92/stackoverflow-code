import com.football_preds.domain.User;
import com.football_preds.domain.UserDao;
import com.football_preds.domain.UserService;
import com.football_preds.exceptions.UserAlreadyExistsException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Configuration
@ComponentScan("com.football_preds")
@EnableAutoConfiguration
@Controller
public class BaseController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    // Map all urls to this method aside from ones starting with api
    @RequestMapping(value = "/**")
    public String getIndex() {
        return "/views/index.jsp";
    }


    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody User user)
            throws UserAlreadyExistsException {

        // Assume password is sent as base 64 encoded
        user.password = StringUtils.newStringUtf8(Base64.decodeBase64(user.password));
        return userService.registerUser(user.emailAddress, user.password, user.firstName, user.lastName);
    }

}