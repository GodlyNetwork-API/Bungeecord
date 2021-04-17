package com.connection.chat;

import com.connection.ChatColor;

public class KeybindComponent extends BaseComponent {

    private String keybind;

    public void setKeybind(String keybind) {
        this.keybind = keybind;
    }

    public String toString() {
        return "KeybindComponent(keybind=" + getKeybind() + ")";
    }

    public KeybindComponent() {}

    public String getKeybind() {
        return this.keybind;
    }

    public KeybindComponent(KeybindComponent original) {
        super(original);
        setKeybind(original.getKeybind());
    }

    public KeybindComponent(String keybind) {
        setKeybind(keybind);
    }

    public BaseComponent duplicate() {
        return new KeybindComponent(this);
    }

    protected void toPlainText(StringBuilder builder) {
        builder.append(getKeybind());
        super.toPlainText(builder);
    }

    protected void toLegacyText(StringBuilder builder) {
        builder.append(getColor());
        if(isBold())
            builder.append(ChatColor.BOLD);
        if(isItalic())
            builder.append(ChatColor.ITALIC);
        if(isUnderlined())
            builder.append(ChatColor.UNDERLINE);
        if(isStrikethrough())
            builder.append(ChatColor.STRIKETHROUGH);
        if(isObfuscated())
            builder.append(ChatColor.MAGIC);
        builder.append(getKeybind());
        super.toLegacyText(builder);
    }

}
