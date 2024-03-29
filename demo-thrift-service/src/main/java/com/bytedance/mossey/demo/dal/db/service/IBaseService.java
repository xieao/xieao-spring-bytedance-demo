package com.bytedance.mossey.demo.dal.db.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bytedance.mossey.demo.dal.db.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * UserService
 *
 * @author xieao.mossey
 * @version 2024/03/29 17:43
 **/
public interface IBaseService<K extends BaseEntity> extends IService<K> {
    @Override
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatch(Collection<K> entityList, int batchSize) {
        boolean success = true;
        for (K entity : entityList) {
            success = success && save(entity);
        }

        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdateBatch(Collection<K> entityList, int batchSize) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    default boolean updateBatchById(Collection<K> entityList, int batchSize) {
        boolean success = true;
        for (K entity : entityList) {
            success = success && saveOrUpdate(entity);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdate(K entity) {
        if (entity.getId() == null) {
            return save(entity);
        }

        return updateById(entity);
    }

    @Override
    default K getOne(Wrapper<K> queryWrapper, boolean throwEx) {
        List<K> list = list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            if (throwEx) {
                throw new IllegalStateException("No record");
            } else {
                return null;
            }
        }
        if (list.size() > 1) {
            if (throwEx) {
                throw new IllegalStateException("Duplicated records");
            }
        }
        return list.get(0);
    }

    @Override
    default Map<String, Object> getMap(Wrapper<K> queryWrapper) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    default <V> V getObj(Wrapper<K> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    default BaseMapper<K> getBaseMapper() {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    default Class<K> getEntityClass() {
        return null;
    }
}
