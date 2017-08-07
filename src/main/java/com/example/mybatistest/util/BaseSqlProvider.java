package com.example.mybatistest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.mybatistest.util.MyBatisExtUtils.camelToUnderline;

/**
 * @author Jiazhi
 * @since 2017/8/6
 */
public abstract class BaseSqlProvider<E, ID> {
    protected Class<E> entityClass;
    protected Class<ID> idClass;

    protected Logger logger;

    private String className;
    private String tableName;
    /**
     * cache缓存
     */
    private Field idField;
    private String idColumnName;


    private List<Field> fieldsWithoutId = new ArrayList<>();
    private List<String> columnNamesWithoutId = new ArrayList<>();

    private List<Field> fields = new ArrayList<>();
    private List<String> columnNames = new ArrayList<>();


    @SuppressWarnings({"unchecked", "rawtypes"})
    public BaseSqlProvider() {

        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            this.entityClass = (Class<E>) ((ParameterizedType) type).getActualTypeArguments()[0];
            this.idClass = (Class<ID>) ((ParameterizedType) type).getActualTypeArguments()[1];
        } else {
            this.entityClass = null;
        }

        //log
        logger = LoggerFactory.getLogger(entityClass);

        //
        className = entityClass.getSimpleName();

        //解析表名称
        Table tableName = entityClass.getAnnotation(Table.class);
        this.tableName = MyBatisExtUtils.camelToUnderline(className);//默认
        if (tableName != null) {
            if (!StringUtils.isEmpty(tableName.name())) {
                this.tableName = tableName.name();
            }
        }

        //没有主键
        //属性
        this.fieldsWithoutId = MyBatisExtUtils.getAllFieldWithoutIdByClass(entityClass);

        for (Field field : this.fieldsWithoutId) {
            Column annotation = field.getAnnotation(Column.class);
            if (annotation != null && !StringUtils.isEmpty(annotation.name())) {
                this.columnNamesWithoutId.add(annotation.name());
            } else {
                this.columnNamesWithoutId.add(camelToUnderline(field.getName()));
            }
        }

        //主键
        this.idField = MyBatisExtUtils.getIdField(entityClass);
        this.idColumnName = MyBatisExtUtils.camelToUnderline(idField.getName());

