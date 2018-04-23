package mionitadev.featureswitch;

import lombok.*;

@Value
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class TestFeatureContext implements FeatureContext{
    private String userId;
}
