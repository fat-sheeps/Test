package com.service.impl;

import com.domain.TbAdTencentStrategyGroupEntity;
import com.mapper.TbAdTencentStrategyGroupMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (TbAdTencentStrategyGroup)表服务实现类
 *
 * @author makejava
 * @since 2025-01-16 11:09:14
 */
@Service("tbAdTencentStrategyGroupService")
public class TbAdTencentStrategyGroupService {
    @Resource
    private TbAdTencentStrategyGroupMapper tbAdTencentStrategyGroupMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param strategyGroupId 主键
     * @return 实例对象
     */
    public TbAdTencentStrategyGroupEntity queryById(Long strategyGroupId) {
        return this.tbAdTencentStrategyGroupMapper.selectByPrimaryKey(strategyGroupId);
    }

    /**
     * 新增数据
     *
     * @param tbAdTencentStrategyGroupEntity 实例对象
     * @return 实例对象
     */
    public TbAdTencentStrategyGroupEntity insert(TbAdTencentStrategyGroupEntity tbAdTencentStrategyGroupEntity) {
        this.tbAdTencentStrategyGroupMapper.insert(tbAdTencentStrategyGroupEntity);
        return tbAdTencentStrategyGroupEntity;
    }

    /**
     * 修改数据
     *
     * @param tbAdTencentStrategyGroupEntity 实例对象
     * @return 实例对象
     */
    public TbAdTencentStrategyGroupEntity update(TbAdTencentStrategyGroupEntity tbAdTencentStrategyGroupEntity) {
        this.tbAdTencentStrategyGroupMapper.update(tbAdTencentStrategyGroupEntity);
        return this.queryById(tbAdTencentStrategyGroupEntity.getStrategyGroupId());
    }

    /**
     * 通过主键删除数据
     *
     * @param strategyGroupId 主键
     * @return 是否成功
     */
    public boolean deleteById(Long strategyGroupId) {
        return this.tbAdTencentStrategyGroupMapper.deleteById(strategyGroupId) > 0;
    }
}

