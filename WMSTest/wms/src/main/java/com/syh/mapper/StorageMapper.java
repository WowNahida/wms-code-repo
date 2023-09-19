package com.syh.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.syh.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syh.entity.User;
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
public interface StorageMapper extends BaseMapper<Storage> {
    IPage<Storage> pageCM(IPage<Storage> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
