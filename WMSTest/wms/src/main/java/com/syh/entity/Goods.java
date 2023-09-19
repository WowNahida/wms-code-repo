package com.syh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author syh
 * @since 2023-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 货物名
     */
    private String name;

    /**
     * 仓库
     */
    private Integer storage;

    /**
     * 货物分类
     */
    private Integer goodstype;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 备注
     */
    private String remark;


}
