# feature-switch
library used for switching application features on/off

The feature switch integration is done by adding the featureswitch dependency and the necessary configuration to the service.

Steps to follow:

 1. Add the featureswitch dependency into the build.gradle file

compile project(':featureswitch')
 2. Create a Feature enum with the necessary features for your service

public enum Feature {
    EDITABLE("editable"); //feature to be used into the service
}
 3. Implement the FeatureContext interface. Add the necessary context fields for your service

class CustomFeatureContext implements FeatureContext {
    private String userId; //context fields used to restrict the feature. There can be as many fields as needed.
}
 4. Create a feature.properties file with the features configurations

feature.editable.active=true // the active property defines if a feature is active
feature.editable.constraints.userId=user_123, user_456 //the restrictions is an array of values that constrain the feature

//editable - name of the feature added into the Feature enum
//userId - restriction that needs to match the context field name
 5. Define the path to the feature.properties file into the application.yml

feature:
  properties:
    path: classpath:/feature.properties //the path to the feature.properties file.


//classpath: - if the file is in the resources folder
//file: - if the file is outside the jar
 6. Activate the feature toggle by adding the @EnableFeatureSwitch annotation the your configuration class

@Configuration
@EnableFeatureSwitch //the annotation has to be added to a Spring configuration class
public class FeatureConfiguration {
}
In order to use this feature toggle, the FeatureService component needs to be injected into the services.

FeatureService component contains two methods that can be used to activate/deactivate features into the microservice.

boolean isActive(String feature, FeatureContext featureContext);
void ifActive(String feature, FeatureContext featureContext, Runnable runnable);
Usage examples:

if (featureService.isActive(Feature.EDITABLE.getValue(), CustomFeatureContext.of("user_123"))){
    //code
}

featureService.ifActive(Feature.EDITABLE.getValue(), CustomFeatureContext.of("user_123"),
        () -> {
        //code
    });


//for this example editable active is true and restrictions contains dealer2