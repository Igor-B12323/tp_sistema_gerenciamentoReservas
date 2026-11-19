package br.com.empresa.view;

import br.com.empresa.controller.RecursoController;
import br.com.empresa.model.Recurso;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrameRecursos extends JInternalFrame{
    
    private RecursoController controller = null;
    private List<Recurso> listaRecursos;
    private int indiceAtual = -1;
    private boolean incluindo = false;
    private boolean editando = false;
    private boolean modoBusca = false;

    private JToolBar tbBotoes;

    private JButton btnInicio, btnAnterior, btnProximo, btnFinal, btnBuscar,
            btnIncluir, btnEditar, btnSalvar, btnExcluir, btnCancelar, btnSair;

    private JTextField txtIdRecurso, txtNomeRecurso, txtCapacidade,
                       txtLocalizacao, txtEhLab;

    private JTable tabRec;
    private DefaultTableModel tableModel;
    private JLabel lbMensagem;

    public FrameRecursos() {
        this.controller = new RecursoController();

        setTitle("Manutenção de Recursos");
        setSize(1000, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        carregarDados();
    }

    private void inicializarComponentes() {
        tbBotoes = new JToolBar();

        btnInicio = criarBotao("Início", "/resources/first.png", 65, 45);
        btnAnterior = criarBotao("Voltar", "/resources/prior.png", 65, 45);
        btnProximo = criarBotao("Avançar", "/resources/next.png", 65, 45);
        btnFinal = criarBotao("Final", "/resources/last.png", 65, 45);

        btnBuscar = criarBotao("Buscar", "/resources/find.png", 65, 45);
        btnIncluir = criarBotao("Incluir", "/resources/add.png", 65, 45);
        btnEditar = criarBotao("Editar", "/resources/copy.png", 65, 45);
        btnSalvar = criarBotao("Salvar", "/resources/save.png", 65, 45);
        btnExcluir = criarBotao("Excluir", "/resources/minus.png", 65, 45);
        btnCancelar = criarBotao("Cancelar", "/resources/undo.png", 65, 45);
        btnSair = criarBotao("Sair", "/resources/abort.png", 65, 45);

        txtIdRecurso = new JTextField();
        txtNomeRecurso = new JTextField();
        txtCapacidade = new JTextField();
        txtLocalizacao = new JTextField();
        txtEhLab = new JTextField();

        lbMensagem = new JLabel("Mensagem: ");

        String[] colunas = {"Id Recurso", "Nome", "Capacidade", "Localização", "Laboratório"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabRec = new JTable(tableModel);
    }

    private JButton criarBotao(String texto, String localIcone, int larg,
                               int alt)
    {

        var botao = new JButton(texto);
        Icon icone = new ImageIcon(getClass().getResource(localIcone));
  
        botao.setIcon(icone);
        botao.setPreferredSize(new Dimension(larg, alt));
        botao.setVerticalTextPosition(SwingConstants.BOTTOM);
        botao.setHorizontalTextPosition(SwingConstants.CENTER);
        botao.setFocusPainted(false);

        return botao;
    }

    private void configurarLayout() {
 
        tbBotoes.setLayout(new FlowLayout());

        tbBotoes.add(btnInicio);
        tbBotoes.add(btnAnterior);
        tbBotoes.add(btnProximo);
        tbBotoes.add(btnFinal);
        tbBotoes.addSeparator();

        tbBotoes.add(btnBuscar);
        tbBotoes.addSeparator();
        tbBotoes.add(btnIncluir);
        tbBotoes.add(btnEditar);
        tbBotoes.add(btnSalvar);
        tbBotoes.add(btnExcluir);
        tbBotoes.add(btnCancelar);
        tbBotoes.add(btnSair);

        tbBotoes.setRollover(true);

        JPanel pnlGrade = new JPanel(new BorderLayout());
        pnlGrade.add(new JScrollPane(tabRec), BorderLayout.CENTER);

        JPanel pnlCampos = new JPanel(new GridLayout(5, 2));
        pnlCampos.add(new JLabel("Id Recurso:"));
        pnlCampos.add(txtIdRecurso);
        pnlCampos.add(new JLabel("Nome:"));
        pnlCampos.add(txtNomeRecurso);
        pnlCampos.add(new JLabel("Capacidade:"));
        pnlCampos.add(txtCapacidade);
        pnlCampos.add(new JLabel("Localização:"));
        pnlCampos.add(txtLocalizacao);
        pnlCampos.add(new JLabel("É laboratório:"));
        pnlCampos.add(txtEhLab);

        JPanel pnlMensagem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlMensagem.add(lbMensagem);

        Container cntForm = getContentPane();
        cntForm.setLayout(new BorderLayout());
        cntForm.add(tbBotoes , BorderLayout.NORTH);
        cntForm.add(pnlGrade , BorderLayout.WEST);
        cntForm.add(pnlCampos , BorderLayout.CENTER);
        cntForm.add(pnlMensagem , BorderLayout.SOUTH);
    }

    private void configurarEventos() {
  
        btnInicio.addActionListener(e -> navegarPara(0));
        btnAnterior.addActionListener(e -> navegarPara(indiceAtual - 1));
        btnProximo.addActionListener(e -> navegarPara(indiceAtual + 1));
        btnFinal.addActionListener(e -> navegarPara(listaRecursos != null ?
                listaRecursos.size() - 1 : -1));

        btnSalvar.addActionListener(e -> executarSalvar());
        btnIncluir.addActionListener(e -> iniciarInclusao());
        btnEditar.addActionListener(e -> iniciarEdicao());
        btnExcluir.addActionListener(e -> executarExclusao());
        btnBuscar.addActionListener(e -> {if (!modoBusca) {
                                            iniciarBusca(true);
                                        } else {
                                            executarBusca();
                                        }});
        btnCancelar.addActionListener(e -> carregarDados());
        btnSair.addActionListener(e -> dispose());
    }

    private void carregarDados() {
        try {
            editando = false;
            incluindo = false;
            iniciarBusca(false);
            txtIdRecurso.setEnabled(true);
            listaRecursos = controller.carregarTodos();
            atualizarTabela();
            if (!listaRecursos.isEmpty()) {
                navegarPara(0);
            } else {
                limparCampos();
            }
            lbMensagem.setText("Mensagem: Dados carregados com sucesso.");
        }
        catch (Exception ex) {
            lbMensagem.setText("Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Recurso r : listaRecursos) {
            tableModel.addRow(
                 new Object[]
                    {
                      r.getId(), r.getNome(),
                      r.getCapacidade(), r.getLocalizacao(),
                      r.getEhLaboratorio()
                    });
        }
    }

    private void navegarPara(int index) {
        if (listaRecursos != null && index >= 0 &&
                index < listaRecursos.size()) {
            indiceAtual = index;
            Recurso r = listaRecursos.get(index);
            txtIdRecurso.setText(String.valueOf(r.getId()));
            txtNomeRecurso.setText(r.getNome() != null ? r.getNome() : "");
            txtCapacidade.setText(String.valueOf(r.getCapacidade()));
            txtLocalizacao.setText(r.getLocalizacao()!=null ? r.getLocalizacao() :"");
            txtEhLab.setText(Byte.toString(r.getEhLaboratorio()));
            tabRec.setRowSelectionInterval(index, index);
        } else
            JOptionPane.showMessageDialog(this, "Sem registros nesta posição!");
    }

    private void executarSalvar() {
        try {
            boolean ok = false;
            if (incluindo) {

                    String ehLabTexto = txtEhLab.getText().trim();
                    if (!ehLabTexto.equals("0") && !ehLabTexto.equals("1")) {
                        JOptionPane.showMessageDialog(this, "O valor de É laboratório deve ser 0 (Não) ou 1 (Sim)!");
                        txtEhLab.requestFocus();
                        return;
                    }
                        
                    byte ehLab = Byte.parseByte(ehLabTexto);

                ok = controller.incluir(
                        txtNomeRecurso.getText().trim(),
                        Integer.parseInt(txtCapacidade.getText().trim()),
                        txtLocalizacao.getText().trim(),
                        ehLab
                        
                );
                txtIdRecurso.setEnabled(true);
            }
            else if(editando){

                 String ehLabTexto = txtEhLab.getText().trim();
                    if (!ehLabTexto.equals("0") && !ehLabTexto.equals("1")) {
                        JOptionPane.showMessageDialog(this, "O valor de É laboratório deve ser 0 (Não) ou 1 (Sim)!");
                        txtEhLab.requestFocus();
                        return;
                    }
                        
                    byte ehLab = Byte.parseByte(ehLabTexto);
                    String idRec = txtIdRecurso.getText().trim();
                    String nomeRec = txtNomeRecurso.getText().trim();
                    String capacidade = txtCapacidade.getText().trim();
                    String localizacao = txtLocalizacao.getText().trim();

                ok = controller.alterar(
                        (!idRec.isEmpty()) ? Integer.parseInt(idRec) : null,
                        (!nomeRec.isEmpty()) ? nomeRec : null,
                        (!capacidade.isEmpty()) ? Integer.parseInt(capacidade) : null,
                        (!localizacao.isEmpty()) ? localizacao : null,
                        ehLab
                );
            }
            if (ok) {
                JOptionPane.showMessageDialog(this, "Operação bem sucedida!");
                incluindo = false;
                editando = false;
                carregarDados();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void iniciarInclusao() {
        limparCampos();
        iniciarBusca(false);
        editando = false;
        incluindo = true;
        lbMensagem.setText("Digite os dados do novo recurso.");
        txtNomeRecurso.requestFocus();
        txtIdRecurso.setEnabled(false);
    }

    private void iniciarEdicao() {
        limparCampos();
        iniciarBusca(false);
        editando = true;
        incluindo = false;
        lbMensagem.setText("Digite os dados do recurso que deseja alterar.");
        txtIdRecurso.requestFocus();
    }

    private void iniciarBusca(boolean valor) {
        limparCampos();
        incluindo = false;
        editando = false;
        lbMensagem.setText("Clique novamente em 'Buscar' para filtrar.");
        txtNomeRecurso.requestFocus();
        txtIdRecurso.setEnabled(!valor);
        txtCapacidade.setEnabled(!valor);
        txtLocalizacao.setEnabled(!valor);
        txtEhLab.setEnabled(!valor);
        modoBusca = valor;
    }

    private void executarExclusao() {
        try {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = controller.excluir(Integer.parseInt(txtIdRecurso.getText()));
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Exclusão bem sucedida!");
                    carregarDados();
                }
                else JOptionPane.showMessageDialog(this, "Erro na Exclusão!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void executarBusca() {
        try {
            if(modoBusca == false){
                return;
            }

            Recurso r = controller.buscar(txtNomeRecurso.getText());
            if (r == null) {
                JOptionPane.showMessageDialog(this, "Mensagem: Recurso não encontrado!");
                return;
            }
            
            iniciarBusca(false);
            txtIdRecurso.setText(String.valueOf(r.getId()));
            txtNomeRecurso.setText(r.getNome());
            txtCapacidade.setText(String.valueOf(r.getCapacidade()));
            txtLocalizacao.setText(r.getLocalizacao());
            txtEhLab.setText(Byte.toString(r.getEhLaboratorio()));
            lbMensagem.setText("Mensagem: Recurso encontrado.");
        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtIdRecurso.setText("");
        txtNomeRecurso.setText("");
        txtCapacidade.setText("");
        txtLocalizacao.setText("");
        txtEhLab.setText("");
        txtIdRecurso.requestFocus();

    }

}


