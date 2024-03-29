package com.eggs.repo.yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.eggs.api.MenuRepository;
import com.eggs.domain.Food;
import com.eggs.domain.Menu;
import com.eggs.domain.MenuEvent;

@Component
@Qualifier("yaml")
public class YamlFileMenuRepository implements MenuRepository {

    private String yamlFileName;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private List<Menu> menus = new ArrayList<Menu>();
    @Autowired
    private ApplicationEventPublisher publisher;
    
    public YamlFileMenuRepository() {
        this("menus.yml");
    }
    
    public YamlFileMenuRepository(String yamlFileName) {
        this.yamlFileName = yamlFileName;
    }

    @PostConstruct
    public void read() {
        logger.debug("read() started ...");
        Constructor constructor = new Constructor(Menu.class);
        TypeDescription menuDescription = new TypeDescription(Menu.class);
        menuDescription.putListPropertyType("foods", Food.class);
        constructor.addTypeDescription(menuDescription);
        Yaml yaml = new Yaml(constructor);

        logger.info("reading YAML: " + yamlFileName);
        InputStream stream = getClass().getClassLoader().getResourceAsStream(yamlFileName);

        Iterable<Object> iter = yaml.loadAll(stream);
        for(Object o : iter){
           addMenu((Menu)o);
        }
    }

    public List<Menu> getAllmenu() {
        return menus;
    }

    public void addMenu(Menu menu) {
        menus.add(menu);
        publishEvent(menu);
    }
    private void publishEvent(Menu menu) {
        if (publisher!= null) {
          publisher.publishEvent(new MenuEvent(menu));
        } else {
            logger.warn("Menu is new-sed ...");
        }
    }
}
