package cw.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cw.dao.KeywordDao;
import cw.model.KeyWord;

public class KeywordDaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		KeyWord keyWord = KeywordDao.getInstance().queryKeyWord("TEST");
		System.out.println(keyWord);
		fail("Not yet implemented");
	}

}
