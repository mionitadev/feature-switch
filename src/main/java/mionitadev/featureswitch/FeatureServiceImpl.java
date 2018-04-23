package mionitadev.featureswitch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService{

    private final String FEATURE_CODE_ACTIVE = "feature.%s.active";
    private final String FEATURE_CODE_CONSTRAINTS = "feature.%s.constraints.%s";

    @Autowired
    private Environment environment;

    @Override
    public boolean isActive(String feature, FeatureContext featureContext) {
        return isFeatureActive(feature) && isContextAllowed(feature, featureContext);
    }

    @Override
    public void ifActive(String feature, FeatureContext featureContext, Runnable runnable) {
        if (isActive(feature, featureContext))
            runnable.run();
    }

    private boolean isFeatureActive(String feature){
        String code = String.format(FEATURE_CODE_ACTIVE, feature);
        String property = environment.getProperty(code);
        return Boolean.valueOf(property);
    }

    private boolean isContextAllowed(String feature, FeatureContext featureContext){
        Field[] fields = featureContext.getClass().getDeclaredFields();
        for (Field field : fields) {
            String code = String.format(FEATURE_CODE_CONSTRAINTS, feature, field.getName());
            try {
                field.setAccessible(true);
                List<String> properties = environment.getProperty(code, List.class);
                if (properties.size() == 0 || properties.contains(field.get(featureContext)))
                    return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
