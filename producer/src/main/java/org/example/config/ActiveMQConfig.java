package org.example.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.queue-name}")
    private String queue;

    @Value("${spring.activemq.topic-name}")
    private String topic;

    @Bean("queue")
    public Queue Queue() {
        return new ActiveMQQueue(queue);
    }

    @Bean("topic")
    public Topic Topic() {
        return new ActiveMQTopic(topic);
    }

    /**
     * 定义连接工厂
     *
     * @return
     */
    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(username, password, brokerUrl);
        return factory;
    }

    @Bean
    public PlatformTransactionManager creatTransactionManager(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }

    @Bean("jmsTemplate")
    public JmsTemplate jmsTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        //非持久化消息需要配置explicitQosEnabled该属性为true，否则spring内部发送消息时将采用默认配置
        jmsTemplate.setExplicitQosEnabled(true);
        //设置消息发送的持久化方式
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean("jmsMessagingTemplate")
    JmsMessagingTemplate jmsMessagingTemplate(@Qualifier("jmsTemplate") JmsTemplate jmsTemplate) {
        JmsMessagingTemplate messagingTemplate = new JmsMessagingTemplate(jmsTemplate);
        return messagingTemplate;
    }

}
