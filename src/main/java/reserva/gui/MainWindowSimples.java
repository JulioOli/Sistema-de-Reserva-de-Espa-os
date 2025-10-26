package reserva.gui;

import reserva.application.*;
import reserva.domain.repository.*;
import reserva.infrastructure.*;
import reserva.presentation.*;
import java.awt.*;
import javax.swing.*;

/**
 * MainWindowSimples - Versão simplificada da janela principal
 */
public class MainWindowSimples extends JFrame {
    
    private AdminController adminController;
    private EspacoController espacoController;
    
    public MainWindowSimples() {
        initializeControllers();
        initializeGUI();
    }
    
    private void initializeControllers() {
        // Inicializa repositórios (em memória)
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
        adminController = new AdminController(usuariosService, categoriasService, niveisService);
        espacoController = new EspacoController(adminEspacosService, pesquisaEspacosService);
    }
    
    private void initializeGUI() {
        setTitle("Sistema de Reserva de Espaços");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Layout principal
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Menu principal
        JPanel menuPanel = createMenuPanel();
        add(menuPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setPreferredSize(new Dimension(0, 80));
        panel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Sistema de Reserva de Espaços", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("RF01: Gestão de Usuários | RF02: Gestão de Espaços", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(subtitleLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // RF01 - Administração
        JPanel adminPanel = createMenuSection("RF01 - Administração", 
            "Gestão de usuários, categorias e níveis de acesso", 
            new Color(46, 125, 50));
        
        JButton btnUsuarios = createMenuButton("Gerenciar Usuários", "Cadastrar, editar e excluir usuários");
        JButton btnCategorias = createMenuButton("Gerenciar Categorias", "Cadastrar e gerenciar categorias");
        JButton btnNiveis = createMenuButton("Gerenciar Níveis", "Configurar níveis de acesso e permissões");
        
        btnUsuarios.addActionListener(e -> openUsuariosWindow());
        btnCategorias.addActionListener(e -> openCategoriasWindow());
        btnNiveis.addActionListener(e -> openNiveisWindow());
        
        adminPanel.add(btnUsuarios);
        adminPanel.add(Box.createVerticalStrut(10));
        adminPanel.add(btnCategorias);
        adminPanel.add(Box.createVerticalStrut(10));
        adminPanel.add(btnNiveis);
        
        // RF02 - Espaços
        JPanel espacosPanel = createMenuSection("RF02 - Gestão de Espaços", 
            "Cadastro e pesquisa de espaços físicos", 
            new Color(33, 150, 243));
        
        JButton btnGerenciarEspacos = createMenuButton("Gerenciar Espaços", "Cadastrar, editar e excluir espaços");
        JButton btnPesquisarEspacos = createMenuButton("Pesquisar Espaços", "Buscar espaços disponíveis");
        
        btnGerenciarEspacos.addActionListener(e -> openGerenciarEspacosWindow());
        btnPesquisarEspacos.addActionListener(e -> openPesquisarEspacosWindow());
        
        espacosPanel.add(btnGerenciarEspacos);
        espacosPanel.add(Box.createVerticalStrut(10));
        espacosPanel.add(btnPesquisarEspacos);
        
        // Layout
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.5; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 20, 10);
        panel.add(adminPanel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 20, 20);
        panel.add(espacosPanel, gbc);
        
        return panel;
    }
    
    private JPanel createMenuSection(String title, String description, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(color);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setForeground(Color.GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(descLabel);
        panel.add(Box.createVerticalStrut(20));
        
        return panel;
    }
    
    private JButton createMenuButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(250, 40));
        button.setMaximumSize(new Dimension(250, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setToolTipText(tooltip);
        button.setFocusPainted(false);
        
        // Estilo do botão
        button.setBackground(new Color(245, 245, 245));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        
        return button;
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
        panel.setPreferredSize(new Dimension(0, 40));
        
        JLabel footerLabel = new JLabel("Sistema desenvolvido para Engenharia de Software 2", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(Color.GRAY);
        
        panel.add(footerLabel);
        return panel;
    }
    
    // Métodos para abrir janelas específicas
    private void openUsuariosWindow() {
        new UsuariosWindow(this, adminController).setVisible(true);
    }
    
    private void openCategoriasWindow() {
        new CategoriasWindow(this, adminController).setVisible(true);
    }
    
    private void openNiveisWindow() {
        new NiveisWindow(this, adminController).setVisible(true);
    }
    
    private void openGerenciarEspacosWindow() {
        new GerenciarEspacosWindow(this, espacoController, adminController).setVisible(true);
    }
    
    private void openPesquisarEspacosWindow() {
        new PesquisarEspacosWindow(this, espacoController).setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindowSimples().setVisible(true);
        });
    }
}
