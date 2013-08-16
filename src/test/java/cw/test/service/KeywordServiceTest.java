package cw.test.service;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;

import cw.service.KeywordService;
import cw.test.BaseTest;

public class KeywordServiceTest extends BaseTest{
	
	@Resource
    private KeywordService keywordService;


	@Test
	public void testBuildKeyWordRelation(){
		keywordService.buildKeyWordRelation();
		fail("");
	}

}
