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

import cw.dao.SubChannelDao;
import cw.model.SubChannel;
import cw.test.BaseTest;

public class SubChannelDaoTest extends BaseTest{
	
	@Autowired
	private SubChannelDao subChannelDao;

	@Test
	public void testQueryAllSubChannels() {
		try{
			List<SubChannel> list = subChannelDao.queryAllSubChannels();
			for(SubChannel subChannel : list){
				System.out.println(subChannel);
			}
		}catch(Exception e){
			e.printStackTrace();
			fail("Not yet implemented");
		}
		
	}

}
