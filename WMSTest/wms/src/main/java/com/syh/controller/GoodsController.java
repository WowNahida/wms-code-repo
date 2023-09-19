package com.syh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.syh.common.QueryPageParam;
import com.syh.common.Result;
import com.syh.entity.Goods;
import com.syh.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author syh
 * @since 2023-07-23
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods) {
        return goodsService.save(goods) ? Result.succeed() : Result.fail();
    }

    //修改表单传输内容
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods) {
        return goodsService.updateById(goods) ? Result.succeed() : Result.fail();
    }

    //删除表中内容
    @GetMapping("/del")
    public Result del(@RequestParam Integer id) {
        return goodsService.removeById(id) ? Result.succeed() : Result.fail();
    }

    //查询提交参数与表中姓名是否相等
    @GetMapping("/findByName")
    public Result findByName(@RequestParam String name) {
        List<Goods> list = goodsService.lambdaQuery().eq(Goods::getName, name).list();
        return list.size() > 0 ? Result.succeed() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPageCP(@RequestBody QueryPageParam query) {
        //System.out.println(query);
        //System.out.println("num=>" + query.getPageNum());
        //System.out.println("size=>" + query.getPageSize());

        HashMap param = query.getParam();
        //System.out.println("param.name=>" + (String) param.get("name"));
        String name = (String) param.get("name");
        String storage = (String) param.get("storage");
        String goodstype = (String) param.get("goodstype");
        //String roleId = (String) param.get("roleId");
        //System.out.println("param.age=>" + (String) param.get("age"));

        Page page = new Page();
        //当前页数(从1开始)
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //通过name使用lambdaQueryWrapper进行模糊查询
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Goods::getName, name);
            //lambdaQueryWrapper.eq(User::getName,goods.getName());

        }

        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
            lambdaQueryWrapper.eq(Goods::getStorage, storage);
            //lambdaQueryWrapper.eq(User::getName,goods.getName());

        }

        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)) {
            lambdaQueryWrapper.eq(Goods::getGoodstype, goodstype);
            //lambdaQueryWrapper.eq(User::getName,goods.getName());

        }

        //通过大于等于roleId进行筛选查询
       /* if (StringUtils.isNotBlank(roleId)) {
            lambdaQueryWrapper.ge(User::getRoleId, roleId);
            //lambdaQueryWrapper.eq(User::getName,goods.getName());

        }*/

        //服务层调用自定义pageCM()方法
        IPage<Goods> result = goodsService.pageCM(page, lambdaQueryWrapper);

        //可添加判定语句
        //if(result.getRecords()!=0)

        return Result.succeed(result.getTotal(), result.getRecords());
    }
}
