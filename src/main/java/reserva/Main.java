package reserva;

import reserva.application.*;
import reserva.domain.model.*;
import reserva.domain.repository.*;
import reserva.infrastructure.*;
import reserva.presentation.*;
import java.util.*;

/**
 * Classe principal do Sistema de Reserva de Espaços
 * Demonstra as funcionalidades de RF01 (Gestão de Usuários) e RF02 (Gestão de Espaços)
 * conforme especificado no documento de requisitos
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║           SISTEMA DE RESERVA DE ESPAÇOS                ║");
        System.out.println("║           RF01: Gestão de Usuários                     ║");
        System.out.println("║           RF02: Gestão de Espaços e Categorias         ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
        
        // Inicializa repositórios (em memória para demonstração)
        UsuarioRepository usuarioRepo = new UsuarioRepositoryMemoria();
        CategoriaRepository categoriaRepo = new CategoriaRepositoryMemoria();
        NivelAcessoRepository nivelRepo = new NivelAcessoRepositoryMemoria();
        EspacoRepository espacoRepo = new EspacoRepositoryMemoria();
        
        // Inicializa serviços de aplicação
        AdminUsuariosAppService usuariosService = 
            new AdminUsuariosAppService(usuarioRepo, nivelRepo, categoriaRepo);
        AdminCategoriasAppService categoriasService = 
            new AdminCategoriasAppService(categoriaRepo, espacoRepo);
        AdminNiveisAppService niveisService = 
            new AdminNiveisAppService(nivelRepo);
        AdminEspacosAppService adminEspacosService = 
            new AdminEspacosAppService(espacoRepo, categoriaRepo);
        PesquisaEspacosAppService pesquisaEspacosService = 
            new PesquisaEspacosAppService(espacoRepo);
        
        // Inicializa controladores
        AdminController adminController = 
            new AdminController(usuariosService, categoriasService, niveisService);
        EspacoController espacoController = 
            new EspacoController(adminEspacosService, pesquisaEspacosService);
        
        // ========== DEMONSTRAÇÃO RF01 - GESTÃO DE USUÁRIOS ==========
        System.out.println("═══════════ RF01: GESTÃO DE USUÁRIOS ═══════════\n");
        
        // RF01.1.2 - Cadastrar níveis de acesso
        System.out.println("1. Cadastrando níveis de acesso...");
        Set<Permissao> permissoesNivel1 = new HashSet<>(Arrays.asList(
            Permissao.VISUALIZAR_ESPACOS,
            Permissao.CADASTRAR_ESPACOS,
            Permissao.CADASTRAR_USUARIOS,
            Permissao.RESERVAR_ESPACOS
        ));
        System.out.println(adminController.cadastrarNivel("Nível 1", permissoesNivel1));
        
        Set<Permissao> permissoesGeral = new HashSet<>(Arrays.asList(
            Permissao.VISUALIZAR_ESPACOS,
            Permissao.RESERVAR_ESPACOS
        ));
        System.out.println(adminController.cadastrarNivel("Usuário Geral", permissoesGeral));
        
        // RF01.1.3 - Cadastrar categorias de usuários
        System.out.println("\n2. Cadastrando categorias de usuários...");
        System.out.println(adminController.cadastrarCategoria(
            "Professor", "Docente da universidade", Categoria.TipoCategoria.USUARIO));
        System.out.println(adminController.cadastrarCategoria(
            "Aluno", "Estudante matriculado", Categoria.TipoCategoria.USUARIO));
        System.out.println(adminController.cadastrarCategoria(
            "Servidor", "Servidor técnico-administrativo", Categoria.TipoCategoria.USUARIO));
        
        // RF01.1.5 - Cadastrar usuários
        System.out.println("\n3. Cadastrando usuários...");
        System.out.println(adminController.cadastrarUsuario(
            "admin@ufpb.br", "Administrador do Sistema", "ADmin@123", 
            "(83) 3216-7000", "1234", "Nível 1", "Servidor"));
        System.out.println(adminController.cadastrarUsuario(
            "joao.silva@ufpb.br", "João Silva", "JOao@456", 
            "(83) 98765-4321", null, "Usuário Geral", "Aluno"));
        System.out.println(adminController.cadastrarUsuario(
            "maria.santos@ufpb.br", "Maria Santos", "MAria@789", 
            "(83) 3216-7100", "5678", "Usuário Geral", "Professor"));
        
        // RF01.1.4 - Listar usuários
        System.out.println("\n4. Listando usuários cadastrados:");
        List<Usuario> usuarios = adminController.listarUsuariosAtivos();
        usuarios.forEach(u -> System.out.println("   - " + u.getNome() + 
            " (" + u.getEmail() + ") - " + u.getNivelAcesso().getNome()));
        
        // ========== DEMONSTRAÇÃO RF02 - GESTÃO DE ESPAÇOS ==========
        System.out.println("\n\n═══════════ RF02: GESTÃO DE ESPAÇOS E CATEGORIAS ═══════════\n");
        
        // RF02.2.1 - Cadastrar categorias de espaços
        System.out.println("1. Cadastrando categorias de espaços...");
        System.out.println(adminController.cadastrarCategoria(
            "Sala de Aula", "Salas para aulas regulares", Categoria.TipoCategoria.ESPACO));
        System.out.println(adminController.cadastrarCategoria(
            "Laboratório", "Laboratórios de informática e pesquisa", Categoria.TipoCategoria.ESPACO));
        System.out.println(adminController.cadastrarCategoria(
            "Auditório", "Auditórios para eventos", Categoria.TipoCategoria.ESPACO));
        
        // RF02.2.2 e RF02.2.3 - Cadastrar espaços
        System.out.println("\n2. Cadastrando espaços...");
        System.out.println(espacoController.cadastrarEspaco(
            "SALA-101", "Sala", 40, 
            Arrays.asList("Projetor", "Quadro branco"), true,
            Espaco.TipoAcesso.CHAVE, "Sala de Aula", 
            "Bloco A - 1º andar"));
        
        System.out.println(espacoController.cadastrarEspaco(
            "LAB-INFO-01", "Laboratório", 30,
            Arrays.asList("Computadores", "Projetor", "Ar condicionado"), true,
            Espaco.TipoAcesso.DIGITAL, "Laboratório",
            "Bloco B - 2º andar"));
        
        System.out.println(espacoController.cadastrarEspaco(
            "AUD-PRINCIPAL", "Auditório", 200,
            Arrays.asList("Projetor", "Sistema de som", "Microfones"), true,
            Espaco.TipoAcesso.CHAVE, "Auditório",
            "Bloco Principal - Térreo"));
        
        System.out.println(espacoController.cadastrarEspaco(
            "SALA-202", "Sala", 50,
            Arrays.asList("Projetor"), false,
            Espaco.TipoAcesso.CHAVE, "Sala de Aula",
            "Bloco A - 2º andar"));
        
        // Listar espaços
        System.out.println("\n3. Listando todos os espaços cadastrados:");
        List<Espaco> espacos = espacoController.listarEspacosAtivos();
        espacos.forEach(e -> System.out.println("   - " + e.getId() + 
            ": " + e.getTipo() + " (Cap: " + e.getCapacidade() + 
            ", AC: " + (e.isArCondicionado() ? "Sim" : "Não") + ")"));
        
        // Demonstrar buscas
        System.out.println("\n4. Demonstrando buscas de espaços:");
        
        System.out.println("\n   Espaços do tipo 'Sala':");
        espacoController.buscarPorTipo("Sala").forEach(e -> 
            System.out.println("   - " + e.getId()));
        
        System.out.println("\n   Espaços com capacidade mínima de 45 pessoas:");
        espacoController.buscarPorCapacidade(45).forEach(e -> 
            System.out.println("   - " + e.getId() + " (Cap: " + e.getCapacidade() + ")"));
        
        System.out.println("\n   Espaços com ar condicionado:");
        espacoController.buscarComArCondicionado().forEach(e -> 
            System.out.println("   - " + e.getId()));
        
        System.out.println("\n   Espaços da categoria 'Laboratório':");
        espacoController.buscarPorCategoria("Laboratório").forEach(e -> 
            System.out.println("   - " + e.getId()));
        
        System.out.println("\n\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║           Demonstração concluída com sucesso!          ║");
        System.out.println("║      RF01 e RF02 implementados conforme especificação  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
}
