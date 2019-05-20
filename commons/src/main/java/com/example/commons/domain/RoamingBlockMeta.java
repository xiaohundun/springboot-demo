package com.example.commons.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * chou created at 2019-03-28
 * @Description:
 *
 * */
@Getter
@Setter
@NoArgsConstructor
public class RoamingBlockMeta extends BaseObject {
    private String blockId, desc;

    public RoamingBlockMeta(String blockId) {
        this.blockId = blockId;
    }

    public RoamingBlockMeta(String blockId, String desc) {
        this.blockId = blockId;
        this.desc = desc;
    }
}
