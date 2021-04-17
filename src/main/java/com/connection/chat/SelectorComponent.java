package com.connection.chat;

import java.beans.ConstructorProperties;

public final class SelectorComponent extends BaseComponent {
    private String selector;

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String toString() {
        return "SelectorComponent(selector=" + getSelector() + ")";
    }

    @ConstructorProperties({"selector"})
    public SelectorComponent(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return this.selector;
    }

    public SelectorComponent(SelectorComponent original) {
        super(original);
        setSelector(original.getSelector());
    }

    public SelectorComponent duplicate() {
        return new SelectorComponent(this);
    }

    protected void toLegacyText(StringBuilder builder) {
        builder.append(this.selector);
        super.toLegacyText(builder);
    }
}

