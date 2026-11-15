package Controller;

import DAO.InstituicaoParceiraDAO;
import Model.InstituicaoParceira;
import View.InstituicaoParceiraView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class InstituicaoParceiraController {

    private InstituicaoParceiraView view;
    private InstituicaoParceiraDAO dao;

    // O construtor recebe a View e inicializa o DAO
    public InstituicaoParceiraController(InstituicaoParceiraView view) {
        this.view = view;
        this.dao = new InstituicaoParceiraDAO();

        // 1. Carrega a tabela com o que já existe no banco
        carregarTabela();

        // 2. Liga cada botão ao seu método
        this.view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarInstituicao();
            }
        });

        this.view.getBtnAtualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarInstituicao();
            }
        });

        this.view.getBtnExcluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirInstituicao();
            }
        });

        this.view.getBtnLimpar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.limparFormulario();
            }
        });

        // 3. Ao clicar numa linha da tabela, os dados vão para o formulário
        this.view.getTabelaInstituicoes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarLinha();
            }
        });
    }

    // CREATE: lê os dados da View, valida e manda para o DAO
    private void salvarInstituicao() {
        String nome = view.getNome();
        String cnpj = view.getCnpj();
        String email = view.getEmail();

        if (!dadosValidos(nome, cnpj, email)) {
            return;
        }

        // id = 0 provisório, o banco gera o AUTO_INCREMENT
        InstituicaoParceira inst = new InstituicaoParceira(0, nome, cnpj, email);
        dao.inserir(inst);

        JOptionPane.showMessageDialog(view, "Instituição salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        view.limparFormulario();
        carregarTabela();
    }

    // UPDATE: precisa ter uma linha selecionada (id preenchido)
    private void atualizarInstituicao() {
        if (view.getId().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Selecione uma instituição na tabela para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nome = view.getNome();
        String cnpj = view.getCnpj();
        String email = view.getEmail();

        if (!dadosValidos(nome, cnpj, email)) {
            return;
        }

        int id = Integer.parseInt(view.getId());
        InstituicaoParceira inst = new InstituicaoParceira(id, nome, cnpj, email);
        dao.atualizar(inst);

        JOptionPane.showMessageDialog(view, "Instituição atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        view.limparFormulario();
        carregarTabela();
    }

    // DELETE: pega o id da linha selecionada e confirma antes de excluir
    private void excluirInstituicao() {
        JTable tabela = view.getTabelaInstituicoes();
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(view, "Selecione uma instituição na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(view, "Tem certeza que deseja excluir esta instituição?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            int id = (int) tabela.getValueAt(linhaSelecionada, 0);
            dao.excluir(id);
            view.limparFormulario();
            carregarTabela();
        }
    }

    // READ: busca a lista no banco e preenche a JTable
    private void carregarTabela() {
        DefaultTableModel modelo = view.getModeloTabela();
        modelo.setRowCount(0); // limpa as linhas antes de carregar de novo

        List<InstituicaoParceira> lista = dao.listar();
        for (InstituicaoParceira inst : lista) {
            modelo.addRow(new Object[]{
                    inst.getId(),
                    inst.getNome(),
                    inst.getCnpj(),
                    inst.getEmail()
            });
        }
    }

    // Passa os dados da linha clicada para o formulário
    private void selecionarLinha() {
        JTable tabela = view.getTabelaInstituicoes();
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            view.preencherFormulario(
                    tabela.getValueAt(linha, 0).toString(),
                    tabela.getValueAt(linha, 1).toString(),
                    tabela.getValueAt(linha, 2).toString(),
                    tabela.getValueAt(linha, 3).toString()
            );
        }
    }

    // Regras de negócio: campos obrigatórios e formato básico
    private boolean dadosValidos(String nome, String cnpj, String email) {
        if (nome.isEmpty() || cnpj.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos (Nome, CNPJ e E-mail).", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (cnpj.replaceAll("\\D", "").length() != 14) {
            JOptionPane.showMessageDialog(view, "O CNPJ deve ter 14 números.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(view, "Digite um e-mail válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}