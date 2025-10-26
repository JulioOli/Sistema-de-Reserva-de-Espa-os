package reserva.model;

import java.util.Objects;

/**
 * Classe que representa um espaço/sala no sistema de reservas
 * RF01 - Cadastrar Espaços
 */
public class Espaco {
    private String codigo;
    private String nome;
    private String tipo; // sala, laboratório, auditório, etc
    private int capacidade;
    private String localizacao;
    private String descricao;
    private boolean ativo;

    public Espaco() {
        this.ativo = true;
    }

    public Espaco(String codigo, String nome, String tipo, int capacidade, String localizacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
        this.ativo = true;
    }

    // Getters e Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Espaco espaco = (Espaco) o;
        return Objects.equals(codigo, espaco.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Espaco{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", capacidade=" + capacidade +
                ", localizacao='" + localizacao + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}

