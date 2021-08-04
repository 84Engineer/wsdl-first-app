package org.cxf.demos.wsdlfirst;

import com.bharath.ws.trainings.CreateOrdersRequest;
import com.bharath.ws.trainings.CreateOrdersResponse;
import com.bharath.ws.trainings.CustomerOrdersPortType;
import com.bharath.ws.trainings.GetOrdersRequest;
import com.bharath.ws.trainings.GetOrdersResponse;
import com.bharath.ws.trainings.Order;
import com.bharath.ws.trainings.Product;
import org.apache.cxf.feature.Features;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author olysenko
 */
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class CustomerOrderWsImpl implements CustomerOrdersPortType {

   public Map<BigInteger, List<Order>> customerOrders = new HashMap<>();
   private int currentId;

   public CustomerOrderWsImpl() {
      init();
   }

   private void init() {
      Order order = new Order();
      order.setId(BigInteger.valueOf(1));

      Product product = new Product();
      product.setId("1");
      product.setDescription("IPhone");
      product.setQuantity(BigInteger.valueOf(3));

      order.getProduct().add(product);

      List<Order> orders = new ArrayList<>();
      orders.add(order);
      customerOrders.put(BigInteger.valueOf(++currentId), orders);
   }

   @Override
   public GetOrdersResponse getOrders(GetOrdersRequest request) {
      BigInteger customerId = request.getCustomerId();
      List<Order> orders = customerOrders.get(customerId);

      GetOrdersResponse ordersResponse = new GetOrdersResponse();
      ordersResponse.getOrder().addAll(orders);
      return ordersResponse;
   }

   @Override
   public CreateOrdersResponse createOrders(CreateOrdersRequest request) {

      BigInteger customerId = request.getCustomerId();
      List<Order> orders = customerOrders.get(customerId);

      if (orders == null) {
         orders = new ArrayList<>();
         customerOrders.put(BigInteger.valueOf(++currentId), orders);
      }

      orders.add(request.getOrder());
      CreateOrdersResponse response = new CreateOrdersResponse();
      response.setResult(true);
      return response;
   }
}
