package com.glowworm.football.booking.dao.util;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * @author xuyongchang
 * @date 2023/3/16
 */
public class YmlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {

        String sourceName = resource.getResource().getFilename();
        if (Objects.nonNull(sourceName) && (sourceName.endsWith(".yml") || sourceName.endsWith(".yaml"))) {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            Properties properties = factory.getObject();

            return new PropertiesPropertySource(name, properties);
        }
        return null;
    }
}
