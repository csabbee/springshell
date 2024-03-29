package com.eggs.console;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.eggs.api.BaseMenuPrinter;
import com.eggs.api.MenuRepository;
import com.eggs.domain.Food;
import com.eggs.domain.Menu;

@Component
@Qualifier("console")
public class ConsoleMenuPrinter extends BaseMenuPrinter {
    
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ConsoleMenuPrinter.class);
    
    @Value("${menu.lang:en}")
    private Locale locale;

    @Autowired
    private MessageSource ctx;
    
    @Autowired
    public ConsoleMenuPrinter(MenuRepository... menuRepository) {
        super(menuRepository);
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
    public Locale getLocale() {
        return locale;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
