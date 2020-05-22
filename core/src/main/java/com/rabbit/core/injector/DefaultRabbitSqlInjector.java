package com.rabbit.core.injector;

import com.rabbit.core.injector.method.service.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this class by created wuyongfei on 2020/5/10 20:53
 **/
public class DefaultRabbitSqlInjector extends AbstractRabbitSqlInjector {
    public DefaultRabbitSqlInjector() {
    }

    /**
     * 获取自定义方法实例
     *
     * @param mapperClass
     * @return
     */
    @Override
    public List<RabbitAbstractMethod> getMethodList(Class<?> mapperClass) {
        return Stream.of(
                new AddBatchObject(),
                new AddObject(),
                new CustomSqlObject(),
                new DeleteBatchByIdObject(),
                new DeleteObject(),
                new GetObject(),
                new GetObjectList(),
                new UpdateBatchByIdObject(),
                new UpdateObject()
        ).collect(Collectors.toList());
    }
}
