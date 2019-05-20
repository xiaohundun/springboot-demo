package com.example.commons.domain;
/*
 * chou created at 2019-03-28
 * @Description:
 *
 * */

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@CommonsLog
public class DaoBase implements Serializable {
    public void fromMap(Map map) {
        try {
            BeanUtils.copyProperties(this, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error(e);
        }
    }

    ;

    public Map toMap() {
        try {
            return BeanUtils.describe(this);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e);
            return new HashMap();
        }
    }

    ;
}
