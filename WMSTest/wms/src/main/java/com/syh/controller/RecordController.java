package com.syh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.syh.common.QueryPageParam;
import com.syh.common.Result;
import com.syh.entity.Goods;
import com.syh.entity.Record;
import com.syh.service.GoodsService;
import com.syh.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author syh
 * @since 2023-07-24
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @Autowired
    private GoodsService goodsService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Record record) {
        Goods goods = goodsService.getById(record.getGoods());
        //入库的数量
        int n = record.getCount();
        //修改对应goods对象的数量
        if ("2".equals(record.getAction())) {
            n = -n;
            record.setCount(n);
        }
        goods.setCount(goods.getCount() + n);
        //数据库中修改
        goodsService.updateById(goods);
        return recordService.save(record) ? Result.succeed() : Result.fail();
    }

    //修改表单传输内容
    @PostMapping("/update")
    public Result update(@RequestBody Record record) {
        return recordService.updateById(record) ? Result.succeed() : Result.fail();
    }

    //删除表中内容
    @GetMapping("/del")
    public Result del(@RequestParam Integer id) {
        return recordService.removeById(id) ? Result.succeed() : Result.fail();
    }


    @PostMapping("/listPage")
    public Result listPageCP(@RequestBody QueryPageParam query) {

        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String storage = (String) param.get("storage");
        String goodstype = (String) param.get("goodstype");
        String userId = (String) param.get("userId");
        String roleId = (String) param.get("roleId");

        Page page = new Page();
        //当前页数(从1开始)
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("record.goods=g.id and g.storage=s.id and g.goodstype = gt.id");

        if ("2".equals(roleId)) {
            queryWrapper.apply("record.userId=" + userId);
        }
        //通过name使用lambdaQueryWrapper进行模糊查询
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like("g.name", name);
            //lambdaQueryWrapper.eq(User::getName,record.getName());

        }

        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
            queryWrapper.eq("s.id", storage);
            //lambdaQueryWrapper.eq(User::getName,record.getName());

        }

        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)) {
            queryWrapper.eq("gt.id", goodstype);
            //lambdaQueryWrapper.eq(User::getName,record.getName());

        }


        //服务层调用自定义pageCM()方法
        IPage<Record> result = recordService.pageCM(page, queryWrapper);

        //可添加判定语句
        //if(result.getRecords()!=0)

        return Result.succeed(result.getTotal(), result.getRecords());
    }

}
