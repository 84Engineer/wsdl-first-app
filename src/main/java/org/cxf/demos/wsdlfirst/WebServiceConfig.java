package org.cxf.demos.wsdlfirst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;

import javax.xml.ws.Endpoint;

/**
 * @author olysenko
 */
@Configuration
public class WebServiceConfig {

   @Autowired
   private Bus bus;

   @Bean
   public Endpoint endpoint() {
      Endpoint endpoint = new EndpointImpl(bus, new CustomerOrderWsImpl());
      endpoint.publish("/customerOrdersService");
      return endpoint;
   }

}