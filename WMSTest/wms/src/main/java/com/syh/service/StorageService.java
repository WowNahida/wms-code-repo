package com.syh.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.syh.entity.Storage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.syh.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author syh
 * @since 2023-07-22
 */
public interface StorageService extends IService<Storage> {
    IPage<Storage> pageCM(IPage<Storage> page, Wrapper wrapper);

}
