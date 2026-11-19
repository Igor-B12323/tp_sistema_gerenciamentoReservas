package br.com.empresa.view;

import br.com.empresa.controller.AcessoController;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrameAuditoria extends JInternalFrame{
    
    private AcessoController controller = null;
    private List<Object[]> listaAcessos;
    private int indiceAtual = -1;
    private DateTimeFormatter dataHoraFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private JToolBar tbBotoes;

    private JButton btnInicio, btnAnterior, btnProximo, btnFinal, btnBuscar,
                    btnCancelar, btnSair;

    private JTextField txtIdAuditoria, txtDataAcesso, txtNomeUser,
                       txtLoginUser, txtIdUser;

    private JTable tabAuditoria;
    private DefaultTableModel tableModel;
    private JLabel lbMensagem;

    public FrameAuditoria() {
        this.controller = new AcessoController();

        setTitle("Auditoria Acessos");
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
        btnCancelar = criarBotao("Cancelar", "/resources/undo.png", 65, 45);
        btnSair = criarBotao("Sair", "/resources/abort.png", 65, 45);

        txtIdAuditoria = new JTextField();
        txtNomeUser = new JTextField();
        txtLoginUser = new JTextField();
        txtDataAcesso = new JTextField();
        txtIdUser = new JTextField();

        txtIdAuditoria.setEditable(false);
        txtDataAcesso.setEditable(false);
        txtNomeUser.setEditable(false);
        txtLoginUser.setEditable(false);

        lbMensagem = new JLabel("Mensagem: ");

        String[] colunas = {"Id Auditoria", "Data Acesso", "Login", "Id User", "Nome"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabAuditoria = new JTable(tableModel);
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
        tbBotoes.add(btnCancelar);
        tbBotoes.add(btnSair);

        tbBotoes.setRollover(true);

        JPanel pnlGrade = new JPanel(new BorderLayout());
        pnlGrade.add(new JScrollPane(tabAuditoria), BorderLayout.CENTER);

        JPanel pnlCampos = new JPanel(new GridLayout(5, 2));
        pnlCampos.add(new JLabel("Id Auditoria:"));
        pnlCampos.add(txtIdAuditoria);
        pnlCampos.add(new JLabel("Nome:"));
        pnlCampos.add(txtNomeUser);
        pnlCampos.add(new JLabel("Login"));
        pnlCampos.add(txtLoginUser);
        pnlCampos.add(new JLabel("Data Acesso:"));
        pnlCampos.add(txtDataAcesso);
        pnlCampos.add(new JLabel("Id Usuário:"));
        pnlCampos.add(txtIdUser);

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
        btnFinal.addActionListener(e -> navegarPara(listaAcessos != null ?
                                                    listaAcessos.size() - 1 : -1));
        btnBuscar.addActionListener(e -> executarBusca());
        btnCancelar.addActionListener(e -> carregarDados());
        btnSair.addActionListener(e -> dispose());
    }

    private void carregarDados() {
        try {
            String textoId = txtIdUser.getText().trim();
            Integer idUser = !textoId.isEmpty() ? Integer.parseInt(textoId) : null;
            listaAcessos = controller.historicoAcessos(idUser);
            atualizarTabela();
            if (!listaAcessos.isEmpty()) {
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
        if (listaAcessos != null) {
            for (Object[] row : listaAcessos) {
                Integer idAuditoria = (Integer) row[0];
                LocalDateTime dataHora = (LocalDateTime) row[1];
                String dataFormatada = (dataHora != null) ? dataHora.format(dataHoraFormatter) : "";
                String login = (String) row[2];
                Integer idUsuario = (Integer) row[3];
                String nomeUsuario = (String) row[4];

                tableModel.addRow(new Object[]{
                        idAuditoria,
                        dataFormatada,
                        login,
                        idUsuario,
                        nomeUsuario
                });
            }
        }
    }

    private void navegarPara(int index) {
        if (listaAcessos != null && index >= 0 &&
                index < listaAcessos.size()) {

            indiceAtual = index;
        
            Object[] row = listaAcessos.get(index);
            txtIdAuditoria.setText(String.valueOf(row[0]));
            txtNomeUser.setText(String.valueOf(row[4]));
            txtLoginUser.setText(String.valueOf(row[2]));

            LocalDateTime dataHora = (LocalDateTime) row[1];
            String dataFormatada = (dataHora != null) ? dataHora.format(dataHoraFormatter) : "";

            txtDataAcesso.setText(dataFormatada);
            txtIdUser.setText(String.valueOf(row[3]));
            tabAuditoria.setRowSelectionInterval(index, index);
        } else
            JOptionPane.showMessageDialog(this, "Sem registros nesta posição!");
    }

    private void executarBusca() {
        try {
            String idTexto = txtIdUser.getText().trim();
            Integer idUser = !idTexto.isEmpty() ? Integer.parseInt(idTexto) : null;
            
            listaAcessos = controller.historicoAcessos(idUser);

            if (listaAcessos != null && !listaAcessos.isEmpty()) {
                atualizarTabela();

                Object[] linha = listaAcessos.get(0);

                LocalDateTime dataHora = (LocalDateTime) linha[1];
                String dataFormatada = (dataHora != null) ? dataHora.format(dataHoraFormatter) : "";

                txtIdAuditoria.setText(String.valueOf(linha[0]));
                txtDataAcesso.setText(dataFormatada);
                txtLoginUser.setText(String.valueOf(linha[2]));
                txtIdUser.setText(String.valueOf(linha[3]));
                txtNomeUser.setText(String.valueOf(linha[4]));

                lbMensagem.setText("Busca concluída. Registros encontrados: " + listaAcessos.size());
            } else {
                tableModel.setRowCount(0);
                limparCampos();
                JOptionPane.showMessageDialog(this, "Nenhum histórico encontrado!");
                lbMensagem.setText("Mensagem: Nenhum registro encontrado.");
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "ID deve ser inteiro > 0!", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtIdUser.requestFocus();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtIdAuditoria.setText("");
        txtNomeUser.setText("");
        txtLoginUser.setText("");
        txtDataAcesso.setText("");
        txtIdUser.setText("");
        txtIdUser.requestFocus();

    }

}
