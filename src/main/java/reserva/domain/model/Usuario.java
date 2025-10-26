package reserva.domain.model;

import java.util.Objects;

/**
 * Usuario - Representa uma pessoa que acessa o sistema, identificada por e-mail
 * Possui nível de acesso e status ativo/inativo
 * RF01.1.5 - O usuário de nível 1 deve poder cadastrar novos usuários, informando
 * nome, telefone, ramal, senha, nível e categoria
 */
public class Usuario {
    private String email;
    private String nome;
    private String senha;
    private String telefone;
    private String ramal; // Opcional, obrigatório apenas para funcionários
    private NivelAcesso nivelAcesso;
    private Categoria categoria; // Ex: aluno, servidor, professor
    private boolean ativo;
    private int tentativasAcesso; // RF01.1.6.1 - Controle de tentativas de login
    
    public Usuario() {
        this.ativo = true;
        this.tentativasAcesso = 0;
    }
    
    public Usuario(String email, String nome, String senha, String telefone, 
                   NivelAcesso nivelAcesso, Categoria categoria) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
        this.nivelAcesso = nivelAcesso;
        this.categoria = categoria;
        this.ativo = true;
        this.tentativasAcesso = 0;
    }
    
    /**
     * RF01.1.9 - Valida se a senha atende aos requisitos:
     * - Mínimo 6 caracteres
     * - Mistura de caracteres alfanuméricos
     * - Pelo menos 2 letras maiúsculas
     */
    public static boolean validarSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            return false;
        }
        
        boolean temNumero = senha.matches(".*\\d.*");
        boolean temLetra = senha.matches(".*[a-zA-Z].*");
        long maiusculas = senha.chars().filter(Character::isUpperCase).count();
        
        return temNumero && temLetra && maiusculas >= 2;
    }
    
    /**
     * RF01.1.6.1 - Incrementa tentativas de acesso
     * @return número de tentativas restantes (máximo 5)
     */
    public int incrementarTentativasAcesso() {
        this.tentativasAcesso++;
        return Math.max(0, 5 - this.tentativasAcesso);
    }
    
    /**
     * Reseta o contador de tentativas de acesso
     */
    public void resetarTentativasAcesso() {
        this.tentativasAcesso = 0;
    }
    
    /**
     * Verifica se o usuário atingiu o limite de tentativas
     */
    public boolean atingiuLimiteTentativas() {
        return this.tentativasAcesso >= 5;
    }
    
    /**
     * Verifica se o usuário tem uma permissão específica
     */
    public boolean temPermissao(Permissao permissao) {
        return nivelAcesso != null && nivelAcesso.temPermissao(permissao);
    }
    
    // Getters e Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getRamal() {
        return ramal;
    }
    
    public void setRamal(String ramal) {
        this.ramal = ramal;
    }
    
    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }
    
    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public int getTentativasAcesso() {
        return tentativasAcesso;
    }
    
    public void setTentativasAcesso(int tentativasAcesso) {
        this.tentativasAcesso = tentativasAcesso;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(email, usuario.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", nivelAcesso=" + (nivelAcesso != null ? nivelAcesso.getNome() : "null") +
                ", categoria=" + (categoria != null ? categoria.getNome() : "null") +
                ", ativo=" + ativo +
                '}';
    }
}

