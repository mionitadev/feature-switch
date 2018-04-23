package mionitadev.featureswitch;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(EnableFeatureSwitch.FeatureSwitchConfiguration.class)
public @interface EnableFeatureSwitch {

    @Configuration
    @ComponentScan
    @PropertySource(value = "${feature.properties.path}", ignoreResourceNotFound = true)
    class FeatureSwitchConfiguration {

    }
}