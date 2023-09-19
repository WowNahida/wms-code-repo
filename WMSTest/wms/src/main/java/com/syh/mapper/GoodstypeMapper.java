package com.syh.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.syh.entity.Goodstype;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author syh
 * @since 2023-07-22
 */
@Mapper
public interface GoodstypeMapper extends BaseMapper<Goodstype> {

    IPage<Goodstype> pageCM(IPage<Goodstype> page, @Param(Constants.WRAPPER)Wrapper wrapper);
}
