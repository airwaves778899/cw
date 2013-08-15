package cw.mvc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cw.dao.MasterChannelDao;
import cw.dao.SubChannelDao;
import cw.json.JasonDataBuilder;
import cw.model.MasterChannel;
import cw.model.SubChannel;

@Controller
@RequestMapping("/queryData")
public class QueryDataController {
	
	@Resource
	private JasonDataBuilder jasonDataBuilder;
	
	@Autowired
	private MasterChannelDao masterChannelDao;
	
	@Autowired
	private SubChannelDao subChannelDao;
	
	/**
	 * 查詢大分類
	 * @return
	 */
	@RequestMapping(value = "/queryAllMasterChannels.do")
	public @ResponseBody List<MasterChannel> queryAllMasterChannels(){		
		return masterChannelDao.queryAllMasterChannels();
	}
	
	/**
	 * 查詢次分類
	 * @param inputNumber1
	 * @return
	 */
	@RequestMapping(value = "/querySubChannels.do", method = RequestMethod.GET)
	public @ResponseBody List<SubChannel> querySubChannels(
			@RequestParam(value="masterChannelId", required=true) int masterChannelId){		
		return subChannelDao.querySubChannelsByMasterChannelId(masterChannelId);
	}
	
	@RequestMapping(value = "/queryJasonData.do", method = RequestMethod.GET)
	public @ResponseBody Object getJasonData(@RequestParam(value="masterChannelId", required=true) int masterChannelId) throws IOException {
			return jasonDataBuilder.buildCwJasonData(masterChannelId);

	}
}
