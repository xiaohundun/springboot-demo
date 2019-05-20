package com.example.commons.domain;
/*
 * chou created at 2019-03-28
 * @Description:
 *
 * */


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@CommonsLog
@NoArgsConstructor
public class RoamingBlock extends BaseObject {
    private static final long serialVersionUID = 9011789204436838197L;
    private List<Map> rows;
    private RoamingBlockMeta blockMeta;

    public RoamingBlock(@NonNull String blockId) {
        this.blockMeta = new RoamingBlockMeta(blockId);
    }

    public RoamingBlock(@NonNull String blockId, String descName) {
        this.blockMeta = new RoamingBlockMeta(blockId, descName);
    }

    public boolean addRows(List rows) {
        if (rows == null)
            return false;
        for (Object row : rows) {
            if (row instanceof Map) {
                this.addRow((Map) row);
            } else if (row instanceof DaoBase) {
                this.addRow(((DaoBase) row).toMap());
            } else {
                try {
                    this.addRow(BeanUtils.describe(row));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    if (log.isDebugEnabled())
                        log.debug("addRow -e:" + e);
                }
            }
        }
        return false;
    }

    public boolean addRow(Object row) {
        if (row == null)
            return false;
        if (row instanceof Map) {
            rows.add((Map) row);
        } else {
            if (row instanceof DaoBase) {
                rows.add(((DaoBase) row).toMap());
            } else {
                try {
                    rows.add(BeanUtils.describe(row));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    if (log.isDebugEnabled()) {
                        log.debug("addRow -e" + e);
                    }
                }
            }
        }
        return false;
    }

    public Map deleteRow(int index) {
        return (Map) rows.remove(index);
    }

    public int getSize() {
        return rows.size();
    }
}
