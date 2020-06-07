package com.springrest.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

	public ModelAndView indexPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
}
