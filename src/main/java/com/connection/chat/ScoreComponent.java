package com.connection.chat;

import java.beans.ConstructorProperties;

public final class ScoreComponent extends BaseComponent {
    private String name;

    private String objective;

    public void setName(String name) {
        this.name = name;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return "ScoreComponent(name=" + getName() + ", objective=" + getObjective() + ", value=" + getValue() + ")";
    }

    @ConstructorProperties({"name", "objective", "value"})
    public ScoreComponent(String name, String objective, String value) {
        this.name = name;
        this.objective = objective;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getObjective() {
        return this.objective;
    }

    private String value = "";

    public String getValue() {
        return this.value;
    }

    public ScoreComponent(String name, String objective) {
        setName(name);
        setObjective(objective);
    }

    public ScoreComponent(ScoreComponent original) {
        super(original);
        setName(original.getName());
        setObjective(original.getObjective());
        setValue(original.getValue());
    }

    public ScoreComponent duplicate() {
        return new ScoreComponent(this);
    }

    protected void toLegacyText(StringBuilder builder) {
        builder.append(this.value);
        super.toLegacyText(builder);
    }
}
