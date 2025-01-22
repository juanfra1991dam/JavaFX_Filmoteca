package org.example.javafx_filmoteca;

import com.google.gson.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.lang.reflect.Type;
import java.util.List;

public class TypeAdapters {

    // Adaptador para IntegerProperty
    public static class IntegerPropertyAdapter implements JsonSerializer<IntegerProperty>, JsonDeserializer<IntegerProperty> {
        @Override
        public JsonElement serialize(IntegerProperty src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.get());
        }

        @Override
        public IntegerProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new SimpleIntegerProperty(json.getAsInt());
        }
    }

    // Adaptador para StringProperty
    public static class StringPropertyAdapter implements JsonSerializer<StringProperty>, JsonDeserializer<StringProperty> {
        @Override
        public JsonElement serialize(StringProperty src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.get());
        }

        @Override
        public StringProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new SimpleStringProperty(json.getAsString());
        }
    }

    // Adaptador para DoubleProperty
    public static class DoublePropertyAdapter implements JsonSerializer<DoubleProperty>, JsonDeserializer<DoubleProperty> {
        @Override
        public JsonElement serialize(DoubleProperty src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.get());
        }

        @Override
        public DoubleProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new SimpleDoubleProperty(json.getAsDouble());
        }
    }

    // Adaptador para ListProperty
    public static class ListPropertyAdapter implements JsonSerializer<ListProperty<String>>, JsonDeserializer<ListProperty<String>> {
        @Override
        public JsonElement serialize(ListProperty<String> src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.get());
        }

        @Override
        public ListProperty<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            List<String> list = context.deserialize(json, List.class);
            return new SimpleListProperty<>(FXCollections.observableArrayList(list));
        }
    }
}
