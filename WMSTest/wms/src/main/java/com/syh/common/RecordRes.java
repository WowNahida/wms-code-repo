package com.syh.common;

import com.syh.entity.Record;
import lombok.Data;

/**
 * @author 孙远航
 * @version 1.0
 * 出入库记录返回类型,通过sql语句把Record类中对应的id换位name
 */
@Data
public class RecordRes extends Record {
    private String username;
    private String adminname;
    private String goodsname;
    private String storagename;
    private String goodstypename;


}
