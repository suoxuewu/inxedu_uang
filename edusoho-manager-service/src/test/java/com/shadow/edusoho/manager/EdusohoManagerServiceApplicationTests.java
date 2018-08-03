package com.shadow.edusoho.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.edusoho.bean.EduUser;
import com.edusoho.service.EduUserService;
import com.github.pagehelper.PageHelper;
import com.shadow.edusoho.manager.mapper.EduUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EdusohoManagerServiceApplicationTests {


	@Reference
	EduUserService eduUserService;

	@Test
	public void test() throws ParseException {

		/*List<EduUser> eduUsers = eduUserService.getPage(1,2);
		for (EduUser eduUser : eduUsers) {
			System.out.println(eduUser.toString());
		}*/

		String str = "2015-07-01 00:00:05";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(str);
        System.out.println(format.format(date));

    }
    @Test
    public void test1() throws ParseException {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("email","lmx193@162.com  ");
        maps.put("mobile","");
        maps.put("isAvalible","0");
       /* maps.put("startDateTime","2015-07-01 00:00:05");
        maps.put("endtDateTime","2018-07-29 10:35:45");*/

        maps.put("startDateTime","");
        maps.put("endtDateTime","");
        List<EduUser> eduUserInfo = eduUserService.findEduUserInfo(maps);
        for (EduUser eduUser : eduUserInfo) {
            System.out.println(eduUser);
        }
    }

}
