package org.example.controller;

import org.example.config.ActiveMQQueueProducer;
import org.example.config.ActiveMQTopicProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.Queue;
import javax.jms.Topic;

@Controller
@RequestMapping("/api")
public class ActiveMQProducerController {

    @Autowired
    private ActiveMQQueueProducer activeMQQueueProducer;

    @Autowired
    private ActiveMQTopicProducer activeMQTopicProducer;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @RequestMapping("/queue/test")
    @ResponseBody
    public void queueSendMsg() {
        activeMQQueueProducer.sendMsg("111111111111", queue);
    }

    @RequestMapping("/topic/test1")
    @ResponseBody
    public void topicSendMsg1() {
        activeMQTopicProducer.sendMsg("111111111111", topic);
    }

    @RequestMapping("/topic/test2")
    @ResponseBody
    public void topicSendMsg2() {
        activeMQTopicProducer.sendMsg("222222222222", topic);
    }

    @RequestMapping("/topic/test3")
    @ResponseBody
    public void topicSendMsg3() {
        activeMQTopicProducer.sendMsg("333333333333", topic);
    }

    @RequestMapping("/topic/test4")
    @ResponseBody
    public void topicSendMsg4() {
        activeMQTopicProducer.sendMsg("444444444444", topic);
    }

}
