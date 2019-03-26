package service.parsing;



public interface Serializer<E> {
    E deserialize (String data);
    String serialize (E e);
}
