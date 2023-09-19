package com.syh.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.syh.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.syh.entity.Goodstype;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author syh
 * @since 2023-07-23
 */
public interface GoodsService extends IService<Goods> {

    IPage<Goods> pageCM(IPage<Goods> page, Wrapper wrapper);

}
