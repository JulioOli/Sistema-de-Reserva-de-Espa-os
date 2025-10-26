package reserva.dto;

import reserva.domain.model.Permissao;
import java.util.Set;

/**
 * NivelAcessoDTO - Data Transfer Object para NivelAcesso
 * Usado para transferir dados de nível de acesso entre camadas
 */
public class NivelAcessoDTO {
    private String nome;
    private Set<Permissao> permissoes;
    
    // Construtor vazio
    public NivelAcessoDTO() {}
    
    // Construtor completo
    public NivelAcessoDTO(String nome, Set<Permissao> permissoes) {
        this.nome = nome;
        this.permissoes = permissoes;
    }
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Set<Permissao> getPermissoes() { return permissoes; }
    public void setPermissoes(Set<Permissao> permissoes) { this.permissoes = permissoes; }
    
    @Override
    public String toString() {
        return "NivelAcessoDTO{" +
                "nome='" + nome + '\'' +
                ", permissoes=" + permissoes +
                '}';
    }
}
