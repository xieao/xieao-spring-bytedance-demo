package com.bytedance.mossey.demo.dal.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * UserDo
 *
 * @author xieao.mossey
 * @version 2024/03/29 17:40
 **/
@Data
@TableName("`user`")
public class UserInfoDo extends BaseEntity {
    private String name;
    private Integer age;
    private String email;
}