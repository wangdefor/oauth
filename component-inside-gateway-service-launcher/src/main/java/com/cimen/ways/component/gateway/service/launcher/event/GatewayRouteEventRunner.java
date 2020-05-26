package com.cimen.ways.component.gateway.service.launcher.event;

import com.cimen.ways.component.gateway.service.launcher.App;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @ClassName GatewayRouteEventRunner
 * @Description 增加或者刷新路由相关事件类
 * @Date 2020/5/21 11:17
 * @Author wangyong
 * @Version 1.0
 **/
@Component
@Slf4j
public class GatewayRouteEventRunner implements CommandLineRunner, ApplicationEventPublisherAware {

    /**
     * 写入相关路由配置器
     */
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run(String... args) throws Exception {
        // 写入相关路由 -- routeDefinitionWriter.save(Mono.just(RouteDefinition)).subscribe();
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
