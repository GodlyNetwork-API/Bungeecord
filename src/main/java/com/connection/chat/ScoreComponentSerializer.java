package com.connection.chat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class ScoreComponentSerializer extends BaseComponentSerializer implements JsonSerializer<ScoreComponent>, JsonDeserializer<ScoreComponent> {
    public ScoreComponent deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = element.getAsJsonObject();
        if (!json.has("name") || !json.has("objective"))
            throw new JsonParseException("A score component needs at least a name and an objective");
        String name = json.get("name").getAsString();
        String objective = json.get("objective").getAsString();
        ScoreComponent component = new ScoreComponent(name, objective);
        if (json.has("value") && !json.get("value").getAsString().isEmpty())
            component.setValue(json.get("value").getAsString());
        deserialize(json, (BaseComponent)component, context);
        return component;
    }

    public JsonElement serialize(ScoreComponent component, Type type, JsonSerializationContext context) {
        JsonObject root = new JsonObject();
        serialize(root, (BaseComponent)component, context);
        JsonObject json = new JsonObject();
        json.addProperty("name", component.getName());
        json.addProperty("objective", component.getObjective());
        json.addProperty("value", component.getValue());
        root.add("score", (JsonElement)json);
        return (JsonElement)root;
    }
}

