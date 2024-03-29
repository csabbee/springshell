package com.eggs.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackageClasses={com.eggs.repo.csv.CsvFileMenuRepository.class, com.eggs.console.ConsoleMenuPrinter.class})
@Profile("csv")
public class CsvConfiguration {

}
