package com.rabbit.core.injector.method.service;

import com.rabbit.common.utils.SqlScriptUtil;
import com.rabbit.core.bean.TableInfo;
import com.rabbit.core.injector.RabbitAbstractMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Map;

/**
 * @ClassName UpdateObject
 * @ClassExplain: 修改实例
 * @Author Duxiaoyu
 * @Date 2020/5/5 11:06
 * @Since V 1.0
 */
public class UpdateObject extends RabbitAbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        StringBuffer sql=new StringBuffer("<script>");
        sql.append("\nupdate ${sqlMap.TABLE_NAME}");
        sql.append(SqlScriptUtil.convertTrim("set",null,null,",",
                SqlScriptUtil.convertForeach("sqlMap.UPDATE_VALUE.keys","item","i",null,null,null,
                        SqlScriptUtil.convertIf("objectMap[item]!=null","${sqlMap.UPDATE_VALUE[item]}")))+
                SqlScriptUtil.convertIf("sqlMap.UPDATE_WHERE!=null and sqlMap.UPDATE_WHERE!=''","${sqlMap.UPDATE_WHERE}"));
        sql.append("\n</script>");
        SqlSource sqlSource=languageDriver.createSqlSource(configuration,sql.toString(), Map.class);
        return addUpdateMappedStatement(mapperClass,Map.class,"updateObject",sqlSource);
    }
}