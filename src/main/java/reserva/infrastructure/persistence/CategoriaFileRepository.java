package reserva.infrastructure.persistence;

import java.io.*;
import java.util.*;
import reserva.domain.model.Categoria;
import reserva.domain.repository.CategoriaRepository;

/**
 * CategoriaFileRepository - Implementação de CategoriaRepository com persistência em arquivo
 * Usa FileStorage para operações de I/O e Serializer para serialização
 * Camada Infrastructure - conforme diagrama de classes
 */
public class CategoriaFileRepository implements CategoriaRepository {
    
    private final FileStorage storage;
    private final Serializer serializer;
    private final String filePath;
    private Map<String, Categoria> cache;
    
    public CategoriaFileRepository() {
        this.storage = new FileStorage();
        this.serializer = new JsonSerializer();
        this.filePath = "data/categorias.dat";
        this.cache = new HashMap<>();
        carregarDados();
    }
    
    public CategoriaFileRepository(FileStorage storage, Serializer serializer, String filePath) {
        this.storage = storage;
        this.serializer = serializer;
        this.filePath = filePath;
        this.cache = new HashMap<>();
        carregarDados();
    }
    
    /**
     * Busca uma categoria por nome
     */
    @Override
    public Optional<Categoria> buscarPorNome(String nome) {
        return Optional.ofNullable(cache.get(nome));
    }
    
    /**
     * Lista todas as categorias
     */
    @Override
    public List<Categoria> listarTodas() {
        return new ArrayList<>(cache.values());
    }
    
    /**
     * Lista todas as categorias (alias)
     */
    public List<Categoria> listar() {
        return listarTodas();
    }
    
    /**
     * Lista categorias por tipo
     */
    @Override
    public List<Categoria> listarPorTipo(Categoria.TipoCategoria tipo) {
        return cache.values().stream()
                .filter(c -> c.getTipo() == tipo)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Salva uma categoria
     */
    @Override
    public Categoria salvar(Categoria categoria) {
        if (categoria == null || categoria.getNome() == null) {
            throw new IllegalArgumentException("Categoria ou nome inválido");
        }
        cache.put(categoria.getNome(), categoria);
        salvarDados();
        return categoria;
    }
    
    /**
     * Remove uma categoria
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
     * Verifica se uma categoria existe
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
                        cache = (Map<String, Categoria>) obj;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar categorias: " + e.getMessage());
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
            System.err.println("Erro ao salvar categorias: " + e.getMessage());
        }
    }
}
