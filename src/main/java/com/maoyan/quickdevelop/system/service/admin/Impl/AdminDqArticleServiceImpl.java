package com.maoyan.quickdevelop.system.service.admin.Impl;

import com.github.pagehelper.PageHelper;
import com.maoyan.quickdevelop.common.utils.StringUtils;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.core.domain.DqArticle;
import com.maoyan.quickdevelop.common.core.domain.DqType;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.MyQueryWrapper;
import com.maoyan.quickdevelop.system.mapper.DqArticleMapper;
import com.maoyan.quickdevelop.system.service.admin.IAdminDqArticleService;
import com.maoyan.quickdevelop.system.service.admin.IAdminDqUserService;
import com.maoyan.quickdevelop.system.service.admin.IAdminIDqTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021年07月12日 下午4:41
 * 类名称：
 * 类作用：TODO
 */
@Service
public class AdminDqArticleServiceImpl implements IAdminDqArticleService {
    @Autowired
    private DqArticleMapper dqArticleMapper;
    @Autowired
    private IAdminIDqTypeService iAdminIDqTypeService;
    @Autowired
    private IAdminDqUserService iAdminDqUserService;
    MyQueryWrapper<DqArticle> myQueryWrapper = new MyQueryWrapper<>();
    @Override
    public List<DqArticle> selectAllDqArticle(int pageNum, int pageSize, DqArticle dqArticle) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        PageHelper.startPage(pageNum, pageSize);
        myQueryWrapper.lambda().eq(DqArticle::getArticleId,dqArticle.getArticleId())
                .like(DqArticle::getArticleTitle,dqArticle.getArticleTitle())
                .like(DqArticle::getArticleContent,dqArticle.getArticleContent())
                .eq(DqArticle::getAuthorId,dqArticle.getAuthorId())
                .eq(DqArticle::getTypeId,dqArticle.getTypeId())
                .eq(DqArticle::getStatus,"0");
//        HashMap<String, Object> queryRules = new LinkedHashMap<>();
//        queryRules.put("articleId",QueryType.EQ);
//        queryRules.put("articleTitle", QueryType.LIKE);
//        queryRules.put("articleContent",QueryType.LIKE);
//        queryRules.put("authorNickName",QueryType.EQ);
//        queryRules.put("authorUsername",QueryType.EQ);
//        queryRules.put("autorId",QueryType.EQ);
//        queryRules.put("typeId",QueryType.EQ);
//        queryRules.put("status",QueryType.EQ);
//        myQueryWrapper.queryAll(dqArticle, queryRules);
        List<DqArticle> dqArticles = dqArticleMapper.selectList(myQueryWrapper);
        myQueryWrapper.clear();
//        queryRules.clear();
        return dqArticles;
    }

    @Override
    public DqArticle selectDqArticleById(Long dqArticleId) {
        DqArticle dqArticle = dqArticleMapper.selectById(dqArticleId);
        return dqArticle;
    }

    @Override
    public DqArticle selectDqArticleByTitle(String title) {
        myQueryWrapper.eq("articleTitle",title);
        DqArticle dqArticle = dqArticleMapper.selectOne(myQueryWrapper);
        myQueryWrapper.clear();
        return dqArticle;
    }

    @Override
    public List<DqArticle> selectDqArticlesByTypeId(int pageNum, int pageSize, Long typeId) {
        PageHelper.startPage(pageNum, pageSize);
        /** 对typeid校验,将Long转换为String **/
        myQueryWrapper.statuseq("type_id",String.valueOf(typeId));
        DqType dqType = iAdminIDqTypeService.selectDqTypeById(typeId);
        if (StringUtils.isNull(dqType)){
            throw new CustomException("此类型不存在", HttpStatus.NOT_FOUND);
        }
        List<DqArticle> dqArticles = dqArticleMapper.selectList(myQueryWrapper);
        //清除queryWrapper的数据，防止给其他的带来影响
        myQueryWrapper.clear();
        return dqArticles;
    }

    @Override
    public List<DqArticle> selectDqArticlesByAuthorId(int pageNum, int pageSize, Long authorId) {
        PageHelper.startPage(pageNum, pageSize);
        /** 对authorid校验,将Long转换为String **/
        myQueryWrapper.statuseq("author_id",String.valueOf(authorId));
        DqUser dqUser = iAdminDqUserService.selectDqUserById(authorId);
        if (StringUtils.isNull(dqUser)){
            throw new CustomException("此用户不存在",HttpStatus.NOT_FOUND);
        }
        List<DqArticle> dqArticles = dqArticleMapper.selectList(myQueryWrapper);
        //清除queryWrapper的数据，防止给其他的带来影响
        myQueryWrapper.clear();
        return dqArticles;
    }

    @Override
    public List<DqArticle> selectDqArticlesByAuthorUsername(int pageNum, int pageSize, String authorUsername) {
        PageHelper.startPage(pageNum, pageSize);
        /** 对authornickname校验 **/
        myQueryWrapper.statuseq("author_username",authorUsername);
        DqUser dqUser = iAdminDqUserService.selectDqUserByUserName(authorUsername);
        if (StringUtils.isNull(dqUser)){
            throw new CustomException("此用户不存在",HttpStatus.NOT_FOUND);
        }
        List<DqArticle> dqArticles = dqArticleMapper.selectList(myQueryWrapper);
        //清除queryWrapper的数据，防止给其他的带来影响
        myQueryWrapper.clear();
        return dqArticles;
    }



    @Override
    public int insertDqArticle(DqArticle dqArticle) {
        int insert = dqArticleMapper.insert(dqArticle);
        return insert;
    }

    @Override
    public int updateDqArticle(DqArticle dqArticle) {
        int i = dqArticleMapper.updateById(dqArticle);
        return i;
    }

    @Override
    public int deleteDqArticleById(Long articleId) {
        int i = dqArticleMapper.deleteById(articleId);
        return i;
    }
}
