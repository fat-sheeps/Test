package com.mapper;

import com.domain.TbAdTencentStrategyGroupEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (tb_ad_tencent_strategy_group)表数据库访问层
 *
 * @author makejava
 * @since 2025-01-16 11:09:11
 */
public interface TbAdTencentStrategyGroupMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param strategyGroupId 主键
     * @return 实例对象
     */
    TbAdTencentStrategyGroupEntity selectByPrimaryKey(Long strategyGroupId);

    /**
     * 统计总行数
     *
     * @param tbAdTencentStrategyGroupEntity 查询条件
     * @return 总行数
     */
    long count(TbAdTencentStrategyGroupEntity tbAdTencentStrategyGroupEntity);

    /**
     * 新增数据
     *
     * @param tbAdTencentStrategyGroupEntity 实例对象
     * @return 影响行数
     */
    int insert(TbAdTencentStrategyGroupEntity tbAdTencentStrategyGroupEntity);


    /**
     * 新增数据
     *
     * @param tbAdTencentStrategyGroupEntity 实例对象
     * @return 影响行数
     */
    int insertSelective(TbAdTencentStrategyGroupEntity tbAdTencentStrategyGroupEntity);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbAdTencentStrategyGroupEntity> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbAdTencentStrategyGroupEntity> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbAdTencentStrategyGroupEntity> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TbAdTencentStrategyGroupEntity> entities);

    /**
     * 修改数据
     *
     * @param tbAdTencentStrategyGroupEntity 实例对象
     * @return 影响行数
     */
    int update(TbAdTencentStrategyGroupEntity tbAdTencentStrategyGroupEntity);

    /**
     * 通过主键删除数据
     *
     * @param strategyGroupId 主键
     * @return 影响行数
     */
    int deleteById(Long strategyGroupId);

}

