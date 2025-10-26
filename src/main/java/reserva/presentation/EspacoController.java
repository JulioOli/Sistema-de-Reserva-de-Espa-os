package reserva.presentation;

import reserva.application.AdminEspacosAppService;
import reserva.application.PesquisaEspacosAppService;
import reserva.domain.model.Espaco;
import java.util.List;

/**
 * EspacoController - Gerencia operações relacionadas a espaços
 * Permite criar, atualizar, inativar e excluir espaços, além de pesquisar
 * usando filtros
 * Implementa RF02 (Gestão de Espaços e Categorias) - Interface de apresentação
 */
public class EspacoController {
    
    private final AdminEspacosAppService adminService;
    private final PesquisaEspacosAppService pesquisaService;
    
    public EspacoController(AdminEspacosAppService adminService,
                           PesquisaEspacosAppService pesquisaService) {
        this.adminService = adminService;
        this.pesquisaService = pesquisaService;
    }
    
    // ========== ADMINISTRAÇÃO DE ESPAÇOS (RF02.2.2 e RF02.2.3) ==========
    
    /**
     * RF02.2.2 e RF02.2.3 - Cadastra um novo espaço
     */
    public String cadastrarEspaco(String id, String tipo, int capacidade,
                                   List<String> equipamentos, boolean arCondicionado,
                                   Espaco.TipoAcesso tipoAcesso, String nomeCategoria,
                                   String localizacao) {
        try {
            Espaco espaco = adminService.cadastrarEspaco(
                id, tipo, capacidade, equipamentos, arCondicionado,
                tipoAcesso, nomeCategoria, localizacao
            );
            return "Espaço cadastrado com sucesso: " + espaco.getId();
        } catch (IllegalArgumentException e) {
            return "Erro ao cadastrar espaço: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }
    
    /**
     * Atualiza um espaço existente
     */
    public String atualizarEspaco(String id, String tipo, Integer capacidade,
                                   List<String> equipamentos, Boolean arCondicionado,
                                   Espaco.TipoAcesso tipoAcesso, String nomeCategoria,
                                   String localizacao) {
        try {
            adminService.atualizarEspaco(id, tipo, capacidade, equipamentos,
                                        arCondicionado, tipoAcesso, nomeCategoria, localizacao);
            return "Espaço atualizado com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro ao atualizar espaço: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }
    
    /**
     * Inativa um espaço
     */
    public String inativarEspaco(String id) {
        try {
            adminService.inativarEspaco(id);
            return "Espaço inativado com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }
    
    /**
     * Exclui um espaço
     */
    public String excluirEspaco(String id) {
        try {
            adminService.excluirEspaco(id);
            return "Espaço excluído com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }
    
    /**
     * Lista todos os espaços
     */
    public List<Espaco> listarTodosEspacos() {
        return adminService.listarTodosEspacos();
    }
    
    /**
     * Lista apenas espaços ativos
     */
    public List<Espaco> listarEspacosAtivos() {
        return adminService.listarEspacosAtivos();
    }
    
    // ========== PESQUISA DE ESPAÇOS (RF02) ==========
    
    /**
     * Lista espaços disponíveis
     */
    public List<Espaco> listarEspacosDisponiveis() {
        return pesquisaService.listarEspacosDisponiveis();
    }
    
    /**
     * RF02.2.3 - Busca espaços por tipo
     */
    public List<Espaco> buscarPorTipo(String tipo) {
        try {
            return pesquisaService.buscarPorTipo(tipo);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * RF02.2.3 - Busca espaços por capacidade mínima
     */
    public List<Espaco> buscarPorCapacidade(int capacidadeMinima) {
        try {
            return pesquisaService.buscarPorCapacidade(capacidadeMinima);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * RF02.2.1 - Busca espaços por categoria
     */
    public List<Espaco> buscarPorCategoria(String nomeCategoria) {
        try {
            return pesquisaService.buscarPorCategoria(nomeCategoria);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * RF02.2.3 - Busca espaços com ar condicionado
     */
    public List<Espaco> buscarComArCondicionado() {
        return pesquisaService.buscarComArCondicionado();
    }
    
    /**
     * RF02.2.3 - Busca espaços por tipo de acesso
     */
    public List<Espaco> buscarPorTipoAcesso(Espaco.TipoAcesso tipoAcesso) {
        try {
            return pesquisaService.buscarPorTipoAcesso(tipoAcesso);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Busca espaços por localização
     */
    public List<Espaco> buscarPorLocalizacao(String localizacao) {
        try {
            return pesquisaService.buscarPorLocalizacao(localizacao);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Busca espaços com equipamento específico
     */
    public List<Espaco> buscarComEquipamento(String equipamento) {
        try {
            return pesquisaService.buscarComEquipamento(equipamento);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }
}

