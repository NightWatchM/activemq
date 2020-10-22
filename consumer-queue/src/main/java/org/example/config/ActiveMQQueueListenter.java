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
            session.rollback();
        }
    }

}
