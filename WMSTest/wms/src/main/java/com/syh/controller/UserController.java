package com.syh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.syh.common.QueryPageParam;
import com.syh.common.Result;
import com.syh.entity.Menu;
import com.syh.entity.User;
import com.syh.service.MenuService;
import com.syh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author syh
 * @since 2023-07-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
       List list = userService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword()).list();

       if(list.size()>0){
           User user1 = (User)list.get(0);
           List<Menu> menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
           HashMap res = new HashMap();
           res.put("user",user1);
           res.put("menu",menuList);
           return Result.succeed(res);


       }
       return Result.fail();
    }


    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        List<User> list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.succeed(list) : Result.fail();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        return userService.save(user) ? Result.succeed() : Result.fail();
    }

    //根据user.id修改
    @PostMapping("/mod")
    public Boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    //修改表单传输内容
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return userService.updateById(user) ? Result.succeed() : Result.fail();
    }

    //删除表中内容
    @GetMapping("/del")
    public Result del(@RequestParam Integer id) {
        return userService.removeById(id) ? Result.succeed() : Result.fail();
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public Boolean saveOrMod(@RequestBody User user) {

        return userService.saveOrUpdate(user);
    }

    //删除
    @GetMapping("/delete")
    public Boolean delete(Integer id) {
        return userService.removeById(id);
    }

    //查询(模糊、匹配)
    @PostMapping("listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //lambdaQueryWrapper.like(User::getName,user.getName());
        if (StringUtils.isNotBlank(user.getName())) {
            lambdaQueryWrapper.like(User::getName, user.getName());
        }
        return Result.succeed(userService.list(lambdaQueryWrapper));

    }

    @PostMapping("/listPage")
    //不封装参数，直接使用map发送
    //public List<User> listPage(@RequestBody HashMap map) {
    //System.out.println(map);
    //System.out.println("num=>" + (String) map.get("pageNum"));
    //System.out.println("size=>" + (String) map.get("pageSize"));
    //return null;
    //}
    public List<User> listPage(@RequestBody QueryPageParam query) {
        //System.out.println(query);
        //System.out.println("num=>" + query.getPageNum());
        //System.out.println("size=>" + query.getPageSize());

        HashMap param = query.getParam();
        //System.out.println("param.name=>" + (String) param.get("name"));
        String name = (String) param.get("name");
        //System.out.println("param.age=>" + (String) param.get("age"));

        Page page = new Page();
        //当前页数(从1开始)
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName, name);
        //lambdaQueryWrapper.eq(User::getName,user.getName());

        IPage<User> result = userService.page(page, lambdaQueryWrapper);
        System.out.println("total=" + result.getTotal());

        return result.getRecords();
    }

    /**
     * 自定义分页函数listPageCustom
     *
     * @param query
     * @return
     */
    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query) {
        System.out.println(query);
        //System.out.println("num=>" + query.getPageNum());
        //System.out.println("size=>" + query.getPageSize());

        HashMap param = query.getParam();
        //System.out.println("param.name=>" + (String) param.get("name"));
        String name = (String) param.get("name");
        //System.out.println("param.age=>" + (String) param.get("age"));

        Page page = new Page();
        //当前页数(从1开始)
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        //LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //lambdaQueryWrapper.like(User::getName, name);
        //lambdaQueryWrapper.eq(User::getName,user.getName());

        //服务层调用自定义pageC()方法
        IPage<User> result = userService.pageC(page);
        System.out.println("total=" + result.getTotal());

        return result.getRecords();
    }

    /**
     * 自定义分页函数和mapper.xml中的sql
     *
     * @param query
     * @return
     */
    @PostMapping("/listPageCM")
    public List<User> listPageCM(@RequestBody QueryPageParam query) {
        System.out.println(query);
        //System.out.println("num=>" + query.getPageNum());
        //System.out.println("size=>" + query.getPageSize());

        HashMap param = query.getParam();
        //System.out.println("param.name=>" + (String) param.get("name"));
        String name = (String) param.get("name");
        //System.out.println("param.age=>" + (String) param.get("age"));

        Page page = new Page();
        //当前页数(从1开始)
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName, name);
        //lambdaQueryWrapper.eq(User::getName,user.getName());

        //服务层调用自定义pageCM()方法
        IPage<User> result = userService.pageCM(page, lambdaQueryWrapper);
        System.out.println("total=" + result.getTotal());

        return result.getRecords();
    }

    /**
     * listPageCustomPrivate
     *
     * @param query
     * @return
     */
    @PostMapping("/listPageCP")
    public Result listPageCP(@RequestBody QueryPageParam query) {
        //System.out.println(query);
        //System.out.println("num=>" + query.getPageNum());
        //System.out.println("size=>" + query.getPageSize());

        HashMap param = query.getParam();
        //System.out.println("param.name=>" + (String) param.get("name"));
        String name = (String) param.get("name");
        String sex = (String) param.get("sex");
        String roleId = (String) param.get("roleId");
        //System.out.println("param.age=>" + (String) param.get("age"));

        Page page = new Page();
        //当前页数(从1开始)
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //通过name使用lambdaQueryWrapper进行模糊查询
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(User::getName, name);
            //lambdaQueryWrapper.eq(User::getName,user.getName());

        }
        //通过sex进行精确查询
        if (StringUtils.isNotBlank(sex)) {
            lambdaQueryWrapper.eq(User::getSex, sex);
            //lambdaQueryWrapper.eq(User::getName,user.getName());

        }

        //通过大于等于roleId进行筛选查询
        if (StringUtils.isNotBlank(roleId)) {
            lambdaQueryWrapper.ge(User::getRoleId, roleId);
            //lambdaQueryWrapper.eq(User::getName,user.getName());

        }

        //服务层调用自定义pageCM()方法
        IPage<User> result = userService.pageCM(page, lambdaQueryWrapper);
        System.out.println("total=" + result.getTotal());

        //可添加判定语句
        //if(result.getRecords()!=0)

        return Result.succeed(result.getTotal(), result.getRecords());
    }
}
