package com.kam.config;

import com.kam.EnableKam;
import com.kam.MyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableKam
@ComponentScan("com.kam")
public class Config {
}
