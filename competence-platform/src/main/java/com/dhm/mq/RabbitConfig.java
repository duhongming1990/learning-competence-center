package com.dhm.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/24 9:50
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queueAdd() {
        return new Queue("org.add");
    }
    @Bean
    public Queue queueUpdate() {
        return new Queue("org.update");
    }
    @Bean
    public Queue queueDelete() {
        return new Queue("org.delete");
    }

}