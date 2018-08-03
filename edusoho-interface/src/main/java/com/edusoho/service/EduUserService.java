package com.edusoho.service;

import com.edusoho.bean.EduUser;
import com.edusoho.vo.Term;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/27.
 */
public interface EduUserService {
    /**
     * 获取的学员信息
     * @return
     */
    public List<EduUser> getAllEduUser();

    /**
     * 分页数据
     * @param termParams
     * @return
     */
    public Map<String, Object> getPage(Term termParams);

    /**
     * 更新用户信息
     * @param map
     * @return
     */
    boolean updateEduUserInfo(Map<String, Object> map);

    /**
     * 更新用户的状态
     * @param map
     * @return
     */
    boolean updateEduUserInfoStatus(HashMap<String, Object> map);

    /**
     * 根据指定条件获取EduUser信息
     * @param maps
     * @return
     */
    List<EduUser> findEduUserInfo(Map<String,Object> maps);

    /**
     * 批量插入
     * @param eduUsers
     * @return
     */
    Boolean  batchSaveEduUserInfo(List<EduUser> eduUsers);
}
