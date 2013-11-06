package com.eggs.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.eggs.Menu;
import com.eggs.MenuBuilder;
import com.eggs.MenuRepository;
import com.eggs.MenuRepositoryReader;

@Component
public class InmemoryMenuRepositoryReader implements MenuRepositoryReader {

    private List<Menu> menus = new ArrayList<Menu>();

    @Autowired
    private ApplicationContext ctx;
    
    public InmemoryMenuRepositoryReader() {
    }
    
    @PostConstruct
    public void init() {
        createFirstMenu();
        createSecondMenu();
    }

    private void createFirstMenu() {
        MenuBuilder builder = ctx.getBean(MenuBuilder.class);
        menus.add(builder.restaurant("Karesz").food("k1", "hagymas rantotta", 450).food("k2", "ham and eggs", 540)
                .food("k3", "kroasszon", 320).build());

    }

    private void createSecondMenu() {
        MenuBuilder builder = ctx.getBean(MenuBuilder.class);
        menus.add(builder.restaurant("Mercello").food("m1", "Margherita", 250).food("m2", "grilled cat", 890).food("m3", "ostriga", 1490)
                .build());

    }

    public List<Menu> getAllmenu() {
        return menus;
    }

    public MenuRepository read() {
        return new MenuRepository(menus);
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

}
