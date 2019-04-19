package com.app.cotroller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.ULike;
import com.app.service.ULikeService;
import com.common.CommonContext;
import com.common.utils.DateUtils;
import com.common.utils.JwtUtils;

@RestController
@RequestMapping("api/")
public class LikeController {

	@Autowired
	private ULikeService uLikeServiceImpl;

	/**
	 * 添加收藏
	 * 
	 * @param token
	 * @param tInfoId
	 * @return
	 */
	@RequestMapping("user/addLike")
	public JSONObject userLike(String token, String tInfoId) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			// System.out.println(token + "==" + tInfoId);
			// System.out.println(JwtUtils.verify(token));
			if (JwtUtils.verify(token)) {
				// System.out.println("insert----");
				ULike entity = new ULike();
				entity.settId(Integer.valueOf(JwtUtils.getId(token)));
				entity.settInfoId(Integer.valueOf(tInfoId));
				entity.setuTime(DateUtils.dateToStringShort(new Date()));
				if (uLikeServiceImpl.selectIsLike(entity) == null) {
					uLikeServiceImpl.insertSelective(entity);
				}
				// System.out.println("complated----");
			} else {
				json.put("IsLogin", CommonContext.NO_LOGIN);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}

	// 判断是否收藏
	@RequestMapping("user/isLike")
	public JSONObject isLike(String token, String tInfoId) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			if (JwtUtils.verify(token)) {
				ULike entity = new ULike();
				entity.settId(Integer.valueOf(JwtUtils.getId(token)));
				entity.settInfoId(Integer.valueOf(tInfoId));
				if (uLikeServiceImpl.selectIsLike(entity) == null) {
					json.put("IsLike", "N");
				} else {
					json.put("IsLike", "Y");
				}
			} else {
				json.put("IsLike", "N");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}

	// 查询收藏列表
	@RequestMapping("user/getLikeList")
	public JSONObject getLikeList(HttpServletRequest req, HttpServletResponse res) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		String token = req.getHeader("token");
		try {

			if (JwtUtils.verify(token)) {
				json.put("islogin", CommonContext.IS_LOGIN);
				json.put("likeList", uLikeServiceImpl.getLikeList(Integer.valueOf(JwtUtils.getId(token))));

			} else {
				json.put("islogin", CommonContext.NO_LOGIN);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}

	//删除收藏
	@RequestMapping("user/deleteLike")
	public JSONObject deleteLike(HttpServletRequest req, HttpServletResponse res,String uId) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			uLikeServiceImpl.deleteByPrimaryKey(Integer.valueOf(uId));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}
}
