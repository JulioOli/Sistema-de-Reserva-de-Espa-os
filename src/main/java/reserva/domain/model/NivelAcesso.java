package reserva.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * NivelAcesso - Define papéis no sistema com nome e conjunto de permissões
 * RF01.1.2 - O sistema deve permitir ao usuário de nível 1 cadastrar novos níveis,
 * determinando suas permissões de acesso
 */
public class NivelAcesso {
    private String nome;
    private Set<Permissao> permissoes;
    
    public NivelAcesso() {
        this.permissoes = new HashSet<>();
    }
    
    public NivelAcesso(String nome) {
        this.nome = nome;
        this.permissoes = new HashSet<>();
    }
    
    public NivelAcesso(String nome, Set<Permissao> permissoes) {
        this.nome = nome;
        this.permissoes = new HashSet<>(permissoes);
    }
    
    public void adicionarPermissao(Permissao permissao) {
        this.permissoes.add(permissao);
    }
    
    public void removerPermissao(Permissao permissao) {
        this.permissoes.remove(permissao);
    }
    
    public boolean temPermissao(Permissao permissao) {
        return this.permissoes.contains(permissao);
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Set<Permissao> getPermissoes() {
        return new HashSet<>(permissoes);
    }
    
    public void setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = new HashSet<>(permissoes);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NivelAcesso that = (NivelAcesso) o;
        return Objects.equals(nome, that.nome);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
    
    @Override
    public String toString() {
        return "NivelAcesso{" +
                "nome='" + nome + '\'' +
                ", permissoes=" + permissoes +
                '}';
    }
}

