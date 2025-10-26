package reserva.infrastructure.alternative;

import reserva.domain.model.NivelAcesso;
import reserva.domain.repository.NivelAcessoRepository;
import java.util.*;

/**
 * Implementação em memória do NivelAcessoRepository
 */
public class NivelAcessoRepositoryMemoria implements NivelAcessoRepository {
    
    private final Map<String, NivelAcesso> niveis;
    
    public NivelAcessoRepositoryMemoria() {
        this.niveis = new HashMap<>();
    }
    
    @Override
    public Optional<NivelAcesso> buscarPorNome(String nome) {
        return Optional.ofNullable(niveis.get(nome));
    }
    
    @Override
    public List<NivelAcesso> listarTodos() {
        return new ArrayList<>(niveis.values());
    }
    
    @Override
    public NivelAcesso salvar(NivelAcesso nivelAcesso) {
        if (nivelAcesso == null || nivelAcesso.getNome() == null) {
            throw new IllegalArgumentException("Nível de acesso ou nome inválido");
        }
        niveis.put(nivelAcesso.getNome(), nivelAcesso);
        return nivelAcesso;
    }
    
    @Override
    public boolean remover(String nome) {
        return niveis.remove(nome) != null;
    }
    
    @Override
    public boolean existe(String nome) {
        return niveis.containsKey(nome);
    }
}

