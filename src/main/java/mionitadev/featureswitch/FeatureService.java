package mionitadev.featureswitch;

public interface FeatureService {
    boolean isActive(String feature, FeatureContext featureContext);
    void ifActive(String feature, FeatureContext featureContext, Runnable runnable);
}
