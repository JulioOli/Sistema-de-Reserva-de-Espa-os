package reserva.infrastructure.persistence;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * JsonSerializer - Implementação de Serializer usando JSON simples
 * Camada Infrastructure - conforme diagrama de classes
 * 
 * Nota: Esta é uma implementação simplificada para demonstração.
 * Em produção, recomenda-se usar bibliotecas como Jackson ou Gson.
 */
public class JsonSerializer implements Serializer {
    
    @Override
    public byte[] serialize(Object obj) {
        if (obj == null) {
            return "null".getBytes(StandardCharsets.UTF_8);
        }
        
        try {
            // Usando serialização Java nativa encapsulada
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao serializar objeto: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao deserializar objeto: " + e.getMessage(), e);
        }
    }
}
