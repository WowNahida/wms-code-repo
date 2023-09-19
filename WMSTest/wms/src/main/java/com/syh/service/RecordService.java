package com.syh.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.syh.entity.Goods;
import com.syh.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author syh
 * @since 2023-07-24
 */
public interface RecordService extends IService<Record> {

    IPage<Record> pageCM(IPage<Record> page, Wrapper wrapper);
}
