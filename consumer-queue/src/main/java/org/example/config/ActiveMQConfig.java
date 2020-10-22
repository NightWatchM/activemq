package org.example.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.*;

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

    @Bean("queue")
    public Queue Queue() {
        return new ActiveMQQueue(queue);
    }

    @Bean("redeliveryPolicy")
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //启用指数倍数递增的方式增加延迟时间。
        redeliveryPolicy.setUseExponentialBackOff(true);
        //最大重传次数
        redeliveryPolicy.setMaximumRedeliveries(5);
        //初始重发延迟时间
        redeliveryPolicy.setInitialRedeliveryDelay(1000L);
        redeliveryPolicy.setRedeliveryDelay(10000L);
        //重连时间间隔递增倍数，只有值大于1和启用useExponentialBackOff参数时才生效。
        redeliveryPolicy.setBackOffMultiplier(1);
        //启用防止冲突功能
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

    @Bean
    public PlatformTransactionManager creatTransactionManager(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }

    @Bean("firstFactory")
    public SimpleJmsListenerContainerFactory firstFactory(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setSessionTransacted(true);//关闭事务
        factory.setSessionAcknowledgeMode(1);//设置签收方式
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

}
