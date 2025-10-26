package reserva.infrastructure.persistence;

import java.io.*;
import java.util.*;
import reserva.domain.model.NivelAcesso;
import reserva.domain.repository.NivelAcessoRepository;

/**
 * NivelAcessoFileRepository - Implementação de NivelAcessoRepository com persistência em arquivo
 * Usa FileStorage para operações de I/O e Serializer para serialização
 * Camada Infrastructure - conforme diagrama de classes
 */
public class NivelAcessoFileRepository implements NivelAcessoRepository {
    
    private final FileStorage storage;
    private final Serializer serializer;
    private final String filePath;
    private Map<String, NivelAcesso> cache;
    
    public NivelAcessoFileRepository() {
        this.storage = new FileStorage();
        this.serializer = new JsonSerializer();
        this.filePath = "data/niveis.dat";
        this.cache = new HashMap<>();
        carregarDados();
    }
    
    public NivelAcessoFileRepository(FileStorage storage, Serializer serializer, String filePath) {
        this.storage = storage;
        this.serializer = serializer;
        this.filePath = filePath;
        this.cache = new HashMap<>();
        carregarDados();
    }
    
    /**
     * Busca um nível por nome
     */
    @Override
    public Optional<NivelAcesso> buscarPorNome(String nome) {
        return Optional.ofNullable(cache.get(nome));
    }
    
    /**
     * Lista todos os níveis
     */
    @Override
    public List<NivelAcesso> listarTodos() {
        return new ArrayList<>(cache.values());
    }
    
    /**
     * Lista todos os níveis (alias)
     */
    public List<NivelAcesso> listar() {
        return listarTodos();
    }
    
    /**
     * Salva um nível
     */
    @Override
    public NivelAcesso salvar(NivelAcesso nivelAcesso) {
        if (nivelAcesso == null || nivelAcesso.getNome() == null) {
            throw new IllegalArgumentException("Nível de acesso ou nome inválido");
        }
        cache.put(nivelAcesso.getNome(), nivelAcesso);
        salvarDados();
        return nivelAcesso;
    }
    
    /**
     * Remove um nível
     */
    @Override
    public boolean remover(String nome) {
        boolean removido = cache.remove(nome) != null;
        if (removido) {
            salvarDados();
        }
        return removido;
    }
    
    /**
     * Verifica se um nível existe
     */
    @Override
    public boolean existe(String nome) {
        return cache.containsKey(nome);
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
                        cache = (Map<String, NivelAcesso>) obj;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar níveis de acesso: " + e.getMessage());
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
            System.err.println("Erro ao salvar níveis de acesso: " + e.getMessage());
        }
    }
}
