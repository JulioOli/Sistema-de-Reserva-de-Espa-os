package reserva;

import reserva.gui.MainWindowSimples;
import reserva.domain.model.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * LauncherSimples - Versão simplificada do launcher
 */
public class LauncherSimples {
    
    public static void main(String[] args) {
        // Verificar argumentos da linha de comando
        if (args.length > 0) {
            String mode = args[0].toLowerCase();
            
            switch (mode) {
                case "--gui":
                case "-g":
                    startGUI();
                    return;
                case "--console":
                case "-c":
                    startConsole();
                    return;
                case "--help":
                case "-h":
                    showHelp();
                    return;
                default:
                    System.out.println("Argumento inválido: " + args[0]);
                    showHelp();
                    return;
            }
        }
        
        // Se não há argumentos, mostrar seletor
        showModeSelector();
    }
    
    private static void showModeSelector() {
        SwingUtilities.invokeLater(() -> {
            JDialog dialog = new JDialog((Frame) null, "Sistema de Reserva de Espaços", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            
            // Flag para controlar se uma opção foi selecionada
            final boolean[] optionSelected = {false};
            
            // Adicionar listener para fechar a aplicação apenas se nenhuma opção foi selecionada
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    if (!optionSelected[0]) {
                        System.exit(0);
                    }
                }
            });
            dialog.setSize(500, 300);
            dialog.setLocationRelativeTo(null);
            dialog.setLayout(new BorderLayout());
            
            // Header
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(70, 130, 180));
            headerPanel.setPreferredSize(new Dimension(0, 80));
            
