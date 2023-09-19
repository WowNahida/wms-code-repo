package com.syh.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.syh.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;



/**
 * <p>
 *  服务类
 * </p>
 *
 * @author syh
 * @since 2023-07-13
 */
public interface UserService extends IService<User> {

    IPage<User> pageC(IPage<User> page);

    IPage<User> pageCM(IPage<User> page, Wrapper wrapper);
}
