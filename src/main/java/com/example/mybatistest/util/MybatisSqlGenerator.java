//package com.example.mybatistest.util;
//
//import com.example.mybatistest.dao.Student;
//import com.google.common.base.Joiner;
//import org.springframework.util.StringUtils;
//
//import javax.persistence.Column;
//import javax.persistence.Table;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.example.mybatistest.util.MyBatisExtUtils.*;
//
///**
// * @author Jiazhi
// * @since 2017/8/5
// */
//public class MybatisSqlGenerator {
//
//
//    public static String getTableName(Class entityClass) {
//        String tableName = camelToUnderline(entityClass.getSimpleName());
//
//
//        Table annotation = (Table) entityClass.getAnnotation(Table.class);
//
//
//        if (annotation != null) {
//            if (!StringUtils.isEmpty(annotation.name())) {
//                tableName = annotation.name();
//            }
//        }
//        return tableName;
//    }
//
//
//    public static List<String> getAllColumns(Object entity, List<Field> allField) {
//        List<String> allColumn = new ArrayList<>();
//        for (Field field : allField) {
////            field.setAccessible(true);
//            Column annotation = field.getAnnotation(Column.class);
//            if (annotation != null && !StringUtils.isEmpty(annotation.name())) {
//                allColumn.add(annotation.name());
//            } else {
//                allColumn.add(camelToUnderline(field.getName()));
//            }
//        }
//        return allColumn;
//    }
//
//    public static List<String> getNotNullFieldColumns(Object entity, List<Field> allField) {
//        List<String> allColumn = new ArrayList<>();
//        for (Field field : allField) {
//            field.setAccessible(true);
//            if (getFieldValue(entity, field) != null) {
//                Column annotation = field.getAnnotation(Column.class);
//                if (annotation != null && !StringUtils.isEmpty(annotation.name())) {
//                    allColumn.add(annotation.name());
//                } else {
//                    allColumn.add(camelToUnderline(field.getName()));
//                }
//            }
//
//        }
//        return allColumn;
//    }
//
//
//
//    public static String insert(Object entity) {
//        List<Field> allField = getAllField(entity);
//        List<String> fieldPlaceHolders =
//                allField.stream().map(field -> "#{" + field.getName() + "}").collect(Collectors.toList());
//
//        List<String> allColumns = getAllColumns(entity, allField);
//
//        return "INSERT INTO " +
//                getTableName(entity.getClass()) + "(" + Joiner.on(",").join(allColumns) +
//                ") VALUES (" + Joiner.on(",").join(fieldPlaceHolders) + ")";
//    }
//
//    public static String selectAll(Class entityClass) {
//        return "SELECT * FROM " + getTableName(entityClass);
//    }
//
//
//    public static String selectAllWhere(Class entityClass) {
//        return "SELECT * FROM " + getTableName(entityClass) + " WHERE 1=1 AND ${sql}";
//    }
//
//
//}
