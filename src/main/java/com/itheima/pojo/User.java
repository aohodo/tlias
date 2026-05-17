package com.itheima.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user") //如果名字不一样的话，实体类的属性名和数据库字段名不一致，那么就需要指定
public class User {
    @TableId(value = "id", type = IdType.AUTO) //前端返回是没有主键的，所以需要指定。此外指定主键生成类型
    private Long id;

    @TableField("username")
    private String name; //表中UserName

    private Boolean isMarried;

    @TableField("`order`") //生成SQL的字段名的
    private Integer order; //关键字

    @TableField(exist = false)
    private String address; //不存在
}
