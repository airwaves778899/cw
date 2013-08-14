package cw.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cw.dao.MasterChannelDao;
import cw.model.MasterChannel;
import cw.test.BaseTest;

public class MasterChannelDaoTest extends BaseTest{
	
	@Autowired
	private MasterChannelDao dao;

	@Test
	public void testQueryAllMasterChannels() {
		try{
			List<MasterChannel> list = dao.queryAllMasterChannels();
			for(MasterChannel masterChannel : list){
				System.out.printf("%s , %s \n", masterChannel.getId(), masterChannel.getName());

			}
		}catch(Exception e){
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
