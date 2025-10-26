package reserva.application;

import reserva.domain.model.NivelAcesso;
import reserva.domain.model.Permissao;
import reserva.domain.repository.NivelAcessoRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * AdminNiveisAppService - Gerencia níveis de acesso e permissões
 * Permite criar novos níveis, editar permissões e remover níveis
 * RF01.1.2 - O sistema deve permitir ao usuário de nível 1 cadastrar novos níveis,
 * determinando suas permissões de acesso
 */
public class AdminNiveisAppService {
    
    private final NivelAcessoRepository nivelAcessoRepository;
    
    public AdminNiveisAppService(NivelAcessoRepository nivelAcessoRepository) {
        this.nivelAcessoRepository = nivelAcessoRepository;
    }
    
    /**
     * RF01.1.2 - Cadastra um novo nível de acesso com permissões
     */
    public NivelAcesso cadastrarNivel(String nome, Set<Permissao> permissoes) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do nível é obrigatório");
        }
        
        if (nivelAcessoRepository.existe(nome)) {
            throw new IllegalArgumentException("Já existe um nível com este nome");
        }
        
        NivelAcesso nivel = new NivelAcesso(nome, permissoes);
        return nivelAcessoRepository.salvar(nivel);
    }
    
    /**
     * Atualiza as permissões de um nível existente
     */
    public NivelAcesso atualizarPermissoes(String nome, Set<Permissao> permissoes) {
        NivelAcesso nivel = nivelAcessoRepository.buscarPorNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("Nível não encontrado"));
        
        nivel.setPermissoes(permissoes);
        return nivelAcessoRepository.salvar(nivel);
    }
    
    /**
     * Adiciona uma permissão a um nível
     */
    public NivelAcesso adicionarPermissao(String nome, Permissao permissao) {
        NivelAcesso nivel = nivelAcessoRepository.buscarPorNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("Nível não encontrado"));
        
        nivel.adicionarPermissao(permissao);
        return nivelAcessoRepository.salvar(nivel);
    }
    
    /**
     * Remove uma permissão de um nível
     */
    public NivelAcesso removerPermissao(String nome, Permissao permissao) {
        NivelAcesso nivel = nivelAcessoRepository.buscarPorNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("Nível não encontrado"));
        
        nivel.removerPermissao(permissao);
        return nivelAcessoRepository.salvar(nivel);
    }
    
    /**
     * Remove um nível de acesso
     */
    public boolean excluirNivel(String nome) {
        if (!nivelAcessoRepository.existe(nome)) {
            throw new IllegalArgumentException("Nível não encontrado");
        }
        return nivelAcessoRepository.remover(nome);
    }
    
    /**
     * Lista todos os níveis de acesso
     */
    public List<NivelAcesso> listarTodosNiveis() {
        return nivelAcessoRepository.listarTodos();
    }
    
    /**
     * Busca nível por nome
     */
    public Optional<NivelAcesso> buscarNivelPorNome(String nome) {
        return nivelAcessoRepository.buscarPorNome(nome);
    }
}

