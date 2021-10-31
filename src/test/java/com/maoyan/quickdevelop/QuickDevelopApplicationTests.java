package com.maoyan.quickdevelop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqArticlePostProcesser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqCommentPostProcesser;
import com.maoyan.quickdevelop.common.utils.MyQueryWrapper;
import com.maoyan.quickdevelop.common.utils.annotation.MapperQuery;
import com.maoyan.quickdevelop.common.utils.annotation.type.QueryType;
import com.maoyan.quickdevelop.common.utils.redis.RedisKeyUtils;
import com.maoyan.quickdevelop.system.mapper.DqUserMapper;
import com.maoyan.quickdevelop.system.mapper.TestMybatis;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqArticlePostProcessorMapper;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqCommentPostProcessorMapper;
import com.maoyan.quickdevelop.system.service.IDqArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class QuickDevelopApplicationTests {

    @Autowired
    private DqUserMapper dqUserMapper;

    @Autowired
    private DqArticlePostProcessorMapper dqArticlePostProcessorMapper;

    @Autowired
    private DqCommentPostProcessorMapper dqCommentPostProcessorMapper;

    @Autowired
    private IDqArticleService iDqArticleService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TestMybatis testMybatis;
//    单元测试
    @Test
    void contextLoads() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        DqUser dqUser = new DqUser();
        dqUser.setUserName("maoyan");

        String methodNameFirst = "";
        String methodName = "";
        Class<? extends DqUser> aClass = dqUser.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        //获取属性名称
        for (int i = 1; i < declaredFields.length; i++) {

            System.out.println(declaredFields[i].getName());
            methodNameFirst = declaredFields[i].getName().substring(0,1).toUpperCase();
            methodName = declaredFields[i].getName();
            methodName = "get"+methodNameFirst+methodName.substring(1);
            System.out.println("方法名称为："+methodName);

//            优化
            System.out.println("优化："+"get"+declaredFields[i].getName().substring(0,1).toUpperCase()+declaredFields[i].getName().substring(1));

            //执行方法
            Method method = aClass.getMethod(methodName,null);
            Object invoke = method.invoke(dqUser, null);
            System.out.println("输出为："+invoke);

        }
        for (Field field:
             declaredFields) {

        }
        //获取所有的方法名称
        //但是顺序是与类中的一样，所以可以构建get方法
        //属性名称第一个大写，然后加上get就是，get方法名称
        Method[] methods = aClass.getMethods();
        for (Method method:
             methods) {
            System.out.println(method.getName());
            if (StringUtils.containsIgnoreCase(method.getName(),"get")&&
                    StringUtils.containsIgnoreCase(method.getName(),"username")){
                System.out.println("值为："+method.getName());
            }
        }

    }

    @Test
    void test() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        DqUser dqUser = new DqUser();
        dqUser.setUserName("maoyan");

        //Hashmap是无序的，所以要使用LinkedHashMap
        HashMap<String, Object> queryRules = new LinkedHashMap<>();
//        HashMap<String, Object> queryRules = new HashMap<>();
        queryRules.put("userName", QueryType.EQ);
        queryRules.put("connect",QueryType.OR);
        queryRules.put("nickName",QueryType.EQ);

        MyQueryWrapper<DqUser> myQueryWrapper = new MyQueryWrapper<>();
//        myQueryWrapper.queryAllByAnnotation(dqUser);
        myQueryWrapper.queryAll(dqUser,queryRules);
        //System.out.println(myQueryWrapper.allEq(dqUser));
//        QueryWrapper queryWrapper = new QueryWrapper<DqUser>();
//        queryWrapper.eq("nick_name","maoyan");
        DqUser dqUser1 = dqUserMapper.selectOne(myQueryWrapper);
        System.out.println(dqUser1);

        myQueryWrapper.clear();

    }

    @Test
    void test2() throws ClassNotFoundException, NoSuchFieldException {
        MapperQuery userName = Class.forName("com.maoyan.quickdevelop.common.core.domain.DqUser")
                .getDeclaredField("userName")
                .getAnnotation(MapperQuery.class);

        System.out.println(userName.queryType().name());
    }


    @Test
    void testLambda(){
        String userName = "107";
        QueryWrapper<DqUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(DqUser::getEmail,userName);
        DqUser dqUser = dqUserMapper.selectOne(queryWrapper);
        System.out.println(dqUser);
    }

    @Test
    void testRedis(){
        redisTemplate.opsForValue().set("name","maoyan");
        String name = (String)redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

    @Test
    void testKey(){
        HashMap<String,Long> hashMap = (HashMap<String, Long>) redisTemplate.opsForHash().entries(RedisKeyUtils.MAP_KEY_USER_LIKED);
        System.out.println(hashMap);
    }

    @Test
    void testHashKey(){
        Set<String> keys = redisTemplate.opsForHash().keys(RedisKeyUtils.MAP_KEY_USER_LIKED);
        //将set转换为stream
        List<String> collect = keys.stream()
                .filter(s -> s.toString().startsWith("1"))
                .distinct()
                .map(ss -> ss.split("::"))
                .map(dqUserId -> dqUserId[1])
                .collect(Collectors.toList());

        for (String s :
                collect) {
            System.out.println(s);
        }
    }

    @Test
    void testDelete(){
        String key = RedisKeyUtils.getLikedKey(1L,3L);
        Long delete = redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        System.out.println("删除成功"+delete);
    }

    @Test
    void testCommentMapper(){
        List<DqCommentPostProcesser> dqCommentPostProcessers = dqCommentPostProcessorMapper.selectAllDqCommentPostProcesser(new DqCommentPostProcesser());
        System.out.println(dqCommentPostProcessers);
    }
}
