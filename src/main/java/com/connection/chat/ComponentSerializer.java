package com.connection.chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import java.util.HashSet;

public class ComponentSerializer implements JsonDeserializer<BaseComponent> {
    private static final JsonParser JSON_PARSER = new JsonParser();

    private static final Gson gson = (new GsonBuilder())
            .registerTypeAdapter(BaseComponent.class, new ComponentSerializer())
            .registerTypeAdapter(TextComponent.class, new TextComponentSerializer())
            .registerTypeAdapter(TranslatableComponent.class, new TranslatableComponentSerializer())
            .registerTypeAdapter(KeybindComponent.class, new KeybindComponentSerializer())
            .registerTypeAdapter(ScoreComponent.class, new ScoreComponentSerializer())
            .registerTypeAdapter(SelectorComponent.class, new SelectorComponentSerializer())
            .create();

    public static final ThreadLocal<HashSet<BaseComponent>> serializedComponents = new ThreadLocal<HashSet<BaseComponent>>();

    public static BaseComponent[] parse(String json) {
        JsonElement jsonElement = JSON_PARSER.parse(json);
        if (jsonElement.isJsonArray())
            return (BaseComponent[])gson.fromJson(jsonElement, BaseComponent[].class);
        return new BaseComponent[] { (BaseComponent)gson

                .fromJson(jsonElement, BaseComponent.class) };
    }

    public static String toString(BaseComponent component) {
        return gson.toJson(component);
    }

    public static String toString(BaseComponent... components) {
        if (components.length == 1)
            return gson.toJson(components[0]);
        return gson.toJson(new TextComponent(components));
    }

    public BaseComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive())
            return (BaseComponent)new TextComponent(json.getAsString());
        JsonObject object = json.getAsJsonObject();
        if (object.has("translate"))
            return (BaseComponent)context.deserialize(json, TranslatableComponent.class);
        if (object.has("keybind"))
            return (BaseComponent)context.deserialize(json, KeybindComponent.class);
        if (object.has("score"))
            return (BaseComponent)context.deserialize(json, ScoreComponent.class);
        if (object.has("selector"))
            return (BaseComponent)context.deserialize(json, SelectorComponent.class);
        return (BaseComponent)context.deserialize(json, TextComponent.class);
    }
}
