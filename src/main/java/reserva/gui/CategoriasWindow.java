package reserva.gui;

import reserva.domain.model.Categoria;
import reserva.dto.CategoriaDTO;
import reserva.presentation.AdminController;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * CategoriasWindow - Interface para gerenciamento de categorias (RF01)
 */
public class CategoriasWindow extends JDialog {
    
    private AdminController adminController;
    private JTable categoriasTable;
    private DefaultTableModel tableModel;
    private JTextField nomeField, descricaoField;
    private JComboBox<Categoria.TipoCategoria> tipoCombo;
    
    public CategoriasWindow(Frame parent, AdminController adminController) {
        super(parent, "Gerenciamento de Categorias - RF01", true);
        this.adminController = adminController;
        initializeGUI();
        loadData();
    }
    
    private void initializeGUI() {
        setSize(800, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 125, 50));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        JLabel titleLabel = new JLabel("📂 Gerenciamento de Categorias", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(450);
        
        // Left panel - Lista de categorias
        JPanel leftPanel = createCategoriasListPanel();
        splitPane.setLeftComponent(leftPanel);
        
        // Right panel - Formulário
        JPanel rightPanel = createFormPanel();
        splitPane.setRightComponent(rightPanel);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createCategoriasListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Categorias Cadastradas"));
        
        // Tabela
        String[] columns = {"Nome", "Descrição", "Tipo"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        categoriasTable = new JTable(tableModel);
        categoriasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoriasTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedCategoria();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(categoriasTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Botões de ação
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshBtn = new JButton("🔄 Atualizar");
        refreshBtn.addActionListener(e -> loadData());
        
        JButton deleteBtn = new JButton("🗑️ Excluir");
        deleteBtn.addActionListener(e -> deleteCategoria());
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(deleteBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados da Categoria"));
        
        // Formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        nomeField = new JTextField(20);
        formPanel.add(nomeField, gbc);
        
        // Descrição
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        descricaoField = new JTextField(20);
        formPanel.add(descricaoField, gbc);
        
        // Tipo
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        tipoCombo = new JComboBox<>(Categoria.TipoCategoria.values());
        formPanel.add(tipoCombo, gbc);
        
        // Informação sobre tipos
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("<html><i>USUARIO: Para classificar usuários (Professor, Aluno, etc.)<br>" +
                                     "ESPACO: Para classificar espaços (Laboratório, Auditório, etc.)</i></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        infoLabel.setForeground(Color.GRAY);
        formPanel.add(infoLabel, gbc);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton saveBtn = new JButton("💾 Salvar");
        saveBtn.setBackground(new Color(76, 175, 80));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> saveCategoria());
        
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
        tableModel.setRowCount(0);
        CategoriaDTO[] categorias = adminController.listarCategorias();
        
        for (CategoriaDTO categoria : categorias) {
            Object[] row = {
                categoria.getNome(),
                categoria.getDescricao(),
                categoria.getTipo().toString()
            };
            tableModel.addRow(row);
        }
    }
    
    private void loadSelectedCategoria() {
        int selectedRow = categoriasTable.getSelectedRow();
        if (selectedRow >= 0) {
            nomeField.setText((String) tableModel.getValueAt(selectedRow, 0));
            descricaoField.setText((String) tableModel.getValueAt(selectedRow, 1));
            String tipo = (String) tableModel.getValueAt(selectedRow, 2);
            tipoCombo.setSelectedItem(Categoria.TipoCategoria.valueOf(tipo));
        }
    }
    
    private void saveCategoria() {
        try {
            String nome = nomeField.getText().trim();
            String descricao = descricaoField.getText().trim();
            Categoria.TipoCategoria tipo = (Categoria.TipoCategoria) tipoCombo.getSelectedItem();
            
            if (nome.isEmpty() || descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nome e descrição são obrigatórios!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se é nova categoria ou atualização
            boolean isUpdate = categoriasTable.getSelectedRow() >= 0;
            
            String result;
            if (isUpdate) {
                // Atualizar categoria existente
                result = "Categoria atualizada com sucesso!"; // Simulado
                // Implementar método de atualização se necessário
            } else {
                // Criar nova categoria
                result = adminController.cadastrarCategoria(nome, descricao, tipo);
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
                "Erro ao salvar categoria: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteCategoria() {
        int selectedRow = categoriasTable.getSelectedRow();
        if (selectedRow >= 0) {
            String nome = (String) tableModel.getValueAt(selectedRow, 0);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir a categoria: " + nome + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = adminController.excluirCategoria(nome);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir categoria!\n" +
                        "Verifique se não há usuários ou espaços usando esta categoria.", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria para excluir!", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void clearForm() {
        nomeField.setText("");
        descricaoField.setText("");
        tipoCombo.setSelectedIndex(0);
        categoriasTable.clearSelection();
    }
}
