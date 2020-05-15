package com.rabbit.core.module;

/**
 * this class by created wuyongfei on 2020/5/10 23:09
 **/
public class RabbitMybatisPlugVersion {
    private RabbitMybatisPlugVersion() {
    }

    public static String getVersion() {
        Package pkg = RabbitMybatisPlugVersion.class.getPackage();
        return pkg != null && pkg.getImplementationVersion() != null ? pkg.getImplementationVersion() : "version";
    }
}