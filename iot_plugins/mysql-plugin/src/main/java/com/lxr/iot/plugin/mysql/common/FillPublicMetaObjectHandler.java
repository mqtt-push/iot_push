package com.lxr.iot.plugin.mysql.common;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.util.StringUtils;

import java.util.Date;


/**
 * Mybatis Plus 公共数据填充器
 * @author Administrator
 */
public class FillPublicMetaObjectHandler extends MetaObjectHandler {

    private final static String CREATETIME = "createTime";
    private final static String UPDATETIME = "updateTime";
    private final static String ID = "id";


    @Override
    public void insertFill(MetaObject metaObject) {
        Object idObject = getFieldValByName(ID, metaObject);

        Object createtimeObj = getFieldValByName(CREATETIME, metaObject);

        if (StringUtils.isEmpty(idObject)) {
            setFieldValByName(ID ,IdWorker.getId(),metaObject);
        }
        if (null == createtimeObj) {
            setFieldValByName(CREATETIME,new Date(),metaObject);
        }
        Object updatetimeObj = getFieldValByName(UPDATETIME, metaObject);
        if (null == updatetimeObj) {
            setFieldValByName(UPDATETIME, new Date(), metaObject);
        }

    }


    @Override
    public void updateFill(MetaObject metaObject) {
        insertFill(metaObject);
    }
}
