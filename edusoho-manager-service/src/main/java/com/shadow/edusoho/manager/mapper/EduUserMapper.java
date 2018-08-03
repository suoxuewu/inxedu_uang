package com.shadow.edusoho.manager.mapper;

import com.edusoho.bean.EduUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 */
public interface EduUserMapper extends Mapper<EduUser> {
    List<EduUser> userDefinedGetAllEduUser();
    int batchSaveEduUserInfo(@Param("eduUsers")List<EduUser> eduUsers);
}
