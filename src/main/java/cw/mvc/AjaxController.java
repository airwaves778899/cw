package cw.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cw.json.JasonDataBuilder;
import cw.service.ArticleService;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
	
	@Resource
	private ArticleService articleService;
	
	@Resource
	private JasonDataBuilder jasonDataBuilder;
	
	@RequestMapping(value = "/queryJasonData.do", method = RequestMethod.GET)
	public @ResponseBody Object getJasonData(HttpServletResponse response) throws IOException {
			return jasonDataBuilder.buildCwJasonData(7);

	}
}
