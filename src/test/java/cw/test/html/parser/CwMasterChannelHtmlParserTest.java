package cw.test.html.parser;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cw.dao.KeywordDao;
import cw.dao.MasterChannelDao;
import cw.dao.SubChannelDao;
import cw.html.parser.CwMasterChannelHtmlParser;
import cw.model.KeyWord;
import cw.model.MasterChannel;
import cw.model.SubChannel;
import cw.test.BaseTest;

public class CwMasterChannelHtmlParserTest extends BaseTest{

	@Test
	public void testExtractSubChannels() {
		
		CwMasterChannelHtmlParser parser = new CwMasterChannelHtmlParser();
		try {
			Set<MasterChannel> list = parser.extractMasterChannels();
			for(MasterChannel masterChannel : list){
				System.out.printf("%s , %s ==============>\n", masterChannel.getId(), masterChannel.getName());
			
				for(SubChannel subChannel : masterChannel.getSubChannels()){
					System.out.printf("%s , %s , %s \n", subChannel.getId(), subChannel.getName(), subChannel.getMasterChannelId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
		
		
	}

}
