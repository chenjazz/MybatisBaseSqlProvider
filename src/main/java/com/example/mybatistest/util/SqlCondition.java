package com.example.mybatistest.util;

import java.lang.reflect.Field;

/**
 * @author Jiazhi
 * @since 2017/8/6
 */
public class SqlCondition {

    private Object p1;
    private Object p2;
    private Object p3;
    private Object p4;
    private Object p5;
    private Object p6;
    private Object p7;
    private Object p8;
    private Object p9;
    private Object p10;
    private Object p11;
    private Object p12;
    private Object p13;
    private Object p14;
    private Object p15;
    private Object p16;

    private String sql;


    public SqlCondition() {
    }

    public SqlCondition(String sql, Object... params) {
        this.sql = sql;
        for (int i = 0; i < params.length; i++) {
            try {
                Field field = this.getClass().getDeclaredField("p" + (i + 1));
                field.setAccessible(true);
                field.set(this, params[i]);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

    }

    public Object getP1() {
        return p1;
    }

    public void setP1(Object p1) {
        this.p1 = p1;
    }

    public Object getP2() {
        return p2;
    }

    public void setP2(Object p2) {
        this.p2 = p2;
    }

    public Object getP3() {
        return p3;
    }

    public void setP3(Object p3) {
        this.p3 = p3;
    }

    public Object getP4() {
        return p4;
    }

    public void setP4(Object p4) {
        this.p4 = p4;
    }

    public Object getP5() {
        return p5;
    }

    public void setP5(Object p5) {
        this.p5 = p5;
    }

    public Object getP6() {
        return p6;
    }

    public void setP6(Object p6) {
        this.p6 = p6;
    }

    public Object getP7() {
        return p7;
    }

    public void setP7(Object p7) {
        this.p7 = p7;
    }

    public Object getP8() {
        return p8;
    }

    public void setP8(Object p8) {
        this.p8 = p8;
    }

    public Object getP9() {
        return p9;
    }

    public void setP9(Object p9) {
        this.p9 = p9;
    }

    public Object getP10() {
        return p10;
    }

    public void setP10(Object p10) {
        this.p10 = p10;
    }

    public Object getP11() {
        return p11;
    }

    public void setP11(Object p11) {
        this.p11 = p11;
    }

    public Object getP12() {
        return p12;
    }

    public void setP12(Object p12) {
        this.p12 = p12;
    }

    public Object getP13() {
        return p13;
    }

    public void setP13(Object p13) {
        this.p13 = p13;
    }

    public Object getP14() {
        return p14;
    }

    public void setP14(Object p14) {
        this.p14 = p14;
    }

    public Object getP15() {
        return p15;
    }

    public void setP15(Object p15) {
        this.p15 = p15;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
