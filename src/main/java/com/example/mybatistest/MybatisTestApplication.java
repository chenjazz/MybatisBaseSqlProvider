package com.example.mybatistest;

import com.example.mybatistest.dao.Student;
import com.example.mybatistest.dao.StudentMapper;
import com.example.mybatistest.util.SqlCondition;
import com.google.common.collect.Lists;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.mybatistest.dao"})
@EnableTransactionManagement
public class MybatisTestApplication implements CommandLineRunner {

    @Autowired
    StudentMapper studentMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisTestApplication.class, args);
    }


    @Override
    public void run(String... strings) throws Exception {
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName("123123");
        studentMapper.insert(student);


//        Student student2 = new Student();
//        student2.setId("34ff4103-0221-4aa5-a2d4-a652b495cc67");
//        student2.setName("啦啦开始的");
//        student2.setBirthday(null);
//        studentMapper.updateSelective(student2);


//        studentMapper.delete();
//        studentMapper.deleteByIdList(Lists.newArrayList("a2a89eeb-452f-41ad-b8e8-4dcd80f7a6e5", "e6159571-71a6-4b04-881f-4968669ac7ba"));
//        studentMapper.deleteById("a2a89eeb-452f-41ad-b8e8-4dcd80f7a6e5");


        System.out.println(studentMapper.selectById("e548e609-5ca2-4e85-b81e-b365c6d598a8"));
        System.out.println(studentMapper.select());


        SqlCondition sqlCondition = new SqlCondition("name=#{p1}", "小明");
        System.out.println(studentMapper.selectWhere(sqlCondition));

        System.out.println(studentMapper.count());
        System.out.println(studentMapper.countWhere(sqlCondition));


    }
}
