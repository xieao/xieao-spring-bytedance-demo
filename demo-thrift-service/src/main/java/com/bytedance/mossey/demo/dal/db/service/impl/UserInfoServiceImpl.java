package com.bytedance.mossey.demo.dal.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bytedance.mossey.demo.dal.db.entity.UserInfoDo;
import com.bytedance.mossey.demo.dal.db.mapper.UserInfoMapper;
import com.bytedance.mossey.demo.dal.db.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * UserInfoServiceImpl
 *
 * @author xieao.mossey
 * @version 2024/03/29 18:05
 **/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoDo> implements UserInfoService {
}
