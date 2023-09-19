package com.syh.common;

import lombok.Data;

import java.util.HashMap;

/**
 * @author 孙远航
 * @version 1.0
 * 查询页面参数
 */
@Data
public class QueryPageParam {
    //默认
    //页面大小
    private static int PAGE_SIZE=20;
    //当前页
    private static int PAGE_NUM=1;

    private int pageSize=PAGE_SIZE;
    private int pageNum=PAGE_NUM;

    private HashMap param = new HashMap();

}
