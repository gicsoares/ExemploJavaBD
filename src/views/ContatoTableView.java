package views;

import models.Contato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ContatoTableView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public ContatoTableView() {
        super("Gerenciamento de Contatos");
        initializeComponents(); 
    }

    private void initializeComponents() {
        String[] columnNames = {"ID", "Nome", "Email", "Telefone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public void atualizarTabela( List<Contato> contatos) {
        tableModel.setRowCount(0); // Limpa a tabela
        for (Contato contato : contatos) {
            Object[] row = {
                contato.getId(),
                contato.getNome(),
                contato.getEmail(),
                contato.getTelefone(),
            };
            tableModel.addRow(row);
        }
    }

    public int getSelectedContatoId() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return (int) tableModel.getValueAt(selectedRow, 0);
        }
        return -1;
    }
}
