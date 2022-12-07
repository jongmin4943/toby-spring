package com.byultudy.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class FactoryBeanTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void getMessageFromFactoryBean() {
        Object message = applicationContext.getBean("message");
        assertThat(message, instanceOf(Message.class));
        assertThat(((Message)message).getText(), is("Factory Bean"));

        Object factory = applicationContext.getBean("&message");
        assertThat(factory, instanceOf(MessageFactoryBean.class));
    }
}
