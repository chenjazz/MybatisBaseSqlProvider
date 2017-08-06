package com.example.mybatistest.dao;

import com.example.mybatistest.util.SqlCondition;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Jiazhi
 * @since 2017/8/5
 */
@Mapper
public interface StudentMapper {

    //save
    @InsertProvider(type = StudentSqlProvider.class, method = "insert")
    int insert(Student student);

    @InsertProvider(type = StudentSqlProvider.class, method = "insertSelective")
    int insertSelective(Student student);


    //update
    @UpdateProvider(type = StudentSqlProvider.class, method = "update")
    int update(Student student);

    @UpdateProvider(type = StudentSqlProvider.class, method = "updateSelective")
    int updateSelective(Student student);

    //delete
    @DeleteProvider(type = StudentSqlProvider.class, method = "delete")
    int delete();

    @DeleteProvider(type = StudentSqlProvider.class, method = "deleteById")
    int deleteById(String id);

    @DeleteProvider(type = StudentSqlProvider.class, method = "deleteByIdList")
    int deleteByIdList(List<String> ids);

    //select
    @SelectProvider(type = StudentSqlProvider.class, method = "select")
    List<Student> select();

    @SelectProvider(type = StudentSqlProvider.class, method = "selectById")
    Student selectById(String id);

    @SelectProvider(type = StudentSqlProvider.class, method = "selectWhere")
    List<Student> selectWhere(SqlCondition sqlCondition);

    //count
    @SelectProvider(type = StudentSqlProvider.class, method = "count")
    int count();

    //count
    @SelectProvider(type = StudentSqlProvider.class, method = "countWhere")
    int countWhere(SqlCondition sqlCondition);
}
