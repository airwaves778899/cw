package cw.mvc;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cw.json.JasonDataBuilder;
import cw.service.ArticleService;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
	
	@Resource
	private ArticleService articleService;
	
	@Resource
	private JasonDataBuilder jasonDataBuilder;
	
	@RequestMapping(value = "/query.do", method = RequestMethod.GET)
	public @ResponseBody String getData() {
		System.out.println( "query..." );
		
		System.out.println( articleService );
		System.out.println( articleService.queryArticleById(5000000) );
		
		return jasonDataBuilder.buildCwJasonData();
	}
}
