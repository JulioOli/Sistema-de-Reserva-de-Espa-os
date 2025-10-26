package reserva.domain.model;

import java.util.Objects;

/**
 * Categoria - Classifica espaços em grupos (como laboratório, sala, auditório)
 * ou classifica usuários (como aluno, servidor, professor)
 * RF01.1.3 - O sistema deve permitir ao usuário de nível 1 cadastrar categorias de usuários
 * RF02.2.1 - O usuário de nível 1 deve poder criar e gerenciar uma hierarquia de categorias
 */
public class Categoria {
    private String nome;
    private String descricao;
    private TipoCategoria tipo; // USUARIO ou ESPACO
    
    public enum TipoCategoria {
        USUARIO, ESPACO
    }
    
    public Categoria() {
    }
    
    public Categoria(String nome, String descricao, TipoCategoria tipo) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public TipoCategoria getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(nome, categoria.nome) && tipo == categoria.tipo;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nome, tipo);
    }
    
    @Override
    public String toString() {
        return "Categoria{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}

