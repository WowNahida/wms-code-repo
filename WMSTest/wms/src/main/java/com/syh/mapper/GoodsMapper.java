package com.syh.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.syh.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author syh
 * @since 2023-07-23
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage<Goods> pageCM(IPage<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
