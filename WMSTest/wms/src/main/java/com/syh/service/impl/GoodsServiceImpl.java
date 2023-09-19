package com.syh.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.syh.entity.Goods;
import com.syh.mapper.GoodsMapper;
import com.syh.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-07-23
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public IPage<Goods> pageCM(IPage<Goods> page, Wrapper wrapper) {
        return goodsMapper.pageCM(page,wrapper);
    }
}
