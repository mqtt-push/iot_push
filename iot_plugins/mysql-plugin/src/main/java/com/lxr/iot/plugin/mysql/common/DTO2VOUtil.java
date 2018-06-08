package com.lxr.iot.plugin.mysql.common;


import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 ShowSoft
 * @创建时间 2016/7/14 10:09
 * @描述
 */
public class DTO2VOUtil {

    /**
     * 前台显示对象转换正POJO对象
     *
     * @param dto
     * @param vo
     * @param <T>
     * @return
     */
    public static <T> T DtoToVo(Object dto, T vo) {
        if(null == dto){
            return null;
        }
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }

    public static <T> T DtoToVo(Object dto, Class<T> vo) {
        if(null == dto){
            return null;
        }
        try {
            T instance = vo.newInstance();
            BeanUtils.copyProperties(dto, instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * POJO转VO
     *
     * @param vo
     * @param dto
     * @param <T>
     * @return
     */
    public static <T> T VoToDto(Object vo, T dto) {
        if(null == vo){
            return null;
        }
        BeanUtils.copyProperties(vo, dto);
        return dto;
    }

    public static <T> T VoToDto(Object vo, Class<T> dto) {
        if(null == vo){
            return null;
        }
        try {
            T instance = dto.newInstance();
            BeanUtils.copyProperties(vo, instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List copyList(List<? extends Object> poList, Class voClass) {
        if(null == poList){
            return null;
        }
        List voList = new ArrayList();
        Object voObj = null;
        for (Object poObj : poList) {
            try {
                voObj = voClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(poObj, voObj);
            voList.add(voObj);
        }
        return voList;

    }
}
