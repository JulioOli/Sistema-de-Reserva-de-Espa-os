package reserva.repository;

import reserva.model.Espaco;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação em memória do repositório de Espaços
 * Para RF01 e RF02
 */
public class EspacoRepositoryImpl implements EspacoRepository {
    
    private final Map<String, Espaco> espacos;
    
    public EspacoRepositoryImpl() {
        this.espacos = new HashMap<>();
    }
    
    @Override
    public Espaco salvar(Espaco espaco) {
        if (espaco == null || espaco.getCodigo() == null || espaco.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("Espaço ou código inválido");
        }
        espacos.put(espaco.getCodigo(), espaco);
        return espaco;
    }
    
    @Override
    public Optional<Espaco> buscarPorCodigo(String codigo) {
        return Optional.ofNullable(espacos.get(codigo));
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
    public boolean remover(String codigo) {
        Optional<Espaco> espaco = buscarPorCodigo(codigo);
        if (espaco.isPresent()) {
            espaco.get().setAtivo(false);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean existe(String codigo) {
        return espacos.containsKey(codigo);
    }
}

