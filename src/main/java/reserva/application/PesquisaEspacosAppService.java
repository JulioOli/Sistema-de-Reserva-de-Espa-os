package reserva.application;

import reserva.domain.model.Espaco;
import reserva.domain.repository.EspacoRepository;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PesquisaEspacosAppService - Serviço responsável por aplicar filtros de busca de espaços
 * Utiliza o repositório de espaços para retornar listas de espaços disponíveis
 * Implementa funcionalidades de pesquisa do RF02
 */
public class PesquisaEspacosAppService {
    
    private final EspacoRepository espacoRepository;
    
    public PesquisaEspacosAppService(EspacoRepository espacoRepository) {
        this.espacoRepository = espacoRepository;
    }
    
    /**
     * Lista todos os espaços ativos
     */
    public List<Espaco> listarEspacosDisponiveis() {
        return espacoRepository.listarAtivos();
    }
    
    /**
     * RF02.2.3 - Busca espaços por tipo (sala, laboratório, anfiteatro, etc)
     */
    public List<Espaco> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser vazio");
        }
        return espacoRepository.buscarPorTipo(tipo);
    }
    
    /**
     * RF02.2.3 - Busca espaços por capacidade mínima
     */
    public List<Espaco> buscarPorCapacidade(int capacidadeMinima) {
        if (capacidadeMinima < 0) {
            throw new IllegalArgumentException("Capacidade não pode ser negativa");
        }
        return espacoRepository.buscarPorCapacidade(capacidadeMinima);
    }
    
    /**
     * RF02.2.1 - Busca espaços por categoria
     */
    public List<Espaco> buscarPorCategoria(String nomeCategoria) {
        if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria não pode ser vazio");
        }
        return espacoRepository.buscarPorCategoria(nomeCategoria);
    }
    
    /**
     * RF02.2.3 - Busca espaços com ar condicionado
     */
    public List<Espaco> buscarComArCondicionado() {
        return espacoRepository.listarAtivos().stream()
                .filter(Espaco::isArCondicionado)
                .collect(Collectors.toList());
    }
    
    /**
     * RF02.2.3 - Busca espaços por tipo de acesso
     */
    public List<Espaco> buscarPorTipoAcesso(Espaco.TipoAcesso tipoAcesso) {
        if (tipoAcesso == null) {
            throw new IllegalArgumentException("Tipo de acesso não pode ser nulo");
        }
        return espacoRepository.listarAtivos().stream()
                .filter(e -> e.getTipoAcesso() == tipoAcesso)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca espaços por localização
     */
    public List<Espaco> buscarPorLocalizacao(String localizacao) {
        if (localizacao == null || localizacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Localização não pode ser vazia");
        }
        return espacoRepository.listarAtivos().stream()
                .filter(e -> e.getLocalizacao().toLowerCase().contains(localizacao.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca espaços com equipamento específico
     */
    public List<Espaco> buscarComEquipamento(String equipamento) {
        if (equipamento == null || equipamento.trim().isEmpty()) {
            throw new IllegalArgumentException("Equipamento não pode ser vazio");
        }
        return espacoRepository.listarAtivos().stream()
                .filter(e -> e.getEquipamentos().stream()
                        .anyMatch(eq -> eq.toLowerCase().contains(equipamento.toLowerCase())))
                .collect(Collectors.toList());
    }
}

