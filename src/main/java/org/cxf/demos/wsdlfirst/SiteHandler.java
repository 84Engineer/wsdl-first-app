package org.cxf.demos.wsdlfirst;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Iterator;
import java.util.Set;

/**
 * @author olysenko
 */
public class SiteHandler implements SOAPHandler<SOAPMessageContext> {
   @Override
   public Set<QName> getHeaders() {
      System.out.println("SiteHandler.getHeaders is called");
      return null;
   }

   @Override
   public boolean handleMessage(SOAPMessageContext context) {
      System.out.println("SiteHandler.handleMessage is called");
      Boolean isResponse = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
      if (!isResponse) {
         SOAPMessage message = context.getMessage();
         try {
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            SOAPHeader header = envelope.getHeader();
            Iterator childElements = header.getChildElements();
            while (childElements.hasNext()) {
               Node node = (Node) childElements.next();
               String name = node.getLocalName();
               if (name != null && name.equals("SiteName")) {
                  System.out.println("Site name is: " + node.getValue());
               }
            }
         } catch (SOAPException e) {
            e.printStackTrace();
         }
      } else {
         System.out.println("This is response");
      }
      return true;
   }

   @Override
   public boolean handleFault(SOAPMessageContext context) {
      System.out.println("SiteHandler.handleFault is called");
      return false;
   }

   @Override
   public void close(MessageContext context) {
      System.out.println("SiteHandler.context is called");
   }
}
