package br.com.empresa.view;

import br.com.empresa.controller.UsuarioController;
import br.com.empresa.model.Usuario;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrameUsuarios extends JInternalFrame{
    
    private UsuarioController controller = null;
    private List<Usuario> listaUsuarios;
    private int indiceAtual = -1;
    private boolean incluindo = false;
    private boolean editando = false;
    private boolean modoBusca = false;

    private JToolBar tbBotoes;

    private JButton btnInicio, btnAnterior, btnProximo, btnFinal, btnBuscar,
            btnIncluir, btnEditar, btnSalvar, btnExcluir, btnCancelar, btnSair;

    private JTextField txtIdUser, txtNomeUser, txtCpf,
            txtDataNasc, txtCelular, txtEmail;

    private JTable tabUser;
    private DefaultTableModel tableModel;
    private JLabel lbMensagem;

    public FrameUsuarios() {
        this.controller = new UsuarioController();

        setTitle("Manutenção de Usuários");
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

        txtIdUser = new JTextField();
        txtNomeUser = new JTextField();
        txtCpf = new JTextField();
        txtDataNasc = new JTextField();
        txtCelular = new JTextField();
        txtEmail = new JTextField();

        lbMensagem = new JLabel("Mensagem: ");

        String[] colunas = {"Id Usuário", "Nome", "CPF", "Data Nasc.", "Celular", "Email"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabUser = new JTable(tableModel);
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
        pnlGrade.add(new JScrollPane(tabUser), BorderLayout.CENTER);

        JPanel pnlCampos = new JPanel(new GridLayout(6, 2));
        pnlCampos.add(new JLabel("Id Usuário:"));
        pnlCampos.add(txtIdUser);
        pnlCampos.add(new JLabel("Nome:"));
        pnlCampos.add(txtNomeUser);
        pnlCampos.add(new JLabel("CPF"));
        pnlCampos.add(txtCpf);
        pnlCampos.add(new JLabel("Data Nasc.:"));
        pnlCampos.add(txtDataNasc);
        pnlCampos.add(new JLabel("Celular:"));
        pnlCampos.add(txtCelular);
        pnlCampos.add(new JLabel("Email"));
        pnlCampos.add(txtEmail);

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
        btnFinal.addActionListener(e -> navegarPara(listaUsuarios != null ?
                listaUsuarios.size() - 1 : -1));

        btnSalvar.addActionListener(e -> executarSalvar());
        btnIncluir.addActionListener(e -> iniciarInclusao());
        btnEditar.addActionListener(e -> iniciarEdicao());
        btnExcluir.addActionListener(e -> executarExclusao());
        btnBuscar.addActionListener(e -> {  if (!modoBusca) {
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
            txtIdUser.setEnabled(true);
            listaUsuarios = controller.carregarTodos();
            atualizarTabela();
            if (!listaUsuarios.isEmpty()) {
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
        for (Usuario u : listaUsuarios) {
            tableModel.addRow(
                 new Object[]
                    {
                      u.getId(), u.getNome(),
                      u.getCpf(), u.getDataAniversario(),
                      u.getCelular(), u.getEmail()
                    });
        }
    }

    private void navegarPara(int index) {
        if (listaUsuarios != null && index >= 0 &&
                index < listaUsuarios.size()) {
            indiceAtual = index;
            Usuario u = listaUsuarios.get(index);
            txtIdUser.setText(String.valueOf(u.getId()));
            txtNomeUser.setText(u.getNome() != null ? u.getNome() : "");
            txtCpf.setText(u.getCpf()!=null ? u.getCpf() :"");
            txtDataNasc.setText(u.getDataAniversario() != null ?
                    u.getDataAniversario().toString() : "");
            txtCelular.setText(u.getCelular()!=null ? u.getCelular() :"");
            txtEmail.setText(u.getEmail()!=null ? u.getEmail() :"");
            tabUser.setRowSelectionInterval(index, index);
        } else
            JOptionPane.showMessageDialog(this, "Sem registros nesta posição!");
    }

    private void executarSalvar() {
        try {
            boolean ok = false;
            if (incluindo) {
                ok = controller.incluir(
                        txtNomeUser.getText().trim(),
                        txtCpf.getText().trim(),
                        LocalDate.parse(txtDataNasc.getText().trim()),
                        txtCelular.getText().trim(),
                        txtEmail.getText().trim()
                );
                txtIdUser.setEnabled(true);
            }
            else if(editando){
                String idUser = txtIdUser.getText().trim();
                String nome = txtNomeUser.getText().trim();
                String cpf = txtCpf.getText().trim();
                String dtNasc = txtDataNasc.getText().trim();
                String celular = txtCelular.getText().trim();
                String email = txtEmail.getText().trim();

                ok = controller.alterar(
                        (!idUser.isEmpty()) ? Integer.parseInt(idUser) : null,
                        (!nome.isEmpty()) ? nome : null,
                        (!cpf.isEmpty()) ? cpf : null,
                        (!dtNasc.isEmpty()) ? LocalDate.parse(dtNasc) : null,
                        (!celular.isEmpty()) ? celular : null,
                        (!email.isEmpty()) ? email : null
                );
                
            }
            if (ok) {
                JOptionPane.showMessageDialog(this, "Operação bem sucedida!");
                editando = false;
                incluindo = false;
                carregarDados();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void iniciarInclusao() {
        limparCampos();
        iniciarBusca(false);
        incluindo = true;
        editando = false;
        txtIdUser.setEnabled(true);
        lbMensagem.setText("Digite os dados do novo usuário.");
        txtNomeUser.requestFocus();
        txtIdUser.setEnabled(false);
    }

    private void iniciarEdicao() {
        limparCampos();
        iniciarBusca(false);
        editando = true;
        incluindo = false;
        txtIdUser.setEnabled(true);
        lbMensagem.setText("Digite os dados que deseja alterar usuário.");
        txtIdUser.requestFocus();
    }

    private void iniciarBusca(boolean valor) {
        limparCampos();
        incluindo = false;
        editando = false;
        lbMensagem.setText("Clique novamente em 'Buscar' para filtrar.");
        txtNomeUser.requestFocus();
        txtIdUser.setEnabled(!valor);
        txtCpf.setEnabled(!valor);
        txtDataNasc.setEnabled(!valor);
        txtCelular.setEnabled(!valor);
        txtEmail.setEnabled(!valor);
        modoBusca = valor;
    }

    private void executarExclusao() {
        try {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = controller.excluir(Integer.parseInt(txtIdUser.getText()));
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Exclusão bem sucedida!");
                    carregarDados();
                }
                else JOptionPane.showMessageDialog(this, "erro na Exclusão!");
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

            Usuario u = controller.buscar(txtNomeUser.getText());
            if (u == null) {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado!");
                return;
            }

            iniciarBusca(false);
            txtIdUser.setText(String.valueOf(u.getId()));
            txtNomeUser.setText(u.getNome());
            txtCpf.setText(u.getCpf());

            txtDataNasc.setText(u.getDataAniversario() != null ?
                    u.getDataAniversario().toString() : "");
                    
            txtCelular.setText(u.getCelular());
            txtEmail.setText(u.getEmail());
            lbMensagem.setText("Mensagem: Usuário encontrado.");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtIdUser.setText("");
        txtNomeUser.setText("");
        txtCpf.setText("");
        txtDataNasc.setText("");
        txtCelular.setText("");
        txtEmail.setText("");
        txtIdUser.requestFocus();

    }

}