            JLabel titleLabel = new JLabel("Sistema de Reserva de Espaços", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setForeground(Color.WHITE);
            
            JLabel subtitleLabel = new JLabel("Escolha o modo de execução", SwingConstants.CENTER);
            subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            subtitleLabel.setForeground(Color.WHITE);
            
            headerPanel.setLayout(new BorderLayout());
            headerPanel.add(titleLabel, BorderLayout.CENTER);
            headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
            
            dialog.add(headerPanel, BorderLayout.NORTH);
            
            // Content
            JPanel contentPanel = new JPanel(new GridBagLayout());
            contentPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            
            // Botão GUI
            JButton guiButton = new JButton("🖥️ Interface Gráfica");
            guiButton.setFont(new Font("Arial", Font.BOLD, 16));
            guiButton.setPreferredSize(new Dimension(250, 50));
            guiButton.setBackground(new Color(76, 175, 80));
            guiButton.setForeground(Color.WHITE);
            guiButton.setFocusPainted(false);
            guiButton.setBorder(BorderFactory.createRaisedBevelBorder());
            guiButton.setToolTipText("Executar com interface gráfica completa");
            
            guiButton.addActionListener(e -> {
                optionSelected[0] = true;
                dialog.dispose();
                startGUI();
            });
            
            // Botão Console
            JButton consoleButton = new JButton("⌨️ Modo Console");
            consoleButton.setFont(new Font("Arial", Font.BOLD, 16));
            consoleButton.setPreferredSize(new Dimension(250, 50));
            consoleButton.setBackground(new Color(33, 150, 243));
            consoleButton.setForeground(Color.WHITE);
            consoleButton.setFocusPainted(false);
            consoleButton.setBorder(BorderFactory.createRaisedBevelBorder());
            consoleButton.setToolTipText("Executar demonstração no console");
            
            consoleButton.addActionListener(e -> {
                optionSelected[0] = true;
                dialog.dispose();
                startConsole();
            });
            
            // Layout
            gbc.gridx = 0; gbc.gridy = 0;
            gbc.insets = new Insets(20, 20, 10, 20);
            contentPanel.add(guiButton, gbc);
            
            gbc.gridy = 1;
            gbc.insets = new Insets(10, 20, 20, 20);
            contentPanel.add(consoleButton, gbc);
            
            dialog.add(contentPanel, BorderLayout.CENTER);
            
            // Footer
            JPanel footerPanel = new JPanel();
            footerPanel.setBackground(new Color(240, 240, 240));
            
            JLabel footerLabel = new JLabel("RF01: Gestão de Usuários | RF02: Gestão de Espaços", SwingConstants.CENTER);
            footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            footerLabel.setForeground(Color.GRAY);
            footerPanel.add(footerLabel);
            
            dialog.add(footerPanel, BorderLayout.SOUTH);
            
            dialog.setVisible(true);
        });
    }
    
    private static void startGUI() {
        System.out.println("Iniciando interface gráfica...");
        
        SwingUtilities.invokeLater(() -> {
            try {
                MainWindowSimples mainWindow = new MainWindowSimples();
                // Criar dados iniciais para que os combos funcionem
                criarDadosIniciais(mainWindow);
                mainWindow.setVisible(true);
            } catch (Exception e) {
                System.err.println("Erro ao iniciar interface gráfica: " + e.getMessage());
                e.printStackTrace();
                
                // Fallback para console
                JOptionPane.showMessageDialog(null, 
                    "Erro ao iniciar interface gráfica.\nExecutando modo console...", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                startConsole();
            }
        });
    }
    
    private static void startConsole() {
        System.out.println("Iniciando modo console...");
        System.out.println();
        
        try {
            Main.main(new String[0]);
        } catch (Exception e) {
            System.err.println("Erro ao executar modo console: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void showHelp() {
        System.out.println("Sistema de Reserva de Espaços");
        System.out.println("============================");
        System.out.println();
        System.out.println("Uso: java reserva.LauncherSimples [OPÇÃO]");
        System.out.println();
        System.out.println("Opções:");
        System.out.println("  --gui, -g      Executar com interface gráfica");
        System.out.println("  --console, -c  Executar demonstração no console");
        System.out.println("  --help, -h     Mostrar esta ajuda");
        System.out.println();
        System.out.println("Se nenhuma opção for especificada, será mostrado um seletor.");
        System.out.println();
        System.out.println("Funcionalidades implementadas:");
        System.out.println("  RF01 - Gestão de Usuários (níveis, categorias, usuários)");
        System.out.println("  RF02 - Gestão de Espaços (cadastro, pesquisa, categorização)");
        System.out.println();
        System.out.println("Exemplos:");
        System.out.println("  java reserva.LauncherSimples --gui");
        System.out.println("  java reserva.LauncherSimples --console");
    }
    
    /**
     * Cria dados iniciais para que a interface gráfica funcione corretamente
     */
    private static void criarDadosIniciais(MainWindowSimples mainWindow) {
        try {
            // Obter o adminController através de reflexão (método público)
            java.lang.reflect.Field adminField = MainWindowSimples.class.getDeclaredField("adminController");
            adminField.setAccessible(true);
            reserva.presentation.AdminController adminController = 
                (reserva.presentation.AdminController) adminField.get(mainWindow);
            
            // Criar níveis de acesso básicos
            Set<Permissao> permissoesAdmin = new HashSet<>(Arrays.asList(
                Permissao.VISUALIZAR_ESPACOS, Permissao.CADASTRAR_ESPACOS, 
                Permissao.CADASTRAR_USUARIOS, Permissao.RESERVAR_ESPACOS
            ));
            adminController.cadastrarNivel("Administrador", permissoesAdmin);
            
            Set<Permissao> permissoesUser = new HashSet<>(Arrays.asList(
                Permissao.VISUALIZAR_ESPACOS, Permissao.RESERVAR_ESPACOS
            ));
            adminController.cadastrarNivel("Usuário Comum", permissoesUser);
            
            // Criar categorias básicas
            adminController.cadastrarCategoria("Professor", "Docente da instituição", Categoria.TipoCategoria.USUARIO);
            adminController.cadastrarCategoria("Aluno", "Estudante matriculado", Categoria.TipoCategoria.USUARIO);
            adminController.cadastrarCategoria("Servidor", "Funcionário da instituição", Categoria.TipoCategoria.USUARIO);
            
            adminController.cadastrarCategoria("Laboratório", "Espaços para aulas práticas", Categoria.TipoCategoria.ESPACO);
            adminController.cadastrarCategoria("Auditório", "Espaços para eventos", Categoria.TipoCategoria.ESPACO);
            adminController.cadastrarCategoria("Sala de Aula", "Salas para aulas teóricas", Categoria.TipoCategoria.ESPACO);
            
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível criar dados iniciais: " + e.getMessage());
            // Continuar mesmo se não conseguir criar os dados
        }
    }
}
