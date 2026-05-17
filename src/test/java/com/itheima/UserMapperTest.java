package com.itheima;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User(); //表明是user
//        user.setId(1L);
        user.setName("玛卡巴卡");
        user.setIsMarried(true);
        user.setOrder(1); //本质上是拼接字段
        user.setAddress("花园");
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setName("唔西迪西");
        userMapper.update(user, null);
    }

    @Test
    void testDeleteUser() {
        userMapper.deleteById(1L);
    }
}
