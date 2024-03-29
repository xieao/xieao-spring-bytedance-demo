package com.bytedance.mossey.demo.dal.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bytedance.mossey.demo.dal.db.entity.UserInfoDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserMapper
 *
 * @author xieao.mossey
 * @version 2024/03/29 17:41
 **/
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoDo> {
}
