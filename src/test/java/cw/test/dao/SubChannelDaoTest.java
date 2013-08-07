package cw.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cw.dao.SubChannelDao;
import cw.model.SubChannel;

public class SubChannelDaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQueryAllSubChannels() {
		try{
			List<SubChannel> list = SubChannelDao.getInstance().queryAllSubChannels();
			for(SubChannel subChannel : list){
				System.out.println(subChannel);
			}
		}catch(Exception e){
			e.printStackTrace();
			fail("Not yet implemented");
		}
		
	}

}
