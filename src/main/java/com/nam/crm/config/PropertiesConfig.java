/**
 * 
 */
package com.nam.crm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Nikhil 13-Jun-2018
 */
@Configuration
@PropertySource(value= {"classpath:config/user.properties","classpath:config/constants.properties"})
public class PropertiesConfig {

}
