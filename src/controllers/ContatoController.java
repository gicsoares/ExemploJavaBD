package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import models.Contato;
import repository.ContatoRepository;
import views.ContatoForm;
import views.ContatoTableView;

public class ContatoController {
    private ContatoRepository repository;
    private ContatoTableView tableView;

    public ContatoController() {
        repository = new ContatoRepository();
        tableView = new ContatoTableView();
        inicializar();
    }
    
    private void inicializar() {
    //Atualizar a tabela com os contatos existentes
        atualizarTabela();

    //Criar a barra de ferramentas (toolBar) com botões
        JToolBar toolBar = new JToolBar();
        JButton addButton = new JButton("Adicionar");
        JButton editarButton = new JButton("Editar");
        JButton deletarButton = new JButton("Deletar");

        toolBar.add(addButton);
        toolBar.add(editarButton);
        toolBar.add(deletarButton);

        tableView.add(toolBar, java.awt.BorderLayout.NORTH);

    // Ações dos botões
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarContato();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarContato();
            }
        });

        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarContato();
            }
        });

        tableView.setVisible(true);
    }

    private void atualizarTabela() {
        List<Contato> contatos = repository.obterTodosContatos();
        tableView.atualizarTabela(contatos);
    }

    private void adicionarContato() {
        ContatoForm form = new ContatoForm(tableView, "Adicionar Contato");
        form.setVisible(true);
        Contato novoContato = form.getContato();
        if (novoContato != null) {
            repository.adicionarContato(novoContato);
            atualizarTabela();
        }
    }

    private void editarContato() {
        int selectedId = tableView.getSelectedContatoId();
        if (selectedId != -1) {
            Contato contato = repository.obterContatoPorId(selectedId);
            
            if (contato != null) {
                ContatoForm form = new ContatoForm(
                    tableView,
                    "Editar Contato",
                    contato
                );
                form.setVisible(true);
                Contato contatoAtualizado = form.getContato();

                if (contatoAtualizado != null) {
                    contatoAtualizado = new Contato(
                        selectedId,
                        contatoAtualizado.getNome(),
                        contatoAtualizado.getEmail(),
                        contatoAtualizado.getTelefone()
                    );
                    repository.adicionarContato(contatoAtualizado);
                    atualizarTabela();
                }

            } else {
                JOptionPane.showMessageDialog(
                    tableView,
                    "Contato não encontrado",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                tableView,
                "Selecione um contato para editar.",
                "Aviso",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deletarContato() {
        int selectedId = tableView.getSelectedContatoId();
        if (selectedId != -1) {
            int confirm = JOptionPane.showConfirmDialog(
                tableView,
                "Tem certeza que deseja deletar este contato?",
                "Confirmar deleção",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_NO_OPTION) {
                repository.deletarContato(selectedId);
                atualizarTabela();
            }
        } else {
            JOptionPane.showMessageDialog(
                tableView,
                "Selecione um contato para deletar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void iniciar() {
        // Ações já são inicializadas no construtor
    }
}
