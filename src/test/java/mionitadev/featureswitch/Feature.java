package mionitadev.featureswitch;

public enum Feature {
    EDITABLE("editable");

    private String value;

    Feature(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
