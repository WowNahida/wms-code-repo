package com.syh.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.syh.entity.Goodstype;
import com.baomidou.mybatisplus.extension.service.IService;
import com.syh.entity.Storage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author syh
 * @since 2023-07-22
 */
public interface GoodstypeService extends IService<Goodstype> {
    IPage<Goodstype> pageCM(IPage<Goodstype> page, Wrapper wrapper);
}
