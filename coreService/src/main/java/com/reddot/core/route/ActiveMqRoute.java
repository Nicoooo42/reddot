package com.reddot.core.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		  from("direct:firstRoute")
		   .to("activemq:stats-post");
	}

}
