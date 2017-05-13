package com.global.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.dao.CycLockDAO;
import com.global.daoimpl.CycLockDAOImpl;
import com.global.model.CycLock;
import com.system.socket.TCPServerThread;
import com.system.tools.NetResult;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome main! You are successing to visit the main page");
		/*logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );*/
		CycLockDAO cdao = new CycLockDAOImpl();
		CycLock cyc = cdao.findLastet();
		/*if(cyc==null){
			return "home1";
		}*/
		
		model.addAttribute("cyc", cyc);
		return "home";
	}
	
	
	/**
	 * ¿ªËø
	 */
	@RequestMapping(value="/lockCyc",method= RequestMethod.POST)
	public @ResponseBody NetResult lockCyc(@RequestParam boolean lock){
		NetResult r=new NetResult();
		CycLockDAO cdao = new CycLockDAOImpl();
		CycLock cyc = cdao.findLastet();
		cyc.setState(false);
		cdao.save(cyc);		
		TCPServerThread server = TCPServerThread.getTCPServerThreadInstance();
		server.send("openlock");
		return r;
	}
	
	/**
	 * ¿ªËø
	 */
	@RequestMapping(value="/findBell",method= RequestMethod.POST)
	public @ResponseBody NetResult findBell(@RequestParam boolean bell){
		NetResult r=new NetResult();
		TCPServerThread server = TCPServerThread.getTCPServerThreadInstance();
		server.send("findbell");
		return r;
	}
	
}
