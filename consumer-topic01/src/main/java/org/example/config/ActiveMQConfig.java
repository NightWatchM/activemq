package org.example.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

@Configuration
public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.topic-name}")
    private String topic;

    @Bean("topic")
    public Topic Topic() {
        return new ActiveMQTopic(topic);
    }

    @Bean("redeliveryPolicy")
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setUseExponentialBackOff(true);
        redeliveryPolicy.setMaximumRedeliveries(4);
        redeliveryPolicy.setInitialRedeliveryDelay(1000L);
        redeliveryPolicy.setBackOffMultiplier(5);
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }

    /**
     * 定义连接工厂
     *
     * @return
     */
    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory(RedeliveryPolicy redeliveryPolicy) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(username, password, brokerUrl);
        factory.setRedeliveryPolicy(redeliveryPolicy);
        return factory;
    }

    @Bean("firstFactory")
    public SimpleJmsListenerContainerFactory firstFactory(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);
        factory.setClientId("consumer-topic-01");
//        factory.setSessionTransacted(false);//关闭事务
//        factory.setSessionAcknowledgeMode(4);//设置签收方式
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

}
