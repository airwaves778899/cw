package cw.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.Before;

import cw.dao.MasterChannelDao;
import cw.model.MasterChannel;

public class MasterChannelDaoTest {
	MasterChannelDao dao;
	
	@Before
	public void start(){
		dao = MasterChannelDao.getInstance();
	}

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
