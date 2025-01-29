package de.unibayreuth.se.taskboard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "taskboard")
@ConfigurationPropertiesScan
public record TaskBoardConfiguration (String username, String password) { }
