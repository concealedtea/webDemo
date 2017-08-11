package com.falcon.dsp.service;

import com.falcon.dsp.exception.FalconDspSqlException;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.interceptor.PageParameter;
import com.falcon.dsp.jdbc.entity.UserInfo;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.jdbc.model.TableParam;
import com.falcon.dsp.jdbc.model.UserListModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei.wang
 * @from 2016-04-15
 * @since V1.0
 */
@Service
public class UserInfoService {

	@Resource(name = "sqlHandler")
	private SqlSessionHandler sqlHandler;

	/**
	 * 获得user_info
	 * 
	 * @param param
	 * @return UserInfo
	 */
	public UserInfo getUserInfoByUserName(UserInfo param) {
		return sqlHandler.selectOne("userInfoMapper.userInfo:select:one", param);
	}

	/**
	 * 创建用户
	 * 
	 * @param param
	 * @return
	 */
	public void insertOne(UserInfo param) {
		checkByUserName(param);
		sqlHandler.insert("userInfoMapper.userInfo:insert", param);
	}

	/**
	 * 是否有用户名 重名
	 * 
	 * @param param
	 */
	private void checkByUserName(UserInfo param) {

		int number = sqlHandler.selectOne("userInfo:check:userName", param);

		if (number > 0) {
			throw new FalconDspSqlException("用户名已经存在");
		}
	}

	/**
	 * 获取代理商用户下的所有登录账户列表
	 *
	 * @param param
	 * @return
     */
	public TableData<UserListModel> getUserList(TableParam param, UserInfo user) {
		// 分页参数
		PageParameter pageParameter = new PageParameter();
		pageParameter.setCurrentPage(param.getCurrentPage());
		pageParameter.setPageSize(param.getPageSize());

		// 列表查询条件
		Map<String, Object> searchCondition = new HashMap<>();
		searchCondition.put("searchKeyword", param.getSearchKeyword());
		// 该代理商账户对应的代理商ID
		searchCondition.put("agencyId", user.getUid());

		// 查询该代理商下的所有登录账户
		List<UserListModel> listUser = sqlHandler.selectList("userInfoMapper.userInfo:list:page", searchCondition, pageParameter);

		TableData<UserListModel> result = new TableData<>();
		result.setData(listUser);
		result.setRecordsTotal(pageParameter.getTotalCount());
		result.setRecordsFiltered(pageParameter.getTotalCount());

		return result;
	}

	public UserListModel read(int id) {
		return sqlHandler.selectOne("userInfoMapper.userInfo:selectById",id);
	}

	public void update(UserInfo param) {
		checkByUserName(param);
		sqlHandler.update("userInfoMapper.userInfo:update",param);
	}

	/**
	 * 更新用户的状态
	 * 
	 * @param userName
	 * @param status
	 */
	// public void updateUserStatus(String userName, Integer status){
	// Map<String, Object> param = new HashMap<String, Object>();
	// param.put("user_name", userName);
	// param.put("status", status);
	//
	// try {
	// sqlHandler.update("userInfoMapper.userInfo:update:status", param);
	// } catch (Exception e) {
	// throw new RuntimeException(e.getMessage());
	// }
	// }

}
