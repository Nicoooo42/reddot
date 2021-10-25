package com.reddot.stats.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reddot.stats.service.StatisticsPostService;
import com.reddot.stats.service.dto.StatisticsPostDTO;

@Component
public class ActiveMqRoute extends RouteBuilder {

	@Autowired
	StatisticsPostService StatisticsPostServiceImpl;

	@Override
	public void configure() throws Exception {
		  from("activemq:stats-post")
		  .unmarshal().json(JsonLibrary.Jackson, StatisticsPostDTO.class)
		  .bean(StatisticsPostServiceImpl, "save")
		  .to("log:received-message-from-active-mq");
	}

}


