package reserva.dto;

import reserva.domain.model.Categoria;

/**
 * CategoriaDTO - Data Transfer Object para Categoria
 * Usado para transferir dados de categoria entre camadas
 */
public class CategoriaDTO {
    private String nome;
    private String descricao;
    private Categoria.TipoCategoria tipo;
    
    // Construtor vazio
    public CategoriaDTO() {}
    
    // Construtor completo
    public CategoriaDTO(String nome, String descricao, Categoria.TipoCategoria tipo) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
    }
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public Categoria.TipoCategoria getTipo() { return tipo; }
    public void setTipo(Categoria.TipoCategoria tipo) { this.tipo = tipo; }
    
    @Override
    public String toString() {
        return "CategoriaDTO{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
