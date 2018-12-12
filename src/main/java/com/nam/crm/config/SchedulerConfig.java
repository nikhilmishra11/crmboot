/**
 * 
 */
package com.nam.crm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author Nikhil 14-Oct-2018
 */
@Configuration
@EnableScheduling
@PropertySource(value= {"classpath:scheduler.properties"})
public class SchedulerConfig {

}
