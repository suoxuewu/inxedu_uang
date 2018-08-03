package com.shadow.edusoho.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.edusoho.bean.EduUser;
import com.edusoho.service.EduUserService;
import com.edusoho.util.MD5Util;
import com.edusoho.vo.Term;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shadow.edusoho.manager.mapper.EduUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/27.
 */
@Service
public class EduUserServiceImpl implements EduUserService{

    @Autowired
    private EduUserMapper eduUserMapper;


    /**
     * 获取所有的学员信息
     * @return
     */
    public List<EduUser> getAllEduUser() {

        List<EduUser> eduUsers = eduUserMapper.selectAll();

        return eduUsers;
    }

    public Map<String, Object> getPage(Term termParams) {
        //获取pageNum显示第几页数据
        String num = termParams.getPageNum();
        int pageNum = 1;//默认显示第一页
        if(num != null || num.length() > 0){
            pageNum = Integer.parseInt(num);
         }
         //获取每页显示的
        String size = termParams.getPageSize();
        int pageSize = 5;//默认每页显示5条数据
        if(size != null || size.length() > 0) {
           pageSize = Integer.parseInt(size);
        }
        //使用mybatis插件pageHeler插件进行分页
        Page<Object> pages = PageHelper.startPage(pageNum, pageSize);

        List<EduUser> eduUsers = null;
        Example example = new Example(EduUser.class);
        Example.Criteria criteria = example.createCriteria();
        //1.条件一:邮箱/手机号/昵称/姓名
        String email =  termParams.getEmail();
        String mobile =  termParams.getMobile();

        if(email != null && email.length() > 6){
            //组装条件
            criteria.andEqualTo("email",email);
        }
        if(mobile != null && mobile.length() == 11){
            //组装条件
            criteria.andEqualTo("mobile",mobile);
        }
        //2.条件二:状态
        String isAvalible = termParams.getIsAvalible();
        if(isAvalible != null && isAvalible.length() > 0 && isAvalible.length() <2){
            //组装条件
            criteria.andEqualTo("isAvalible",isAvalible);
        }

        //3.条件三:注册时间 [开始注册时间，结束注册时间]这个范围之间的
        String startDateTime = termParams.getStartDateTime();
        String endDateTime = termParams.getEndDateTime();
        if(startDateTime != null && startDateTime.length() > 0 && endDateTime != null && endDateTime.length() > 0){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                //将字符串类型的日期转换成Date类型
                Date startDate = format.parse(startDateTime);
                Date endDate =  format.parse(endDateTime);
                //组装条件
                criteria.andBetween("createTime",startDate,endDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //执行查询
        eduUsers = eduUserMapper.selectByExample(example);

       HashMap<String, Object> maps = new HashMap<String, Object>();
        //当前第几页
        maps.put("pageNum",pages.getPageNum());
        //总共多少页
        maps.put("totalPageCount",pages.getPages());
        //每页显示多少跳数据
        maps.put("pageSize",pages.getPageSize());
        //总共多少条
        maps.put("totalCount",pages.getTotal());

        //要显示数据
        maps.put("eduUsers",eduUsers);
        return maps;
    }

    /**
     * 更新用户密码
     * @param map
     * @return
     */
    public boolean updateEduUserInfo(Map<String, Object> map) {
        String enterPasswd = (String)map.get("enterPasswd");
        String  userId = (String)map.get("userId");
        EduUser eduUser = new EduUser();
        eduUser.setUserId(userId);
        //对密码进行加密
        String passwd = MD5Util.digest(enterPasswd);
        eduUser.setPassword(passwd);
        int i = eduUserMapper.updateByPrimaryKeySelective(eduUser);
        return i > 0;
    }

    /**
     *isAvalible == 1 正常
     *              0 冻结
     * @param map
     * @return
     */
    public boolean updateEduUserInfoStatus(HashMap<String, Object> map) {
        String isAvalible = (String)map.get("isAvalible");
        String  userId = (String)map.get("userId");
        EduUser eduUser = new EduUser();
        eduUser.setUserId(userId);

        if ("1".equals(isAvalible)){
            eduUser.setIsAvalible(0);
        }else {
            eduUser.setIsAvalible(1);
        }
        int i = eduUserMapper.updateByPrimaryKeySelective(eduUser);

        return i>0;
    }

    /**
     * 根据条件获取符合指定条件的EduUser信息
     * @param maps
     * @return
     */
    public List<EduUser> findEduUserInfo(Map<String, Object> maps) {

        List<EduUser> eduUsers = null;
        Example example = new Example(EduUser.class);
        Example.Criteria criteria = example.createCriteria();
        //1.条件一:邮箱/手机号/昵称/姓名
        String email = (String)maps.get("email");
        String mobile = (String)maps.get("mobile");

        if(email != null && email.length() > 6){
            //组装条件
            criteria.andEqualTo("email",email);
        }
        if(mobile != null && mobile.length() == 11){
            //组装条件
            criteria.andEqualTo("mobile",mobile);
        }
        //2.条件二:状态
        String isAvalible = (String)maps.get("isAvalible");
        if(isAvalible != null && isAvalible.length() > 0 && isAvalible.length() <2){
            //组装条件
            criteria.andEqualTo("isAvalible",isAvalible);
        }

        //3.条件三:注册时间 [开始注册时间，结束注册时间]这个范围之间的
        String startDateTime = (String)maps.get("startDateTime");
        String endtDateTime = (String)maps.get("endtDateTime");
        if(startDateTime != null && startDateTime.length() > 0 && endtDateTime != null && endtDateTime.length() > 0){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                //将字符串类型的日期转换成Date类型
                Date startDate = format.parse(startDateTime);
                Date endDate =  format.parse(endtDateTime);
                //组装条件
                criteria.andBetween("createTime",startDate,endDate);
             } catch (Exception e) {
                e.printStackTrace();
            }
        }
        eduUsers = eduUserMapper.selectByExample(example);


        return eduUsers;
    }

    public Boolean batchSaveEduUserInfo(List<EduUser> eduUsers) {
        if(eduUsers == null || eduUsers.size() ==0)
            return false;
        int flag = 0;
        for (EduUser eduUser : eduUsers) {
            eduUser.setUserId(null);
            flag = eduUserMapper.insertSelective(eduUser);
        }
         return flag>0;
    }
}
