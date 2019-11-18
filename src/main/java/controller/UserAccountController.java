package controller;

import model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.UserAccountService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes("userAccount")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;

    @ModelAttribute("userAccountList")
    private Iterable<UserAccount> userAccountList() {
        return userAccountService.findAll();
    }

    @ModelAttribute("userAccount")
    public UserAccount setupUserAccount() {
        return new UserAccount();
    }

    @GetMapping("")
    public ModelAndView getIndex(@CookieValue(value = "setUser", defaultValue = "") String setUser) {
        Cookie cookie = new Cookie("setUser", setUser);
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("cookieValue", cookie);
        return modelAndView;
    }

    @PostMapping("/dologin")
    public ModelAndView doLogin(@Validated @ModelAttribute("userAccount") UserAccount userAccount, @CookieValue(value = "setUser", defaultValue = "") String setUser, HttpServletResponse response, HttpServletRequest request) {
        Iterable<UserAccount> userAccountList = userAccountList();
        ModelAndView modelAndView = new ModelAndView("/index");
        for (UserAccount ua : userAccountList) {
            if (ua.getUsername().equals(userAccount.getUsername()) && ua.getPassword().equals(userAccount.getPassword())) {
                if (userAccount.getUsername() != null) {
                    setUser = userAccount.getUsername();
                }
                Cookie cookie = new Cookie("setUser", setUser);
                cookie.setMaxAge(1000000000);
                response.addCookie(cookie);

                Cookie[] cookies = request.getCookies();
                for (Cookie ck : cookies) {
                    if (ck.getName().equals("setUser")) {
                        modelAndView.addObject("cookieValue", ck);
                        break;
                    } else {
                        ck.setValue("");
                        modelAndView.addObject("cookieValue", ck);
                        break;
                    }
                }
                modelAndView.addObject("msg", "Login Success");
                return modelAndView;
            }
        }
        userAccount.setUsername("");
        Cookie cookie = new Cookie("setUser", setUser);
        modelAndView.addObject("cookieValue", cookie);
        modelAndView.addObject("msg", "Login Failed!!");
        return modelAndView;
    }
}


