package com.maoyan.quickdevelop.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.maoyan.quickdevelop.common.utils.annotation.MapperQuery;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.function.Predicate;

/**
 * @author 猫颜
 * @date 2021/6/6 18:47
 * 自定义的查询方法（用来代替以前的查询工具类）
 */
public class MyQueryWrapper<T> extends QueryWrapper<T> {

    String getMethodName = "";
    Field field = null;
    Method method = null;
    Object getValue = null;

    public MyQueryWrapper() {
        super();
    }

    public MyQueryWrapper(T entity) {
        super(entity);
    }

    public MyQueryWrapper(T entity, String... columns) {
        super(entity, columns);
    }

    @Override
    public QueryWrapper<T> select(String... columns) {
        return super.select(columns);
    }

    @Override
    public QueryWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        return super.select(entityClass, predicate);
    }

    @Override
    public String getSqlSelect() {
        return super.getSqlSelect();
    }

    @Override
    protected String columnSqlInjectFilter(String column) {
        return super.columnSqlInjectFilter(column);
    }

    @Override
    public LambdaQueryWrapper<T> lambda() {
        return super.lambda();
    }

    @Override
    protected QueryWrapper<T> instance() {
        return super.instance();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public QueryWrapper<T> eq(String column, Object val) {
        return super.eq(column, val);
    }

    /**
     * 状态控制，采用控制sql语句来控制，也可以直接使用mybatis-plus的逻辑删除功能。
     *
     * @param column
     * @param val
     * @return
     */
    @Deprecated
    public QueryWrapper<T> statuseq(String column, Object val) {
        if (StpUtil.isLogin() && StpUtil.hasRole("管理员")) {
            return super.eq(column, val);
        } else {
            return super.eq("status", "0").eq(column, val);
        }
    }

    @Deprecated
    public QueryWrapper<T> statuseq() {
//        if (StpUtil.isLogin() && StpUtil.hasRole("管理员")) {
//            //使用false将eq后面的对比省略掉(不行，似乎无法架空的样子)
//            return super.eq("status", "0").or().eq("status", "1");
//        } else {
            return super.eq("status", "0");
//        }

    }

    /**
     * TODO 自定义查询方法
     *
     * @param object
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T>
     * @author 猫颜
     * @date 上午11:33
     */
    public QueryWrapper<T> queryAll(T object, HashMap<String, Object> queryRules) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Class<?> aClass = object.getClass();

//        for (Map.Entry<String,Object> entry:
//             queryRules.entrySet()) {
//            System.out.println("所有值"+entry.getKey());
//        }
        for (String declaredField :
                queryRules.keySet()) {

            if (StringUtils.equals(declaredField, "connect")) {
                switch (String.valueOf(queryRules.get(declaredField))) {
                    case "AND":
                        //并且
                        break;
                    case "OR":
                        //或
                        MyQueryWrapper.this.or();
                        break;
                }
                continue;
            }
            field = aClass.getDeclaredField(declaredField);

            getMethodName = "get" + declaredField.substring(0, 1).toUpperCase() + declaredField.substring(1);
            method = aClass.getMethod(getMethodName, null);
            getValue = method.invoke(object, null);
            System.out.println("进入");
            System.out.println(declaredField);
            System.out.println(field.getName());
            System.out.println(getValue);
            if ((getValue != null && !StringUtils.equals(getValue.toString(),"null"))&&
                    (field.getName() != null && !StringUtils.equals(field.getName(),"null"))) {

                System.out.println("实体类的字段名: " + declaredField);
                System.out.println("表的列名值：" + field.getAnnotation(TableField.class).value());
                System.out.println("get获取值：" + getValue.toString());
                switch (String.valueOf(queryRules.get(field.getName()))) {
                    case "EQ":
                        //等于
                        super.eq(field.getAnnotation(TableField.class).value(), getValue.toString());
                        break;
                    case "NE":
                        //不等于
                        super.ne(field.getAnnotation(TableField.class).value(), getValue.toString());
                        break;
                    case "GT":
                        //大于
                        super.gt(field.getAnnotation(TableField.class).value(), getValue.toString());
                        break;
                    case "GE":
                        //大于等于
                        super.ge(field.getAnnotation(TableField.class).value(), getValue.toString());
                        break;
                    case "LT":
                        super.lt(field.getAnnotation(TableField.class).value(), getValue.toString());
                        //小于
                        break;
                    case "LE":
                        super.le(field.getAnnotation(TableField.class).value(), getValue.toString());
                        //小于等于
                        break;
                    case "LIKE":
                        super.like(field.getAnnotation(TableField.class).value(), getValue.toString());
                        //存在
                        break;
                    case "NOTLIKE":
                        super.notLike(field.getAnnotation(TableField.class).value(), getValue.toString());
                        //不存在
                        break;
                }
            }
        }
        System.out.println("内容为"+super.toString());
        return super.isNotNull("status");
    }


    /**
     * TODO 自定义遍历查询方法（通过注解）
     *
     * @param object
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T>
     * @author 猫颜
     * @date 下午9:20
     */
    public QueryWrapper<T> queryAllByAnnotation(T object) throws IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {

        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
//        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        //第一个为serialVersionUID(舍去) i为1 绕过
        //获取object的所有字段
        for (int i = 1; i < declaredFields.length; i++) {
            field = declaredFields[i];
            //类中的成员变量为private,故必须进行此操
            field.setAccessible(true);
//            不能直接获取到值，可以通过构造get方法，或者调用类的get方法来获取值
//            通过StringUtils的containsIgnoreCase方法来获取方法名称
//            通过上面那种方法，有循环嵌套，性能需要优化
//            所以采取拼接get方法名
/**
 *
 * @author 猫颜
 * @date         //获取属性名称
 *         for (Field field:
 *              declaredFields) {
 *             System.out.println(field.getName());
 *             methodNameFirst = field.getName().substring(0,1).toUpperCase();
 *             methodName = field.getName();
 *             methodName = "get"+methodNameFirst+methodName.substring(1);
 *             System.out.println("方法名称为："+methodName);
 *
 * //            优化
 *             System.out.println("优化："+"get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1));
 *         } 下午5:18
 * @param object
 * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T>
 */
//获取get方法名称
            getMethodName = "get" + declaredFields[i].getName().substring(0, 1).toUpperCase() + declaredFields[i].getName().substring(1);
            System.out.println(getMethodName);
//            执行get方法获取属性值
            //执行方法
            Method method = aClass.getMethod(getMethodName, null);
            Object getValue = method.invoke(object, null);
            //System.out.println("输出为："+invoke);
//            System.out.println("优化："+"get"+declaredFields[i].getName().substring(0,1).toUpperCase()+declaredFields[i].getName().substring(1));
//            如果值不为空，则添加到eq中
            if (getValue != null) {
                //映射关系
//                return super.eq(,field.get(object));
                //获取其注解的值
//                后面可以采用查询方法注释来解决，不同的查询所需的问题
                System.out.println("实体类的字段名: " + field.getName());
                System.out.println("表的列名值：" + field.getAnnotation(TableField.class).value());
                System.out.println("get获取值：" + getValue.toString());

//                获取MapperQuery注解的类型
                String mapperTypeName = field.getAnnotation(MapperQuery.class).queryType().name();

                switch (mapperTypeName) {
                    case "EQ":
                        //等于
                        super.eq(field.getAnnotation(TableField.class).value(), getValue.toString());
                        break;
                    case "NE":
                        //不等于
                        super.ne(field.getAnnotation(TableField.class).value(), getValue.toString());
                        break;
                    case "GT":
                        //大于
                        super.gt(field.getAnnotation(TableField.class).value(), getValue.toString());
                        break;
                    case "GE":
                        //大于等于
                        super.ge(field.getAnnotation(TableField.class).value(), getValue.toString());
                        break;
                    case "LT":
                        super.lt(field.getAnnotation(TableField.class).value(), getValue.toString());
                        //小于
                        break;
                    case "LE":
                        super.le(field.getAnnotation(TableField.class).value(), getValue.toString());
                        //小于等于
                        break;
                    case "LIKE":
                        super.like(field.getAnnotation(TableField.class).value(), getValue.toString());
                        //存在
                        break;
                    case "NOTLIKE":
                        super.notLike(field.getAnnotation(TableField.class).value(), getValue.toString());
                        //不存在
                        break;
                    default:
                        //默认是eq
                        super.eq(field.getAnnotation(TableField.class).value(), getValue.toString());
                }
            }
            //MyQueryWrapper.this.eq(field.getAnnotation(TableField.class).value(), getValue.toString());
        }

        return super.eq(false,null,null);
    }

}
