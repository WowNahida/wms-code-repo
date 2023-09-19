package com.syh.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.syh.entity.Record;
import com.syh.mapper.RecordMapper;
import com.syh.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-07-24
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Resource
    private RecordMapper recordMapper;

    @Override
    public IPage<Record> pageCM(IPage<Record> page, Wrapper wrapper) {
        return recordMapper.pageCM(page,wrapper);
    }
}
