package com.ruoyi.web.core.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.ruoyi.common.annotation.FactoryDataScope;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * 工厂数据过滤处理
 * 
 * @author ruoyi
 */
@Aspect
@Component
public class FactoryDataScopeAspect
{
    /**
     * 数据权限过滤关键字
     */
    public static final String FACTORY_DATA_SCOPE = "factoryDataScope";

    @Before("@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, FactoryDataScope controllerDataScope) throws Throwable
    {
        clearDataScope(point);
        handleDataScope(point, controllerDataScope);
    }

    protected void handleDataScope(final JoinPoint joinPoint, FactoryDataScope controllerDataScope)
    {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，则不过滤数据
            if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin())
            {
                dataScopeFilter(joinPoint, currentUser, controllerDataScope.factoryAlias(), controllerDataScope.userAlias());
            }
        }
    }

    /**
     * 数据范围过滤
     * 
     * @param joinPoint 切点
     * @param user 用户
     * @param factoryAlias 工厂别名
     * @param userAlias 用户别名
     */
    public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String factoryAlias, String userAlias)
    {
        StringBuilder sqlString = new StringBuilder();
        
        // 拼接工厂过滤逻辑
        if (StringUtils.isNotBlank(factoryAlias))
        {
            sqlString.append(StringUtils.format(" OR {}.factory_id IN ( SELECT factory_id FROM sys_user_factory WHERE user_id = {} ) ", factoryAlias, user.getUserId()));
        }

        if (StringUtils.isNotBlank(userAlias))
        {
            sqlString.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
        }

        if (StringUtils.isNotBlank(sqlString.toString()))
        {
            Object params = joinPoint.getArgs()[0];
            if (StringUtils.isNotNull(params) && params instanceof BaseEntity)
            {
                BaseEntity baseEntity = (BaseEntity) params;
                // 用 AND 包裹
                baseEntity.getParams().put(FACTORY_DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
            }
        }
    }

    /**
     * 拼接权限sql前先清空params.factoryDataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint)
    {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity)
        {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.getParams().put(FACTORY_DATA_SCOPE, "");
        }
    }
}
