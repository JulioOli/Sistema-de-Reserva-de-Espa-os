package reserva.infrastructure.alternative;

import reserva.domain.model.Espaco;
import reserva.domain.repository.EspacoRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação em memória do EspacoRepository
 */
public class EspacoRepositoryMemoria implements EspacoRepository {
    
    private final Map<String, Espaco> espacos;
    
    public EspacoRepositoryMemoria() {
        this.espacos = new HashMap<>();
    }
    
    @Override
    public Optional<Espaco> buscarPorId(String id) {
        return Optional.ofNullable(espacos.get(id));
    }
    
    @Override
    public List<Espaco> listarTodos() {
        return new ArrayList<>(espacos.values());
    }
    
    @Override
    public List<Espaco> listarAtivos() {
        return espacos.values().stream()
                .filter(Espaco::isAtivo)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Espaco> buscarPorTipo(String tipo) {
        return espacos.values().stream()
                .filter(e -> e.isAtivo() && e.getTipo().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Espaco> buscarPorCapacidade(int capacidadeMinima) {
        return espacos.values().stream()
                .filter(e -> e.isAtivo() && e.getCapacidade() >= capacidadeMinima)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Espaco> buscarPorCategoria(String nomeCategoria) {
        return espacos.values().stream()
                .filter(e -> e.isAtivo() && 
                        e.getCategoria() != null && 
                        e.getCategoria().getNome().equalsIgnoreCase(nomeCategoria))
                .collect(Collectors.toList());
    }
    
    @Override
    public Espaco salvar(Espaco espaco) {
        if (espaco == null || espaco.getId() == null) {
            throw new IllegalArgumentException("Espaço ou ID inválido");
        }
        espacos.put(espaco.getId(), espaco);
        return espaco;
    }
    
    @Override
    public boolean remover(String id) {
        return espacos.remove(id) != null;
    }
    
    @Override
    public boolean existe(String id) {
        return espacos.containsKey(id);
    }
}

