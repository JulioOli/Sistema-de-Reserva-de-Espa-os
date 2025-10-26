package reserva.domain.repository;

import reserva.domain.model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * UsuarioRepository - Interface de repositório para usuários
 * Define operações de busca por e-mail, listagem e salvamento
 * Implementa persistência para RF01 (Gestão de Usuários)
 */
public interface UsuarioRepository {
    
    /**
     * Busca um usuário por e-mail (RF01.1.6 - login)
     * @param email E-mail do usuário
     * @return Optional com o usuário se encontrado
     */
    Optional<Usuario> buscarPorEmail(String email);
    
    /**
     * Lista todos os usuários cadastrados
     * @return Lista de todos os usuários
     */
    List<Usuario> listarTodos();
    
    /**
     * Lista apenas usuários ativos
     * @return Lista de usuários ativos
     */
    List<Usuario> listarAtivos();
    
    /**
     * Salva ou atualiza um usuário (RF01.1.5 - cadastro)
     * @param usuario Usuário a ser salvo
     * @return Usuário salvo
     */
    Usuario salvar(Usuario usuario);
    
    /**
     * Remove um usuário (RF01.1.8 - exclusão)
     * @param email E-mail do usuário
     * @return true se removido com sucesso
     */
    boolean remover(String email);
    
    /**
     * Verifica se existe usuário com o e-mail
     * @param email E-mail a verificar
     * @return true se existe
     */
    boolean existe(String email);
}

