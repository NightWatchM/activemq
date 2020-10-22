package org.example.config;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ActiveMQQueueListenter {

    private static int a = -1;

    @JmsListener(destination = "${spring.activemq.queue-name}", containerFactory = "firstFactory")
    public void readQueue(final TextMessage messsage, Session session) throws JMSException {
        try {
            a = a + 1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            System.out.println("queue接受的消息为:" + messsage.getText() + "====" + sdf.format(now) + "  " + a + "次");
            int b = 10 / 0;
        } catch (Exception e) {
            //开启事务时，调用rollback()，触发重试机制
            session.rollback();
        }

/*        try {
            a = a + 1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            System.out.println("queue接受的消息为:" + messsage.getText() + "====" + sdf.format(now) + "  " + a + "次");
            int b = 10 / 0;
            //开启签收机制时，消息需要签收
            messsage.acknowledge();
        } catch (Exception e) {
            //开启签收机制时，调用recover()，触发重试机制
            session.recover();
        }*/

    }

}
