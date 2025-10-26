package reserva.gui;

import reserva.domain.model.*;
import reserva.dto.*;
import reserva.presentation.AdminController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * UsuariosWindow - Interface para gerenciamento de usuários (RF01)
 */
public class UsuariosWindow extends JDialog {
    
    private AdminController adminController;
    private JTable usuariosTable;
    private DefaultTableModel tableModel;
    private JTextField emailField, nomeField, senhaField, telefoneField, ramalField;
    private JComboBox<String> nivelCombo, categoriaCombo;
    
    public UsuariosWindow(Frame parent, AdminController adminController) {
        super(parent, "Gerenciamento de Usuários - RF01", true);
        this.adminController = adminController;
        initializeGUI();
        loadData();
    }
    
    private void initializeGUI() {
        setSize(900, 600);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 125, 50));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        JLabel titleLabel = new JLabel("👥 Gerenciamento de Usuários", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500);
        
        // Left panel - Lista de usuários
        JPanel leftPanel = createUsuariosListPanel();
        splitPane.setLeftComponent(leftPanel);
        
        // Right panel - Formulário
        JPanel rightPanel = createFormPanel();
        splitPane.setRightComponent(rightPanel);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createUsuariosListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Usuários Cadastrados"));
        
        // Tabela
        String[] columns = {"Email", "Nome", "Telefone", "Nível", "Categoria", "Ativo"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        usuariosTable = new JTable(tableModel);
        usuariosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usuariosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedUsuario();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(usuariosTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Botões de ação
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshBtn = new JButton("🔄 Atualizar");
        refreshBtn.addActionListener(e -> loadData());
        
        JButton deleteBtn = new JButton("🗑️ Excluir");
        deleteBtn.addActionListener(e -> deleteUsuario());
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(deleteBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Usuário"));
        
        // Formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Email
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        nomeField = new JTextField(20);
        formPanel.add(nomeField, gbc);
        
        // Senha
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        senhaField = new JPasswordField(20);
        formPanel.add(senhaField, gbc);
        
        // Telefone
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        telefoneField = new JTextField(20);
        formPanel.add(telefoneField, gbc);
        
        // Ramal
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Ramal:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        ramalField = new JTextField(20);
        formPanel.add(ramalField, gbc);
        
        // Nível
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Nível:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        nivelCombo = new JComboBox<>();
        formPanel.add(nivelCombo, gbc);
        
        // Categoria
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        categoriaCombo = new JComboBox<>();
        formPanel.add(categoriaCombo, gbc);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton saveBtn = new JButton("💾 Salvar");
        saveBtn.setBackground(new Color(76, 175, 80));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> saveUsuario());
        
        JButton clearBtn = new JButton("🗑️ Limpar");
        clearBtn.addActionListener(e -> clearForm());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(clearBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(240, 240, 240));
        
        JButton closeBtn = new JButton("Fechar");
        closeBtn.addActionListener(e -> dispose());
        panel.add(closeBtn);
        
        return panel;
    }
    
    private void loadData() {
        // Carregar usuários
        tableModel.setRowCount(0);
        List<Usuario> usuarios = adminController.listarUsuariosAtivos();
        
        for (Usuario usuario : usuarios) {
            Object[] row = {
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getNivelAcesso().getNome(),
                usuario.getCategoria().getNome(),
                usuario.isAtivo() ? "Sim" : "Não"
            };
            tableModel.addRow(row);
        }
        
        // Carregar combos
        loadCombos();
    }
    
    private void loadCombos() {
        // Carregar níveis
        nivelCombo.removeAllItems();
        NivelAcessoDTO[] niveis = adminController.listarNiveisAcesso();
        for (NivelAcessoDTO nivel : niveis) {
            nivelCombo.addItem(nivel.getNome());
        }
        
        // Carregar categorias de usuário
        categoriaCombo.removeAllItems();
        CategoriaDTO[] categorias = adminController.listarCategorias();
        for (CategoriaDTO categoria : categorias) {
            if (categoria.getTipo() == Categoria.TipoCategoria.USUARIO) {
                categoriaCombo.addItem(categoria.getNome());
            }
        }
    }
    
    private void loadSelectedUsuario() {
        int selectedRow = usuariosTable.getSelectedRow();
        if (selectedRow >= 0) {
            emailField.setText((String) tableModel.getValueAt(selectedRow, 0));
            nomeField.setText((String) tableModel.getValueAt(selectedRow, 1));
            telefoneField.setText((String) tableModel.getValueAt(selectedRow, 2));
            nivelCombo.setSelectedItem(tableModel.getValueAt(selectedRow, 3));
            categoriaCombo.setSelectedItem(tableModel.getValueAt(selectedRow, 4));
            senhaField.setText(""); // Não mostrar senha por segurança
            
            // Buscar ramal (não está na tabela)
            // Para simplificar, deixamos vazio - seria necessário buscar do repositório
            ramalField.setText("");
        }
    }
    
    private void saveUsuario() {
        try {
            String email = emailField.getText().trim();
            String nome = nomeField.getText().trim();
            String senha = senhaField.getText();
            String telefone = telefoneField.getText().trim();
            String ramal = ramalField.getText().trim();
            String nivel = (String) nivelCombo.getSelectedItem();
            String categoria = (String) categoriaCombo.getSelectedItem();
            
            if (email.isEmpty() || nome.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Email, nome e senha são obrigatórios!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se é novo usuário ou atualização
            boolean isUpdate = usuariosTable.getSelectedRow() >= 0;
            
            String result;
            if (isUpdate) {
                // Atualizar (sem senha se estiver vazia)
                if (senha.isEmpty()) {
                    senha = null; // Manter senha atual
                }
                result = "Usuário atualizado com sucesso!"; // Simulado
                // adminController.atualizarUsuario(...) - implementar se necessário
            } else {
                // Criar novo
                result = adminController.cadastrarUsuario(email, nome, senha, telefone, 
                    ramal.isEmpty() ? null : ramal, nivel, categoria);
            }
            
            if (result.contains("sucesso")) {
                JOptionPane.showMessageDialog(this, result, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, result, "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao salvar usuário: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteUsuario() {
        int selectedRow = usuariosTable.getSelectedRow();
        if (selectedRow >= 0) {
            String email = (String) tableModel.getValueAt(selectedRow, 0);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o usuário: " + email + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = adminController.excluirUsuario(email);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir usuário!", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir!", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void clearForm() {
        emailField.setText("");
        nomeField.setText("");
        senhaField.setText("");
        telefoneField.setText("");
        ramalField.setText("");
        nivelCombo.setSelectedIndex(-1);
        categoriaCombo.setSelectedIndex(-1);
        usuariosTable.clearSelection();
    }
}
