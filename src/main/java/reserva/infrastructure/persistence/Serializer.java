package reserva.infrastructure.persistence;

/**
 * Serializer - Interface para serialização/deserialização de objetos
 * Camada Infrastructure - conforme diagrama de classes
 */
public interface Serializer {
    
    /**
     * Serializa um objeto em array de bytes
     * @param obj Objeto a ser serializado
     * @return Array de bytes representando o objeto
     */
    byte[] serialize(Object obj);
    
    /**
     * Deserializa um array de bytes em um objeto
     * @param bytes Array de bytes a ser deserializado
     * @return Objeto deserializado
     */
    Object deserialize(byte[] bytes);
}
