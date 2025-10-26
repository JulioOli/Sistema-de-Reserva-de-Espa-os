package reserva.domain.repository;

import reserva.domain.model.NivelAcesso;
import java.util.List;
import java.util.Optional;

/**
 * NivelAcessoRepository - Interface de repositório para níveis de acesso
 * Define operações de busca por nome, listagem e salvamento
 * Implementa persistência para RF01.1.2 (cadastro de níveis)
 */
public interface NivelAcessoRepository {
    
    /**
     * Busca um nível de acesso por nome
     * @param nome Nome do nível
     * @return Optional com o nível se encontrado
     */
    Optional<NivelAcesso> buscarPorNome(String nome);
    
    /**
     * Lista todos os níveis de acesso
     * @return Lista de todos os níveis
     */
    List<NivelAcesso> listarTodos();
    
    /**
     * Salva ou atualiza um nível de acesso (RF01.1.2)
     * @param nivelAcesso Nível a ser salvo
     * @return Nível salvo
     */
    NivelAcesso salvar(NivelAcesso nivelAcesso);
    
    /**
     * Remove um nível de acesso
     * @param nome Nome do nível
     * @return true se removido com sucesso
     */
    boolean remover(String nome);
    
    /**
     * Verifica se existe nível com o nome
     * @param nome Nome a verificar
     * @return true se existe
     */
    boolean existe(String nome);
}

