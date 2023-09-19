package com.syh.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.syh.entity.Goodstype;
import com.syh.mapper.GoodstypeMapper;
import com.syh.service.GoodstypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-07-22
 */
@Service
public class GoodstypeServiceImpl extends ServiceImpl<GoodstypeMapper, Goodstype> implements GoodstypeService {

    @Resource
    private GoodstypeMapper goodstypeMapper;
    @Override
    public IPage<Goodstype> pageCM(IPage<Goodstype> page, Wrapper wrapper) {
        return goodstypeMapper.pageCM(page,wrapper);
    }
}
