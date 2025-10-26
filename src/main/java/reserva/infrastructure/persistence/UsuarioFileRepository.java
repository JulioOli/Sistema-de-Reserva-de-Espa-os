package reserva.infrastructure.persistence;

import java.io.*;
import java.util.*;
import reserva.domain.model.Usuario;
import reserva.domain.repository.UsuarioRepository;

/**
 * UsuarioFileRepository - Implementação de UsuarioRepository com persistência em arquivo
 * Usa FileStorage para operações de I/O e Serializer para serialização
 * Camada Infrastructure - conforme diagrama de classes
 */
public class UsuarioFileRepository implements UsuarioRepository {
    
    private final FileStorage storage;
    private final Serializer serializer;
    private final String filePath;
    private Map<String, Usuario> cache;
    
    public UsuarioFileRepository() {
        this.storage = new FileStorage();
        this.serializer = new JsonSerializer();
        this.filePath = "data/usuarios.dat";
        this.cache = new HashMap<>();
        carregarDados();
    }
    
    public UsuarioFileRepository(FileStorage storage, Serializer serializer, String filePath) {
        this.storage = storage;
        this.serializer = serializer;
        this.filePath = filePath;
        this.cache = new HashMap<>();
        carregarDados();
    }
    
    /**
     * Busca um usuário por email
     */
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return Optional.ofNullable(cache.get(email));
    }
    
    /**
     * Lista todos os usuários
     */
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(cache.values());
    }
    
    /**
     * Lista usuários ativos
     */
    @Override
    public List<Usuario> listarAtivos() {
        return cache.values().stream()
                .filter(Usuario::isAtivo)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Salva um usuário
     */
    @Override
    public Usuario salvar(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null) {
            throw new IllegalArgumentException("Usuário ou email inválido");
        }
        cache.put(usuario.getEmail(), usuario);
        salvarDados();
        return usuario;
    }
    
    /**
     * Remove um usuário
     */
    @Override
    public boolean remover(String email) {
        boolean removido = cache.remove(email) != null;
        if (removido) {
            salvarDados();
        }
        return removido;
    }
    
    /**
     * Verifica se um usuário existe
     */
    @Override
    public boolean existe(String email) {
        return cache.containsKey(email);
    }
    
    /**
     * Carrega dados do arquivo
     */
    @SuppressWarnings("unchecked")
    private void carregarDados() {
        try {
            if (storage.exists(filePath)) {
                byte[] data = storage.read(filePath);
                if (data != null && data.length > 0) {
                    Object obj = serializer.deserialize(data);
                    if (obj instanceof Map) {
                        cache = (Map<String, Usuario>) obj;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
            cache = new HashMap<>();
        }
    }
    
    /**
     * Salva dados no arquivo
     */
    private void salvarDados() {
        try {
            byte[] data = serializer.serialize(cache);
            storage.write(filePath, data);
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }
}
