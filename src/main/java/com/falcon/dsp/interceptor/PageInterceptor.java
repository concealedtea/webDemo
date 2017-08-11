package com.falcon.dsp.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author dongbin.yu
 * @from 2016-04-05
 * @since V1.0
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})
})
public class PageInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

    private ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();

    private ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

    private String signature;

    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        Connection conn = (Connection)invocation.getArgs()[0];
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

        //分离代理对象链
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }

        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,DEFAULT_OBJECT_WRAPPER_FACTORY);
        }

        MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.getValue("delegate.mappedStatement");

        String id = mappedStatement.getId();
        if (id.endsWith(signature)) {
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                throw new NullPointerException("page param is null");
            } else {
                PageParameter pageParameter = (PageParameter) metaStatementHandler.getValue("delegate.boundSql.parameterObject.page");
                String originSql = boundSql.getSql();

                //重写sql
                String sql = buildSql(originSql, pageParameter);
                metaStatementHandler.setValue("delegate.boundSql.sql", sql);
                // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
                /*metaStatementHandler.setValue("delegate.rowBounds.offset",RowBounds.NO_ROW_OFFSET);
                metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);*/

                //获取总数
                setPageCount(originSql, conn, mappedStatement, boundSql, pageParameter);
            }

        }

        return invocation.proceed();
    }

    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }


    private String buildSql(String originSql, PageParameter pageParameter) {

        StringBuffer sql = new StringBuffer(originSql);
        int pageSize = pageParameter.getPageSize();
        int start = (pageParameter.getCurrentPage() - 1) * pageSize;
        sql.append(" limit " + start + "," + pageSize);
        return  sql.toString();
    }

    private void setPageCount(String originSql, Connection conn, MappedStatement mappedStatement, BoundSql boundSql, PageParameter pageParameter) {

        String countSql = "select count(1) from (" + originSql +") as total";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(countSql);
            BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), countBoundSql);
            parameterHandler.setParameters(ps);
            rs = ps.executeQuery();

            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }

            pageParameter.setTotalCount(totalCount);
            int totalPage = totalCount == 0 ? 0 : (totalCount - 1) / pageParameter.getPageSize() + 1;
            pageParameter.setTotalPage(totalPage);

        } catch (SQLException e) {
            logger.error("sql execute failed");
        } finally {

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error("PreparedStatement close failed");
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("resultSet close failed");
                }
            }

        }

    }

    public void setProperties(Properties properties) {

        this.signature = properties.getProperty("signature");
    }
}
