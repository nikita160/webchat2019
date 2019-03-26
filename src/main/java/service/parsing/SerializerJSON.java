package service.parsing;


import com.google.gson.Gson;

public class SerializerJSON<E> implements Serializer {

    private static final Gson GSON = new Gson();
    private final Class<E> typeParameterClass;

    public SerializerJSON (Class<E> typeParameterClass){
       this.typeParameterClass = typeParameterClass;
    }

    @Override
    public E deserialize(String data) {

        return GSON.fromJson(data,typeParameterClass);
    }

    @Override
    public String serialize(Object object) {
        return GSON.toJson(object);
    }
}
