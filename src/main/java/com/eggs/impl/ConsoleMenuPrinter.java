package com.eggs.impl;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.eggs.BaseMenuPrinter;
import com.eggs.Food;
import com.eggs.Menu;
import com.eggs.MenuRepositoryReader;

@Component
public class ConsoleMenuPrinter extends BaseMenuPrinter implements ApplicationContextAware {

    
    private ApplicationContext ctx;
    
    private static final Logger logger = LoggerFactory.getLogger(ConsoleMenuPrinter.class);
    
    @Value("#{T(com.eggs.utils.ConfigurationSupport).getConfig('menu.lang','en')}")
    private Locale locale;
    
    @Autowired
    public ConsoleMenuPrinter(@Qualifier("memory") MenuRepositoryReader reader) {
        this.reader = reader;
        logger.debug("#### autowired reader: {}", reader);
    }

    @Override
    protected void printSingleMenu(Menu menu) {
        String name = menu.getRestaurant().getName();
        String fmt = "%-"+ (name.length() + 2 ) + "s";
        String padded = " " + String.format(fmt, "-").replace(" ", "-");
        System.out.println(padded);
        System.out.format("< %s >%n", name);       
        System.out.println(padded);
        System.out.println("        \\   ^__^");
        System.out.println("         \\  (oo)\\_______");
        System.out.println("            (__)\\       )\\/\\");
        System.out.println("                ||----w |");
        System.out.println("                ||     ||");
        
        System.out.format(String.format(" %-20s---%10s %n", "-", "-").replace(" ", "-"));
        
        
        String nameHeader = ctx.getMessage("header.name", new Object[0], this.locale);
        String priceHeader = ctx.getMessage("header.price", new Object[0],  this.locale);
        
        System.out.format(" %-20s | %10s %n", nameHeader, priceHeader);
        
        for (Food food : menu.getFoodList()) {
            System.out.format(" %-20s | %10.2f %n", food.getName(), food.getPrice());
        }
    }
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
        
    }
    public Locale getLocale() {
        return locale;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
