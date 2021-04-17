package com.connection.chat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class SelectorComponentSerializer extends BaseComponentSerializer implements JsonSerializer<SelectorComponent>, JsonDeserializer<SelectorComponent> {
    public SelectorComponent deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = element.getAsJsonObject();
        SelectorComponent component = new SelectorComponent(object.get("selector").getAsString());
        deserialize(object, (BaseComponent)component, context);
        return component;
    }

    public JsonElement serialize(SelectorComponent component, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        serialize(object, (BaseComponent)component, context);
        object.addProperty("selector", component.getSelector());
        return (JsonElement)object;
    }
}
