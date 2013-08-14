package cw.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cw.dao.KeywordDao;
import cw.model.KeyWord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class KeywordDaoTest {
	
    @Autowired
    private KeywordDao keywordDao;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		KeyWord keyWord = keywordDao.queryKeyWord("TEST");
		System.out.println(keyWord);
		fail("Not yet implemented");
	}

}
