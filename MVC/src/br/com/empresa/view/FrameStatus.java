package br.com.empresa.view;

import br.com.empresa.controller.StatusReservaController;
import br.com.empresa.model.StatusReserva;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class FrameStatus extends JInternalFrame{
    
    private StatusReservaController controller = null;
    private List<StatusReserva> listaStatus;

    private JToolBar tbBotoes;

    private JButton btnSair;

    private JTable tabStatus;
    private DefaultTableModel tableModel;
    private JLabel lbMensagem;

    public FrameStatus() {
        this.controller = new StatusReservaController();

        setTitle("Descrição Status");
        setSize(510, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        carregarDados();
    }

    private void inicializarComponentes() {
        tbBotoes = new JToolBar();

        btnSair = criarBotao("Sair", "/resources/abort.png", 65, 40);

        lbMensagem = new JLabel("Mensagem: ");

        String[] colunas = {"Id Status", "Nome Status", "Descrição"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabStatus = new JTable(tableModel);
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
        tbBotoes.add(btnSair);

        tbBotoes.setRollover(true);

        JPanel pnlGrade = new JPanel(new BorderLayout());
        TableColumnModel columnModel = tabStatus.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(70);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(340);
        pnlGrade.add(new JScrollPane(tabStatus), BorderLayout.CENTER);

        JPanel pnlMensagem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlMensagem.add(lbMensagem);

        Container cntForm = getContentPane();
        cntForm.setLayout(new BorderLayout());
        cntForm.add(tbBotoes , BorderLayout.NORTH);
        cntForm.add(pnlGrade , BorderLayout.CENTER);
        cntForm.add(pnlMensagem , BorderLayout.SOUTH);
    }

    private void configurarEventos() {

        btnSair.addActionListener(e -> dispose());
    }

    private void carregarDados() {
        try {
            listaStatus = controller.listarTodos();
            atualizarTabela();
            lbMensagem.setText("Mensagem: Dados carregados com sucesso.");
        }
        catch (Exception ex) {
            lbMensagem.setText("Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void atualizarTabela() {
       tableModel.setRowCount(0);
        for (StatusReserva s : listaStatus) {
            tableModel.addRow(
                 new Object[]
                    {
                      s.getId(), s.getNome(), s.getDescricao()
                    });
        }
    }
}
