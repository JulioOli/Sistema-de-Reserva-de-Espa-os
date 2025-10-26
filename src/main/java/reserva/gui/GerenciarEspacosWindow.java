package reserva.gui;

import reserva.domain.model.*;
import reserva.dto.CategoriaDTO;
import reserva.presentation.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * GerenciarEspacosWindow - Interface para gerenciamento de espaços (RF02)
 */
public class GerenciarEspacosWindow extends JDialog {
    
    private EspacoController espacoController;
    private AdminController adminController;
    private JTable espacosTable;
    private DefaultTableModel tableModel;
    private JTextField idField, nomeField, localizacaoField, capacidadeField, equipamentosField;
    private JComboBox<String> categoriaCombo;
    
    public GerenciarEspacosWindow(Frame parent, EspacoController espacoController, AdminController adminController) {
        super(parent, "Gerenciamento de Espaços - RF02", true);
        this.espacoController = espacoController;
        this.adminController = adminController;
        initializeGUI();
        loadData();
    }
    
    private void initializeGUI() {
        setSize(1000, 700);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(33, 150, 243));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        JLabel titleLabel = new JLabel("🏢 Gerenciamento de Espaços", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(600);
        
        // Left panel - Lista de espaços
        JPanel leftPanel = createEspacosListPanel();
        splitPane.setLeftComponent(leftPanel);
        
        // Right panel - Formulário
        JPanel rightPanel = createFormPanel();
        splitPane.setRightComponent(rightPanel);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createEspacosListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Espaços Cadastrados"));
        
        // Tabela
        String[] columns = {"ID", "Nome", "Localização", "Capacidade", "Categoria"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        espacosTable = new JTable(tableModel);
        espacosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        espacosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedEspaco();
            }
        });
        
        // Ajustar largura das colunas
        espacosTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        espacosTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        espacosTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        espacosTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        espacosTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        
        JScrollPane scrollPane = new JScrollPane(espacosTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Botões de ação
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshBtn = new JButton("🔄 Atualizar");
        refreshBtn.addActionListener(e -> loadData());
        
        JButton deleteBtn = new JButton("🗑️ Excluir");
        deleteBtn.addActionListener(e -> deleteEspaco());
        
        JButton detailsBtn = new JButton("📋 Detalhes");
        detailsBtn.addActionListener(e -> showEspacoDetails());
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(detailsBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Espaço"));
        
        // Formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        idField = new JTextField(15);
        formPanel.add(idField, gbc);
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        nomeField = new JTextField(15);
        formPanel.add(nomeField, gbc);
        
        // Localização
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Localização:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        localizacaoField = new JTextField(15);
        formPanel.add(localizacaoField, gbc);
        
        // Capacidade
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Capacidade:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        capacidadeField = new JTextField(15);
        formPanel.add(capacidadeField, gbc);
        
        // Equipamentos
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Equipamentos:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        equipamentosField = new JTextField(15);
        formPanel.add(equipamentosField, gbc);
        
        // Categoria
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        categoriaCombo = new JComboBox<>();
        formPanel.add(categoriaCombo, gbc);
        
        // Informações
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("<html><i>Equipamentos: separe por vírgula (ex: Projetor, Quadro, Ar-condicionado)</i></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        infoLabel.setForeground(Color.GRAY);
        formPanel.add(infoLabel, gbc);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton saveBtn = new JButton("💾 Salvar");
        saveBtn.setBackground(new Color(76, 175, 80));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> saveEspaco());
        
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
        // Carregar espaços
        tableModel.setRowCount(0);
        List<Espaco> espacos = espacoController.listarTodosEspacos();
        
        for (Espaco espaco : espacos) {
            Object[] row = {
                espaco.getId(),
                espaco.getTipo(),
                espaco.getLocalizacao(),
                espaco.getCapacidade(),
                espaco.getCategoria() != null ? espaco.getCategoria().getNome() : "Sem categoria"
            };
            tableModel.addRow(row);
        }
        
        // Carregar categorias de espaço
        loadCategorias();
    }
    
    private void loadCategorias() {
        categoriaCombo.removeAllItems();
        CategoriaDTO[] categorias = adminController.listarCategorias();
        for (CategoriaDTO categoria : categorias) {
            if (categoria.getTipo() == Categoria.TipoCategoria.ESPACO) {
                categoriaCombo.addItem(categoria.getNome());
            }
        }
    }
    
    private void loadSelectedEspaco() {
        int selectedRow = espacosTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            
            // Buscar espaço completo
            List<Espaco> espacos = espacoController.listarTodosEspacos();
            for (Espaco espaco : espacos) {
                if (espaco.getId().equals(id)) {
                    idField.setText(espaco.getId());
                    nomeField.setText(espaco.getTipo());
                    localizacaoField.setText(espaco.getLocalizacao());
                    capacidadeField.setText(String.valueOf(espaco.getCapacidade()));
                    
                    // Equipamentos
                    if (espaco.getEquipamentos() != null && !espaco.getEquipamentos().isEmpty()) {
                        equipamentosField.setText(String.join(", ", espaco.getEquipamentos()));
                    } else {
                        equipamentosField.setText("");
                    }
                    
                    // Categoria
                    if (espaco.getCategoria() != null) {
                        categoriaCombo.setSelectedItem(espaco.getCategoria().getNome());
                    }
                    break;
                }
            }
        }
    }
    
    private void saveEspaco() {
        try {
            String id = idField.getText().trim();
            String nome = nomeField.getText().trim();
            String localizacao = localizacaoField.getText().trim();
            String capacidadeStr = capacidadeField.getText().trim();
            String equipamentos = equipamentosField.getText().trim();
            String categoria = (String) categoriaCombo.getSelectedItem();
            
            if (id.isEmpty() || nome.isEmpty() || localizacao.isEmpty() || capacidadeStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "ID, nome, localização e capacidade são obrigatórios!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int capacidade;
            try {
                capacidade = Integer.parseInt(capacidadeStr);
                if (capacidade <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Capacidade deve ser um número inteiro positivo!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar se é novo espaço ou atualização
            boolean isUpdate = espacosTable.getSelectedRow() >= 0;
            
            String result;
            // Converter equipamentos para lista
            List<String> equipamentosList = null;
            if (!equipamentos.isEmpty()) {
                String[] equipArray = equipamentos.split(",");
                equipamentosList = new ArrayList<>();
                for (String equip : equipArray) {
                    equipamentosList.add(equip.trim());
                }
            }
            
            if (isUpdate) {
                // Atualizar espaço existente
                result = espacoController.atualizarEspaco(id, nome, capacidade, 
                    equipamentosList, false, Espaco.TipoAcesso.CHAVE, categoria, localizacao);
            } else {
                // Criar novo espaço
                result = espacoController.cadastrarEspaco(id, nome, capacidade, 
                    equipamentosList, false, Espaco.TipoAcesso.CHAVE, categoria, localizacao);
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
                "Erro ao salvar espaço: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteEspaco() {
        int selectedRow = espacosTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            String nome = (String) tableModel.getValueAt(selectedRow, 1);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o espaço: " + nome + " (ID: " + id + ")?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                String result = espacoController.excluirEspaco(id);
                if (result.contains("sucesso")) {
                    JOptionPane.showMessageDialog(this, result, 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, result, 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um espaço para excluir!", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void showEspacoDetails() {
        int selectedRow = espacosTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            
            // Buscar espaço completo
            List<Espaco> espacos = espacoController.listarTodosEspacos();
            for (Espaco espaco : espacos) {
                if (espaco.getId().equals(id)) {
                    StringBuilder details = new StringBuilder();
                    details.append("ID: ").append(espaco.getId()).append("\n");
                    details.append("Tipo: ").append(espaco.getTipo()).append("\n");
                    details.append("Localização: ").append(espaco.getLocalizacao()).append("\n");
                    details.append("Capacidade: ").append(espaco.getCapacidade()).append(" pessoas\n");
                    details.append("Categoria: ").append(espaco.getCategoria() != null ? 
                        espaco.getCategoria().getNome() : "Sem categoria").append("\n");
                    
                    if (espaco.getEquipamentos() != null && !espaco.getEquipamentos().isEmpty()) {
                        details.append("Equipamentos:\n");
                        for (String equipamento : espaco.getEquipamentos()) {
                            details.append("  • ").append(equipamento).append("\n");
                        }
                    } else {
                        details.append("Equipamentos: Nenhum equipamento cadastrado\n");
                    }
                    
                    JOptionPane.showMessageDialog(this, details.toString(), 
                        "Detalhes do Espaço", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um espaço para ver os detalhes!", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void clearForm() {
        idField.setText("");
        nomeField.setText("");
        localizacaoField.setText("");
        capacidadeField.setText("");
        equipamentosField.setText("");
        categoriaCombo.setSelectedIndex(-1);
        espacosTable.clearSelection();
    }
}
