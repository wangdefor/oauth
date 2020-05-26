package com.cimen.ways.component.gateway.service.launcher.dal;

import com.cimen.ways.component.dal.domains.BaseMapper;
import com.cimen.ways.component.gateway.service.launcher.model.GatewayRouteConfig;
import com.cimen.ways.component.gateway.service.launcher.model.GatewayRouteConfigExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GatewayRouteConfigMapper extends BaseMapper {
    /**
     *
     * @param example
     */
    int countByExample(GatewayRouteConfigExample example);

    /**
     *
     * @param example
     */
    int deleteByExample(GatewayRouteConfigExample example);

    /**
     * 根据主键删除数据库的记录
     *
     * @param routeId
     */
    int deleteByPrimaryKey(Integer routeId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(GatewayRouteConfig record);

    /**
     *
     * @param record
     */
    int insertSelective(GatewayRouteConfig record);

    /**
     *
     * @param example
     */
    List<GatewayRouteConfig> selectByExample(GatewayRouteConfigExample example);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param routeId
     */
    GatewayRouteConfig selectByPrimaryKey(Integer routeId);

    /**
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") GatewayRouteConfig record, @Param("example") GatewayRouteConfigExample example);

    /**
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") GatewayRouteConfig record, @Param("example") GatewayRouteConfigExample example);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(GatewayRouteConfig record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(GatewayRouteConfig record);
}