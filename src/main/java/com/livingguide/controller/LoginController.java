package com.livingguide.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@RequestMapping(value = "loginAction")
	public ModelAndView login(@RequestParam("account") String acc, @RequestParam("passwd") String passwd){
        ModelAndView mv = new ModelAndView("redirect:/EtlConfig/config");
		UsernamePasswordToken token = new UsernamePasswordToken(acc, passwd, true);
		logger.info("accNo: {} password: {}", acc, passwd);
		//获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {  
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查  
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应  
            //所以这一步在调用login(token)方法时,它会走到AuthorizeRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            currentUser.login(token);
        }catch(UnknownAccountException uae){
        	mv = new ModelAndView("/login");
        	mv.addObject("msg", "账号不存在 !");
        	logger.error("账号：[{}] 不存在...", acc);
        }catch(IncorrectCredentialsException ice){  
        	mv = new ModelAndView("/login");
        	mv.addObject("msg", "登录密码错误 !");
            logger.error("账号：[{}]登录密码错误", acc);
        }catch(LockedAccountException lae){  
        	mv = new ModelAndView("/login");
        	mv.addObject("msg", "账号已锁定 !");
            logger.error("账号：[{}]已锁定", acc);
        }catch(ExcessiveAttemptsException eae){
        	mv = new ModelAndView("/login");
        	mv.addObject("msg", "登录尝试次数超过5次限制 !");
            logger.error("账号：[{}]，登录尝试次数超过5次限制！", acc);
        }catch(AuthenticationException ae){
        	mv = new ModelAndView("/login");
        	mv.addObject("msg", "登录失败 !");
             logger.error("账号：[{}]登录失败...", acc);
            
        } 
		//验证是否登录成功  
        if(currentUser.isAuthenticated()){  
        	logger.info("User is Authenticated !"); 
        }else{  
            token.clear();  
        }
        
		return mv;
	}
	
	@RequestMapping(value = "logout")
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		logger.info("账号【{}】登出...", subject.getPrincipal().toString());
		subject.logout();
		return "login";
	}
	@RequestMapping(value = "login")
	public Object toLogin(){
		return "login";
	}
}
