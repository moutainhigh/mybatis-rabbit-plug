package com.rabbit.core.injector.method.base;

import com.rabbit.common.utils.SqlScriptUtil;
import com.rabbit.core.bean.TableInfo;
import com.rabbit.core.constructor.DefaultAbstractWrapper;
import com.rabbit.core.enumation.MySqlKeyWord;
import com.rabbit.core.injector.RabbitAbstractMethod;
import com.rabbit.core.parse.ParseClass2TableInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Map;

/**
 * @ClassName InsertBatch
 * @ClassExplain: 批量新增
 * @Author Duxiaoyu
 * @Date 2020/5/30 17:29
 * @Since V 1.0
 */
public class InsertBatch extends RabbitAbstractMethod {

    @Override
    public void injectMappedStatement(Class<?> mapperClass, Class<?> modelClass) {
        if (ObjectUtils.isEmpty(modelClass)) {
            return;
        }

        TableInfo tableInfo = ParseClass2TableInfo.parseClazzToTableInfo(modelClass);
        Map<String, String> sqlMap = new DefaultAbstractWrapper(tableInfo).insertSqlGenerate();
        String keyProperty = null;
        String keyColumn = null;
        KeyGenerator keyGenerator = new NoKeyGenerator();

        String value = sqlMap.get("INSERT_VALUE");
        value = value.replaceAll("#\\{", "#{obj.");
        StringBuffer sql = new StringBuffer("<script>");
        sql.append(sqlMap.get("INSERT_HEAD") + "\t");
        sql.append(sqlMap.get("TABLE_NAME") + "\t");
        sql.append(sqlMap.get("INSERT_PAM_LEFT_BRA") + "\t");
        sql.append(sqlMap.get("INSERT_PARAMETER") + "\t");
        sql.append(sqlMap.get("INSERT_PAM_RIGHT_BRA") + "\t");
        sql.append(MySqlKeyWord.VALUES.getValue() + "\t");
        sql.append(SqlScriptUtil.convertForeach("entityList", "obj", null, null, ",", null, "(" + value + ")"));
        sql.append("</script>");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql.toString(), modelClass);
        addInsertMappedStatement(mapperClass, modelClass, StringUtils.uncapitalize(this.getClass().getSimpleName()), sqlSource, keyGenerator, keyProperty, keyColumn);
    }
}
