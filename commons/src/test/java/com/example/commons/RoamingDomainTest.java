package com.example.commons;
/*
 * chou created at 2019-03-28
 * @Description:
 *
 * */

import com.example.commons.domain.RoamingBlock;
import com.example.commons.domain.RoamingBlockMeta;
import com.example.commons.domain.RoamingDomain;
import com.example.commons.utils.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoamingDomainTest {

    @Test
    public void test() throws IOException {
        RoamingDomain roamingDomain = new RoamingDomain("test");
        roamingDomain.addBlock("testBlock");
        RoamingBlock block = roamingDomain.getBlock("testBlock");
        RoamingBlockMeta meta = block.getBlockMeta();
        System.out.println(meta.getBlockId());
        List<Map> rows = new ArrayList<>();
        Map m1 = new TestEntity("jason", "male", 22).toMap();
        System.out.println(m1);
        rows.add(m1);
        block.setRows(rows);
        //  不序列化空字段
        ObjectMapperUtil.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String blockJSON = block.toJSONString();
        String metaJSON = meta.toJSONString();
        String domainJSON = roamingDomain.toJSONString();
        System.out.println(blockJSON);
        System.out.println(metaJSON);
        System.out.println(domainJSON);
        //  属性没有对上也不报错
        ObjectMapperUtil.getObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        RoamingBlock roamingBlock = ObjectMapperUtil.getObjectMapper().readValue(blockJSON, RoamingBlock.class);
        RoamingDomain roamingDomainDes = ObjectMapperUtil.getObjectMapper().readValue(domainJSON, RoamingDomain.class);
        RoamingBlockMeta roamingBlockMeta = ObjectMapperUtil.getObjectMapper().readValue(metaJSON, RoamingBlockMeta.class);
        System.out.println(roamingBlock);
        System.out.println(roamingBlock.getRows());
        System.out.println(roamingDomainDes);
        System.out.println(roamingBlockMeta);
    }
}