        //所有
//        Collections.copy(this.fieldsWithoutId, this.fields);
        this.fields.addAll(this.fieldsWithoutId);
        this.fields.add(0, idField);
        this.columnNames.addAll(this.columnNames);
        this.columnNames.add(0, MyBatisExtUtils.camelToUnderline(idField.getName()));
    }

    /////////////////////////////////////////////////保存方法////////////////////////////////////////


    /**
     * 插入对象中非null的值到数据库
     */
    public String insertSelective(E object) {

        StringBuilder nameBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();


        for (Field field : fields) {
            if (MyBatisExtUtils.getFieldValue(object, field) != null) {
                nameBuilder.append(",").append(MyBatisExtUtils.camelToUnderline(field.getName()));
                valueBuilder.append(",#{").append(field.getName()).append("}");
            }
        }

        return "INSERT INTO " + tableName + "(" + nameBuilder.toString().replaceFirst(",", "") + ") " +
                "VALUES(" + valueBuilder.toString().replaceFirst(",", "") + ")";

    }

    /**
     * 插入对象中的值到数据库，null值在数据库中会设置为NULL
     */
    public String insert(E object) {


        StringBuilder nameBuilder = new StringBuilder();
//
        StringBuilder valueBuilder = new StringBuilder();
        for (Field field : fields) {
            nameBuilder.append(",").append(MyBatisExtUtils.camelToUnderline(field.getName()));
            valueBuilder.append(",#{").append(field.getName()).append("}");

        }
        return "INSERT INTO " + tableName + "(" + nameBuilder.toString().replaceFirst(",", "") + ") " +
                "VALUES(" + valueBuilder.toString().replaceFirst(",", "") + ")";
    }


    /////////////////////////////修改 /////////////////////////////////////////////

    /**
     * 全更新 null值在 数据库中设置为null
     */

    public String update(E entity) {
//        initCache(entity);

        ID id = (ID) MyBatisExtUtils.getFieldValue(entity, idField);

        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("修改时对象id不能为空");
        }

        StringBuilder sqlBuilder = new StringBuilder();
        for (Field field : fieldsWithoutId) {
            sqlBuilder.append("," + MyBatisExtUtils.camelToUnderline(field.getName()) + "=#{" + field.getName() + "}");
        }

        String setValueSql = sqlBuilder.toString().replaceFirst(",", "");
        return "UPDATE " + tableName + " SET " + setValueSql + " WHERE " + idColumnName + "=#{id}";//set sql
    }


    /**
     * 仅更新非null， null值 不更新
     */
    public String updateSelective(E entity) {
        ID id = (ID) MyBatisExtUtils.getFieldValue(entity, idField);

        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("修改时对象id不能为空");
        }

        StringBuilder sqlBuilder = new StringBuilder();
        for (Field field : fieldsWithoutId) {
            if (MyBatisExtUtils.getFieldValue(entity, field) != null) {
                sqlBuilder.append("," + MyBatisExtUtils.camelToUnderline(field.getName()) + "=#{" + field.getName() + "}");
            }
        }

        String setValueSql = sqlBuilder.toString().replaceFirst(",", "");
        return "UPDATE " + tableName + " SET " + setValueSql + " WHERE " + idColumnName + "=#{id}";//set sql
    }


    /////////////////////////////////////////////////删除方法////////////////////////////////////////

    /**
     * 根据id删除数据
     */
    public String deleteById(ID id) {
        return "DELETE FROM " + tableName + " WHERE " + idColumnName + "=#{id}";
    }

    /**
     * 删除所有数据
     */
    public String delete() {
        return "DELETE FROM " + tableName;
    }

    /**
     * 根据id列表批量删除数据
     */
    public String deleteByIdList(Map<String, Object> map) {
        List<ID> list = (List<ID>) map.get("list");

        System.out.println(map);
        StringBuilder idsStr = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            idsStr.append(",#{list[").append(i).append("]}");
        }
        //
        String sql = "DELETE FROM " + tableName + " WHERE   " + idColumnName + " IN  (" + idsStr.toString().replaceFirst(",", "") + ")";
        System.out.println(sql);
        return sql;
    }


    //////////////////////////////find one/////////////////////////////////////

    /**
     * 通过id查找
     */
    public String selectById(ID id) {
        //sql
        return "SELECT * FROM " + tableName + " WHERE " + idColumnName + "=#{id}";
    }


    //////////////////////////////find list/////////////////////////////////////


    public String select() {
        //sql
        return "SELECT * FROM " + tableName;
    }


    public String selectWhere(Map<String, Object> map) {
        Object[] params = (Object[]) map.get("param2");
        String sqlCondition = (String) map.get("param1");

        for (int i = 0; i < params.length; i++) {
            sqlCondition = sqlCondition.replaceFirst("\\?", "#{param2[" + i + "]}");
        }
        //sql
        return "SELECT * FROM " + tableName + " WHERE " + sqlCondition;
    }


    /**
     * 根据id列表批量删除数据
     */
    public String selectByIds(Map<String, Object> map) {
        List<ID> list = (List<ID>) map.get("list");

        System.out.println(map);
        StringBuilder idsStr = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            idsStr.append(",#{list[").append(i).append("]}");
        }//
        return "SELECT  * FROM " + tableName + " WHERE   " + idColumnName + " IN  (" + idsStr.toString().replaceFirst(",", "") + ")";
    }


    ////////////////////////////////////count///////////////////////////////////////////
    public String count() {
        //sql
        return "SELECT COUNT(*) FROM " + tableName;
    }

    public String countWhere(Map<String, Object> map) {
        Object[] params = (Object[]) map.get("param2");
        String sqlCondition = (String) map.get("param1");

        for (int i = 0; i < params.length; i++) {
            sqlCondition = sqlCondition.replaceFirst("\\?", "#{param2[" + i + "]}");
        }
        //sql
        return "SELECT count(*) FROM " + tableName + " WHERE " + sqlCondition;
    }
}
