package com.syh.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.syh.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syh.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author syh
 * @since 2023-07-24
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    IPage<Record> pageCM(IPage<Record> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
