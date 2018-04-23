package mionitadev.featureswitch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@TestPropertySource(value = "classpath:feature.properties")
public class FeatureServiceTest {

    @Configuration
    static class ContextConfiguration {
        @Bean
        public FeatureService featureService() {
            return new FeatureServiceImpl();
        }
    }

    @Autowired
    private FeatureService featureService;

    @Test
    public void testFeatureSwitchIsActive(){
        assertTrue(featureService.isActive(Feature.EDITABLE.getValue(), TestFeatureContext.of("user_123")));
    }

    @Test
    public void testFeatureSwitchIfActive(){
        List<String> features = new ArrayList<>();
        featureService.ifActive(Feature.EDITABLE.getValue(), TestFeatureContext.of("user_123"), () -> features.add("feature_editable"));
        assertNotEquals(0, features.size());
        assertEquals("feature_editable", features.get(0));
    }
}
