package org.example.javafx_filmoteca;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.util.List;

/**
 * Adaptadores personalizados para serializar y deserializar propiedades de JavaFX con Jackson.
 */
public class TypeAdapters {

    // 🔹 Adaptador para IntegerProperty
    public static class IntegerPropertySerializer extends StdSerializer<IntegerProperty> {
        public IntegerPropertySerializer() {
            super(IntegerProperty.class);
        }

        @Override
        public void serialize(IntegerProperty value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeNumber(value.get());
        }
    }

    public static class IntegerPropertyDeserializer extends StdDeserializer<IntegerProperty> {
        public IntegerPropertyDeserializer() {
            super(IntegerProperty.class);
        }

        @Override
        public IntegerProperty deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return new SimpleIntegerProperty(p.getIntValue());
        }
    }

    // 🔹 Adaptador para StringProperty
    public static class StringPropertySerializer extends StdSerializer<StringProperty> {
        public StringPropertySerializer() {
            super(StringProperty.class);
        }

        @Override
        public void serialize(StringProperty value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(value.get());
        }
    }

    public static class StringPropertyDeserializer extends StdDeserializer<StringProperty> {
        public StringPropertyDeserializer() {
            super(StringProperty.class);
        }

        @Override
        public StringProperty deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return new SimpleStringProperty(p.getText());
        }
    }

    // 🔹 Adaptador para DoubleProperty
    public static class DoublePropertySerializer extends StdSerializer<DoubleProperty> {
        public DoublePropertySerializer() {
            super(DoubleProperty.class);
        }

        @Override
        public void serialize(DoubleProperty value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeNumber(value.get());
        }
    }

    public static class DoublePropertyDeserializer extends StdDeserializer<DoubleProperty> {
        public DoublePropertyDeserializer() {
            super(DoubleProperty.class);
        }

        @Override
        public DoubleProperty deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return new SimpleDoubleProperty(p.getDoubleValue());
        }
    }

    // 🔹 Adaptador para ListProperty<String>
    public static class ListPropertySerializer extends StdSerializer<ListProperty<String>> {
        public ListPropertySerializer() {
            super((Class<ListProperty<String>>) (Class<?>) ListProperty.class);
        }

        @Override
        public void serialize(ListProperty<String> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeObject(value.get());
        }
    }

    public static class ListPropertyDeserializer extends StdDeserializer<ListProperty<String>> {
        public ListPropertyDeserializer() {
            super((Class<?>) ListProperty.class);
        }

        @Override
        public ListProperty<String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            List list = p.readValueAs(List.class);
            return new SimpleListProperty<>(FXCollections.observableArrayList(list));
        }
    }
}
