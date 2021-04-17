package com.connection.chat;

import com.connection.ChatColor;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextComponent extends BaseComponent {

    private String text;
    private static final Pattern url = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,4})(/\\S*)?$");

    public void setText(String text) {
        this.text = text;
    }

    @ConstructorProperties({"text"})
    public TextComponent(String text) {
        this.text = text;
    }

    public static BaseComponent[] fromLegacyText(String message) {
        return fromLegacyText(message, ChatColor.WHITE);
    }

    public static BaseComponent[] fromLegacyText(String message, ChatColor defaultColor) {
        ArrayList<BaseComponent> components = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        TextComponent component = new TextComponent();
        Matcher matcher = url.matcher(message);
        for(int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if(c == '§') {
                if(++i >= message.length())
                    break;
                c = message.charAt(i);
                if (c >= 'A' && c <= 'Z')
                    c = (char)(c + 32);
                ChatColor format = ChatColor.getByChar(c);
                if(format != null) {
                    if(builder.length() > 0) {
                        TextComponent old = component;
                        component = new TextComponent(old);
                        old.setText(builder.toString());
                        builder = new StringBuilder();
                        components.add(old);
                    }
                    switch (format) {
                        case BOLD:
                            component.setBold(true);
                            break;
                        case ITALIC:
                            component.setItalic(true);
                            break;
                        case UNDERLINE:
                            component.setUnderlined(true);
                            break;
                        case STRIKETHROUGH:
                            component.setStrikethrough(true);
                            break;
                        case MAGIC:
                            component.setObfuscated(true);
                            break;
                        case RESET:
                            format = defaultColor;
                        default:
                            component = new TextComponent();
                            component.setColor(format);
                            break;
                    }
                }
            } else {
                int pos = message.indexOf(' ', i);
                if(pos == -1)
                    pos = message.length();
                if(matcher.region(i, pos).find()) {
                    if(builder.length() > 0) {
                        TextComponent textComponent = component;
                        component = new TextComponent(component);
                        textComponent.setText(builder.toString());
                        builder = new StringBuilder();
                        components.add(textComponent);
                    }
                    TextComponent old = component;
                    component = new TextComponent(old);
                    String urlString = message.substring(i, pos);
                    component.setText(urlString);
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                            urlString.startsWith("http") ? urlString : ("http://" + urlString)));
                    components.add(component);
                    i += pos - i - 1;
                    component = old;
                } else {
                    builder.append(c);
                }
            }
        }
        component.setText(builder.toString());
        components.add(component);
        return components.toArray(new BaseComponent[components.size()]);
    }

    public String getText() {
        return this.text;
    }

    public TextComponent() {
        this.text = "";
    }

    public TextComponent(TextComponent textComponent) {
        super(textComponent);
        setText(textComponent.getText());
    }

    public TextComponent(BaseComponent... extras) {
        setText("");
        setExtra(new ArrayList<>(Arrays.asList(extras)));
    }

    public BaseComponent duplicate() {
        return new TextComponent(this);
    }

    protected void toPlainText(StringBuilder builder) {
        builder.append(this.text);
        super.toPlainText(builder);
    }

    protected void toLegacyText(StringBuilder builder) {
        builder.append(getColor());
        if (isBold())
            builder.append(ChatColor.BOLD);
        if (isItalic())
            builder.append(ChatColor.ITALIC);
        if (isUnderlined())
            builder.append(ChatColor.UNDERLINE);
        if (isStrikethrough())
            builder.append(ChatColor.STRIKETHROUGH);
        if (isObfuscated())
            builder.append(ChatColor.MAGIC);
        builder.append(this.text);
        super.toLegacyText(builder);
    }

    public String toString() {
        return String.format("TextComponent{text=%s, %s}", new Object[] { this.text, super.toString() });
    }

}
