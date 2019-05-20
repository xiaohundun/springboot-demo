package com.example.commons.domain;
/*
 * chou created at 2019-03-28
 * @Description:
 *
 * */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@CommonsLog
@NoArgsConstructor
public class RoamingDomain extends BaseObject {
    private static final long serialVersionUID = -2486717908846610128L;
    private String version = "1", name, descName;
    private int status;
    private Map<String, BaseObject> blocks = new HashMap<>();

    public RoamingDomain(String name) {
        this.name = name;
    }

    public RoamingBlock addBlock(String blockId) {
        return this.addBlock(blockId, null);
    }

    public RoamingBlock addBlock(String blockId, String descName) {
        if (blockId == null)
            return null;
        RoamingBlock roamingBlock = new RoamingBlock(blockId, descName);
        blocks.put(blockId, roamingBlock);
        return roamingBlock;
    }

    public RoamingBlock addBlock(RoamingBlock block) {
        if (block == null)
            return null;
        String blockId = block.getBlockMeta().getBlockId();

        if (blocks.get(blockId) != null)
            return (RoamingBlock) blocks.get(blockId);
        blocks.put(blockId, block);
        return block;
    }

    public RoamingBlock getBlock(String blockId) {
        if (blockId == null || StringUtils.isEmpty(blockId))
            return null;
        return (RoamingBlock) blocks.get(blockId);
    }
}
