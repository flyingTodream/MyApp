package com.app.cotroller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.LInfo;
import com.app.model.lDetail;
import com.app.service.LDetailService;
import com.app.service.LineService;

@RequestMapping("api/")
@RestController
public class AdminLineController {

	@Autowired
	private LineService LineServiceImpl;
	
	@Autowired
	private LDetailService LDetailServiceImpl;
	/**
	 * 获取路线集合
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/getLineList")
	public JSONObject getLineList(@RequestBody(required=false) Map<String, Object> map) {
		JSONObject json = new JSONObject();
		String tId = map.get("tId").toString();
		json.put("lineList", LineServiceImpl.getLineList(Integer.valueOf(tId)));
		return json;
	}
	
	
	/**
	 * 增加路线
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/addLine")
	public void addLine(@RequestBody(required=false) Map<String, Object> map) {
		String tId = map.get("tId").toString();
		String lName = map.get("lName").toString();
		LInfo entity = new LInfo();
		entity.settInfoId(Integer.valueOf(tId));
		entity.setlName(lName);
		LineServiceImpl.insertSelective(entity);
	}
	
	/**
	 * 删除路线
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/delLine")
	public JSONObject delLine(@RequestBody(required=false) Map<String, Object> map) {
		JSONObject json = new JSONObject();
		String lId = map.get("lId").toString();
		if(LDetailServiceImpl.getDetailList(Integer.valueOf(lId)).size() != 0) {
			json.put("status", 1);
		}else {
			LineServiceImpl.deleteByPrimaryKey(Integer.valueOf(lId));
		}
		return json;
	}
	
	/**
	 * 获取路线详细信息
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/getlineDetail")
	public List<lDetail> getlineDetail(@RequestBody(required=false) Map<String, Object> map) {
		String lId = map.get("lId").toString();
		return LDetailServiceImpl.getDetailList(Integer.valueOf(lId));
	}
	
	/**
	 * 更新路线详细信息
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/updateLineDetail")
	public void updateLineDetail(@RequestBody(required=false) Map<String, Object> map) {
		String detailId = map.get("detailId").toString();
		String lLine = map.get("lLine").toString();
		lDetail entity = new lDetail();
		entity.setDetailId(Integer.valueOf(detailId));;
		entity.setlLine(lLine);
		LDetailServiceImpl.updateByPrimaryKeySelective(entity);
	}
	
	/**
	 * 删除路线详细信息
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/delLineDetail")
	public void delLineDetail(@RequestBody(required=false) Map<String, Object> map) {
		String detailId = map.get("detailId").toString();
		LDetailServiceImpl.deleteByPrimaryKey(Integer.valueOf(detailId));
	}
	
	/**
	 * 增加路线详细信息
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/addLineDetail")
	public void addLineDetail(@RequestBody(required=false) Map<String, Object> map) {
		String lId = map.get("lId").toString();
		String lLine = map.get("lLine").toString();
		lDetail entity = new lDetail();
		entity.setlId(Integer.valueOf(lId));
		entity.setlLine(lLine);
		LDetailServiceImpl.insertSelective(entity);
	}
}
