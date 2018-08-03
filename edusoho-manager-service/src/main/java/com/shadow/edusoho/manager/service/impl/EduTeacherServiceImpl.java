package com.shadow.edusoho.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.edusoho.bean.EduCourseTeacher;
import com.edusoho.bean.EduTeacher;
import com.edusoho.bean.EduUser;
import com.edusoho.service.EduTeacherService;
import com.edusoho.vo.TermTeacher;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shadow.edusoho.manager.mapper.EduCourseTeacherMapper;
import com.shadow.edusoho.manager.mapper.EduTeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/29.
 */
@Service
public class EduTeacherServiceImpl implements EduTeacherService {

    @Autowired
    private EduTeacherMapper eduTeacherMapper;

    @Autowired
    private EduCourseTeacherMapper eduCourseTeacherMapper;

    public Map<String, Object> getPage(TermTeacher termParams) {
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

        List<EduTeacher> eduUsers = null;
        Example example = new Example(EduTeacher.class);
        Example.Criteria criteria = example.createCriteria();
        //  状态  0 正常 1 删除
        criteria.andEqualTo("status","0");
        //1.条件一:讲师名
        String name = termParams.getName();
        if(name != null && name.length() > 0){
            //组装条件
            criteria.andEqualTo("name",name);
        }

        //2.条件二:头衔   1 高级讲师  2首席讲师
        String isStar = termParams.getIsStar();
        if(isStar != null && isStar.length() > 0 && isStar.length() <2){
            //组装条件
            criteria.andEqualTo("isStar",isStar);
        }

        //3.条件三:注册时间 [开始注册时间，结束注册时间]这个范围之间的
        String startDateTime = termParams.getStartDateTime();
        String endDateTime = termParams.getEndDateTime();
        if(startDateTime != null && startDateTime.length() > 0 && endDateTime != null && endDateTime.length() > 0){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                //将字符串类型的日期转换成Date类型
                Date startDate,endDate;
                startDate  = format.parse(startDateTime);
                 endDate =  format.parse(endDateTime);
                //组装条件
                criteria.andBetween("createTime",startDate,endDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //执行查询
        eduUsers = eduTeacherMapper.selectByExample(example);

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

    public Boolean saveEduTeacher(EduTeacher eduTeacher) {
        int flag = eduTeacherMapper.insert(eduTeacher);
        return flag>0;
    }

    public Boolean updateEduTeacher(EduTeacher eduTeacher) {
        int flag = eduTeacherMapper.updateByPrimaryKeySelective(eduTeacher);
        return flag>0;
    }

    /**
     * 删除教师信息
     * 只需要将教师信息中的  status值更新
     *        //状态  0 正常 1 删除
     *  @param id
     * @return
     */
    public Boolean deleteEduTeacher(String id) {
        EduTeacher eduTeacher = eduTeacherMapper.selectByPrimaryKey(id);
        if(eduTeacher == null || eduTeacher.getId() == null){
            return false;
        }
        //status 为0 表示该记录已经被删除
        eduTeacher.setStatus(1);
        int flag = 0;
        if(id != null && id.length() >0){
           flag = eduTeacherMapper.updateByPrimaryKeySelective(eduTeacher);
        }
        return flag>0;
    }

    public EduTeacher getEduTeacherById(String id) {

        EduTeacher eduTeacher = eduTeacherMapper.selectByPrimaryKey(id);
        return eduTeacher;
    }

    /**
     * 获取所有的教师信息
     * @return
     */
    public List<EduTeacher> getAllTeacherInfo() {
        return eduTeacherMapper.selectAll();
    }

    /**
     * 根据课时Id在中间表中查找分配给当前课时的老师
     * @param courseId
     * @return
     */
    public List<EduCourseTeacher> getAllCourseTeacher(Integer courseId) {
        if(courseId == null )
            return null;
        Example example = new Example(EduCourseTeacher.class);
        example.createCriteria().andEqualTo("courseId",courseId);
        List<EduCourseTeacher> eduCourseTeachers = eduCourseTeacherMapper.selectByExample(example);
        if(eduCourseTeachers != null && eduCourseTeachers.size() > 0)
            return eduCourseTeachers;
        return null;
    }
}
