package com.cimen.ways.component.gateway.service.launcher.repository;

import com.cimen.ways.component.gateway.service.launcher.dal.GatewayRouteConfigMapper;
import com.cimen.ways.component.gateway.service.launcher.model.GatewayRouteConfig;
import com.cimen.ways.component.gateway.service.launcher.model.GatewayRouteConfigExample;
import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 加载数据库配置类 该类会每隔一段时间定时获取 调用 getRouteDefinitions 来刷新路由
 */
@Component
@NoArgsConstructor
public class MysqlRouteDefinitionRepository implements RouteDefinitionRepository {

    @Autowired
    private GatewayRouteConfigMapper gatewayRouteConfigMapper;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<GatewayRouteConfig> gatewayRouteConfigs = gatewayRouteConfigMapper.selectByExample(new GatewayRouteConfigExample());
        List<RouteDefinition> routeDefinitions = gatewayRouteConfigs.stream()
                .map(this::builderRouteDefinition)
                .collect(Collectors.toList());
        return Flux.fromIterable(routeDefinitions);
    }


    private RouteDefinition builderRouteDefinition(GatewayRouteConfig gatewayRouteConfig) {
        RouteDefinition definition = new RouteDefinition();
        definition.setOrder(gatewayRouteConfig.getSort());
        definition.setId(gatewayRouteConfig.getRouteId().toString());

        PredicateDefinition pathPredicate = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);
        pathPredicate.setName("Path");
        predicateParams.put("pattern", gatewayRouteConfig.getPath());
        pathPredicate.setArgs(predicateParams);
        definition.setPredicates(Lists.newArrayList(pathPredicate));
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName("StripPrefix");
        filterDefinition.addArg("parts", "1");

        filterDefinition.setName("RewritePath");
        filterDefinition.addArg("regexp", "/dd/(?<segment>.*)");
        filterDefinition.addArg("replacement", "/$\\{segment}");

        definition.setFilters(Lists.newArrayList(filterDefinition));
        //如果lb 不为空优先走lb
        if(!StringUtils.isBlank(gatewayRouteConfig.getLb()))
        {
            definition.setUri(URI.create(gatewayRouteConfig.getLb()));
        }else {
            //请替换成本地可访问的域名
            URI uri = URI.create(gatewayRouteConfig.getUrl());
            definition.setUri(uri);
        }
        return definition;
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        route.map(r -> {
            String id = r.getId();
            return r;
        });
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        // todo 修改数据库
        return null;
    }
}
