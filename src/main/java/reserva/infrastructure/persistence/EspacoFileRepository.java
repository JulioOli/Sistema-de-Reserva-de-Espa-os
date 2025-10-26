package reserva.infrastructure.persistence;

import java.io.*;
import java.util.*;
import reserva.domain.model.Espaco;
import reserva.domain.repository.EspacoRepository;

/**
 * EspacoFileRepository - Implementação de EspacoRepository com persistência em arquivo
 * Usa FileStorage para operações de I/O e Serializer para serialização
 * Camada Infrastructure - conforme diagrama de classes
 */
public class EspacoFileRepository implements EspacoRepository {
    
    private final FileStorage storage;
    private final Serializer serializer;
    private final String filePath;
    private Map<String, Espaco> cache;
    
    public EspacoFileRepository() {
        this.storage = new FileStorage();
        this.serializer = new JsonSerializer();
        this.filePath = "data/espacos.dat";
        this.cache = new HashMap<>();
        carregarDados();
    }
    
    public EspacoFileRepository(FileStorage storage, Serializer serializer, String filePath) {
        this.storage = storage;
        this.serializer = serializer;
        this.filePath = filePath;
        this.cache = new HashMap<>();
        carregarDados();
    }
    
    /**
     * Busca um espaço por código (ID)
     */
    @Override
    public Optional<Espaco> buscarPorId(String codigo) {
        return Optional.ofNullable(cache.get(codigo));
    }
    
    /**
     * Lista todos os espaços
     */
    @Override
    public List<Espaco> listarTodos() {
        return new ArrayList<>(cache.values());
    }
    
    /**
     * Lista todos os espaços (alias)
     */
    public List<Espaco> listar() {
        return listarTodos();
    }
    
    /**
     * Salva um espaço no repositório
     */
    @Override
    public Espaco salvar(Espaco espaco) {
        if (espaco == null || espaco.getId() == null) {
            throw new IllegalArgumentException("Espaço ou ID inválido");
        }
        cache.put(espaco.getId(), espaco);
        salvarDados();
        return espaco;
    }
    
    /**
     * Verifica se um espaço existe
     */
    @Override
    public boolean existe(String codigo) {
        return cache.containsKey(codigo);
    }
    
    /**
     * Remove um espaço
     */
    @Override
    public boolean remover(String codigo) {
        boolean removido = cache.remove(codigo) != null;
        if (removido) {
            salvarDados();
        }
        return removido;
    }
    
    /**
     * Lista espaços ativos
     */
    @Override
    public List<Espaco> listarAtivos() {
        return cache.values().stream()
                .filter(Espaco::isAtivo)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Busca espaços por tipo
     */
    @Override
    public List<Espaco> buscarPorTipo(String tipo) {
        return cache.values().stream()
                .filter(e -> e.getTipo().equalsIgnoreCase(tipo))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Busca espaços por capacidade mínima
     */
    @Override
    public List<Espaco> buscarPorCapacidade(int capacidadeMinima) {
        return cache.values().stream()
                .filter(e -> e.getCapacidade() >= capacidadeMinima)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Busca espaços por categoria
     */
    @Override
    public List<Espaco> buscarPorCategoria(String nomeCategoria) {
        return cache.values().stream()
                .filter(e -> e.getCategoria() != null && 
                           e.getCategoria().getNome().equalsIgnoreCase(nomeCategoria))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
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
                        cache = (Map<String, Espaco>) obj;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar espaços: " + e.getMessage());
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
            System.err.println("Erro ao salvar espaços: " + e.getMessage());
        }
    }
}
