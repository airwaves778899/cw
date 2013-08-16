package cw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cw.dao.KeywordDao;
import cw.model.KeyWord;

@Service("keywordService")
@Transactional
public class KeywordService {

	@Autowired
	private KeywordDao keywordDao;
	
	public void buildKeyWordRelation(){
		KeyWord keyWord = keywordDao.queryKeyWordById(1);
		System.out.println(keyWord);
	}
}
