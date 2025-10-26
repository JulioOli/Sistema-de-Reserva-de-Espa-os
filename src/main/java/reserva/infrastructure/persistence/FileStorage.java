package reserva.infrastructure.persistence;

import java.io.*;
import java.nio.file.*;

/**
 * FileStorage - Classe responsável por operações de leitura e escrita em arquivos
 * Camada Infrastructure - conforme diagrama de classes
 */
public class FileStorage {
    
    /**
     * Lê o conteúdo de um arquivo e retorna como array de bytes
     * @param path Caminho do arquivo
     * @return Array de bytes com o conteúdo do arquivo
     * @throws IOException Se houver erro na leitura
     */
    public byte[] read(String path) throws IOException {
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            return new byte[0];
        }
        return Files.readAllBytes(filePath);
    }
    
    /**
     * Escreve dados em um arquivo
     * @param path Caminho do arquivo
     * @param data Array de bytes a ser escrito
     * @throws IOException Se houver erro na escrita
     */
    public void write(String path, byte[] data) throws IOException {
        Path filePath = Paths.get(path);
        
        // Criar diretórios pai se não existirem
        Path parentDir = filePath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
        
        Files.write(filePath, data);
    }
    
    /**
     * Obtém um handle (RandomAccessFile) para o arquivo
     * @param path Caminho do arquivo
     * @return RandomAccessFile para acesso ao arquivo
     * @throws IOException Se houver erro ao abrir o arquivo
     */
    public RandomAccessFile lock(String path) throws IOException {
        Path filePath = Paths.get(path);
        
        // Criar diretórios pai se não existirem
        Path parentDir = filePath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
        
        // Criar arquivo se não existir
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        
        return new RandomAccessFile(filePath.toFile(), "rw");
    }
    
    /**
     * Verifica se um arquivo existe
     * @param path Caminho do arquivo
     * @return true se o arquivo existe, false caso contrário
     */
    public boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }
    
    /**
     * Remove um arquivo
     * @param path Caminho do arquivo
     * @throws IOException Se houver erro ao remover o arquivo
     */
    public void delete(String path) throws IOException {
        Path filePath = Paths.get(path);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }
}
