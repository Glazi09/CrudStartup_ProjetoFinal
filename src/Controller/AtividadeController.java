package Controller;

import DAO.AtividadeDAO;
import DAO.InstituicaoParceiraDAO;
import Model.Atividade;
import Model.InstituicaoParceira;
import View.AtividadeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AtividadeController {

    private AtividadeView view;
    private AtividadeDAO dao;
    private InstituicaoParceiraDAO instituicaoDAO;
    private List<Atividade> listaAtual; // guarda a lista mostrada na tabela

    public AtividadeController(AtividadeView view) {
        this.view = view;
        this.dao = new AtividadeDAO();
        this.instituicaoDAO = new InstituicaoParceiraDAO();

        // 1. Preenche a caixinha de instituições e a tabela
        carregarInstituicoes();
        carregarTabela();

        // 2. Liga os botões aos métodos
        this.view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarAtividade();
            }
        });

        this.view.getBtnAtualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarAtividade();
            }
        });

        this.view.getBtnExcluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirAtividade();
            }
        });

        this.view.getBtnLimpar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.limparFormulario();
            }
        });

        // 3. Clique na tabela leva os dados para o formulário
        this.view.getTabelaAtividades().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarLinha();
            }
        });
    }

    // CREATE
    private void salvarAtividade() {
        Atividade atividade = montarAtividade(0);
        if (atividade == null) {
            return; // algum dado inválido, a mensagem já foi mostrada
        }

        dao.inserir(atividade);
        JOptionPane.showMessageDialog(view, "Atividade salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        view.limparFormulario();
        carregarTabela();
    }

    // UPDATE
    private void atualizarAtividade() {
        if (view.getId().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Selecione uma atividade na tabela para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Atividade atividade = montarAtividade(Integer.parseInt(view.getId()));
        if (atividade == null) {
            return;
        }

        dao.atualizar(atividade);
        JOptionPane.showMessageDialog(view, "Atividade atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        view.limparFormulario();
        carregarTabela();
    }

    // DELETE
    private void excluirAtividade() {
        JTable tabela = view.getTabelaAtividades();
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(view, "Selecione uma atividade na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(view, "Tem certeza que deseja excluir esta atividade?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            int id = (int) tabela.getValueAt(linhaSelecionada, 0);
            dao.excluir(id);
            view.limparFormulario();
            carregarTabela();
        }
    }

    // READ: preenche a tabela de atividades
    private void carregarTabela() {
        DefaultTableModel modelo = view.getModeloTabela();
        modelo.setRowCount(0);

        listaAtual = dao.listar();
        for (Atividade a : listaAtual) {
            modelo.addRow(new Object[]{
                    a.getId(),
                    a.getTitulo(),
                    a.getTipo(),
                    a.getHorasComplementares(),
                    a.getInstituicao().getNome()
            });
        }
    }

    // Busca as instituições no banco e coloca na caixinha de seleção
    private void carregarInstituicoes() {
        JComboBox<InstituicaoParceira> combo = view.getCbInstituicao();
        combo.removeAllItems();
        for (InstituicaoParceira inst : instituicaoDAO.listar()) {
            combo.addItem(inst);
        }
    }

    private void selecionarLinha() {
        int linha = view.getTabelaAtividades().getSelectedRow();
        if (linha >= 0) {
            Atividade a = listaAtual.get(linha);
            view.preencherFormulario(
                    String.valueOf(a.getId()),
                    a.getTitulo(),
                    a.getTipo(),
                    String.valueOf(a.getHorasComplementares()),
                    a.getInstituicao().getId()
            );
        }
    }

    // Lê e valida os dados da tela. Devolve null se algo estiver errado.
    private Atividade montarAtividade(int id) {
        String titulo = view.getTitulo();
        String tipo = view.getTipo();
        InstituicaoParceira instituicao = view.getInstituicaoSelecionada();

        if (titulo.isEmpty() || view.getHoras().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha o título e as horas complementares.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (instituicao == null) {
            JOptionPane.showMessageDialog(view, "Cadastre uma instituição parceira antes de criar atividades.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        double horas;
        try {
            // aceita tanto 1.5 quanto 1,5
            horas = Double.parseDouble(view.getHoras().replace(",", "."));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "O campo Horas deve conter apenas números (ex: 1.5).", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (horas <= 0) {
            JOptionPane.showMessageDialog(view, "As horas complementares devem ser maiores que zero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return new Atividade(id, titulo, tipo, horas, instituicao);
    }
}