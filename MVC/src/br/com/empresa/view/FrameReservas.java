package br.com.empresa.view;

import br.com.empresa.controller.RecursoController;
import br.com.empresa.controller.ReservaController;
import br.com.empresa.controller.StatusReservaController;
import br.com.empresa.controller.UsuarioController;
import br.com.empresa.model.Recurso;
import br.com.empresa.model.ReservaDTO;
import br.com.empresa.model.StatusReserva;
import br.com.empresa.model.Usuario;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrameReservas extends JInternalFrame{
    
    private ReservaController controller = null;
    private UsuarioController usuarioController = null;
    private RecursoController recursoController = null;
    private StatusReservaController statusController = null;
    private List<ReservaDTO> listaReservas;
    private int indiceAtual = -1;
    private boolean incluindo = false;
    private boolean editando = false;
    private boolean modoBusca = false;

    private CardLayout cardLayout;
    private JPanel pnlCards;

    private JTextField txtDataBusca;
    private JTextField txtHoraBusca;

    private JToolBar tbBotoes;

    private JButton btnInicio, btnAnterior, btnProximo, btnFinal, btnBuscar,
            btnIncluir, btnEditar, btnSalvar, btnExcluir, btnCancelar, btnSair;

    private JButton btnFiltrar = new JButton("Buscar");

    private JTextField txtIdReserva, txtDtInicio,  txtDtFim,
                       txtHrInicio, txtHrFim;

    private JComboBox<Recurso> cbRecursos;
    private JComboBox<StatusReserva> cbStatus;
    private JComboBox<Usuario> cbUsuarios;
    private JComboBox<Recurso> cbRecursosBusca;
    private JComboBox<StatusReserva> cbStatusBusca;
    private JComboBox<Usuario> cbUsuariosBusca;


    private JTable tabReserva;
    private DefaultTableModel tableModel;
    private JLabel lbMensagem;

    private Usuario usuarioLogado;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public FrameReservas(Usuario userLogado) {
        this.usuarioLogado = userLogado;
        this.controller = new ReservaController();
        this.usuarioController = new UsuarioController();
        this.recursoController = new RecursoController();
        this.statusController = new StatusReservaController();

        setTitle("Manutenção de Reservas");
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

        txtIdReserva = new JTextField();
        cbRecursos = new JComboBox<>();
        cbStatus = new JComboBox<>();
        cbUsuarios = new JComboBox<>();
        cbRecursosBusca = new JComboBox<>(cbRecursos.getModel());
        cbStatusBusca = new JComboBox<>(cbStatus.getModel());
        cbUsuariosBusca = new JComboBox<>(cbUsuarios.getModel());
        txtDtInicio = new JTextField();
        txtHrInicio = new JTextField();
        txtDtFim = new JTextField();
        txtHrFim = new JTextField();

        lbMensagem = new JLabel("Mensagem: ");

        String[] colunas = {"Id Reserva", "Recurso", "Capacidade", "Localização", "Data Inicial",
                            "Data Final", "Hora Inicial", "Hora Final", "Status", "Usuário", "Email"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabReserva = new JTable(tableModel);
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
        tbBotoes.addSeparator();
        tbBotoes.add(btnExcluir);
        tbBotoes.add(btnCancelar);
        tbBotoes.add(btnSair);
        tbBotoes.setRollover(true);

        JPanel pnlGrade = new JPanel(new BorderLayout());
        pnlGrade.setPreferredSize(new Dimension(700, 0));
        pnlGrade.add(new JScrollPane(tabReserva), BorderLayout.CENTER);

        JPanel pnlGridCadastro = new JPanel(new GridLayout(7, 2, 5, 5));
        pnlGridCadastro.add(new JLabel("Id Reserva:"));
        pnlGridCadastro.add(txtIdReserva);
        pnlGridCadastro.add(new JLabel("Recurso:"));
        pnlGridCadastro.add(cbRecursos);
        pnlGridCadastro.add(new JLabel("Status:"));
        pnlGridCadastro.add(cbStatus);
        pnlGridCadastro.add(new JLabel("Data Inicial:"));
        pnlGridCadastro.add(txtDtInicio);
        pnlGridCadastro.add(new JLabel("Data Final:"));
        pnlGridCadastro.add(txtDtFim);
        pnlGridCadastro.add(new JLabel("Hora Inicial:"));
        pnlGridCadastro.add(txtHrInicio);
        pnlGridCadastro.add(new JLabel("Hora Final:"));
        pnlGridCadastro.add(txtHrFim);

        txtDataBusca = new JTextField();
        txtHoraBusca = new JTextField();

        JPanel pnlGridBusca = new JPanel(new GridLayout(6, 2, 5, 5));
        pnlGridBusca.add(new JLabel("Recurso:"));
        pnlGridBusca.add(cbRecursosBusca);
        pnlGridBusca.add(new JLabel("Data da Reserva:"));
        pnlGridBusca.add(txtDataBusca);
        pnlGridBusca.add(new JLabel("Hora da Reserva:"));
        pnlGridBusca.add(txtHoraBusca);
        pnlGridBusca.add(new JLabel("Status:"));
        pnlGridBusca.add(cbStatusBusca);
        pnlGridBusca.add(new JLabel("Usuário:"));
        pnlGridBusca.add(cbUsuariosBusca);
        pnlGridBusca.add(new JLabel(""));
        pnlGridBusca.add(btnFiltrar);

        cardLayout = new CardLayout();
        pnlCards = new JPanel(cardLayout);
        pnlCards.add(pnlGridCadastro, "CADASTRO");
        pnlCards.add(pnlGridBusca, "BUSCA");

        JPanel pnlMensagem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlMensagem.add(lbMensagem);

        Container cntForm = getContentPane();
        cntForm.setLayout(new BorderLayout());
        cntForm.add(tbBotoes, BorderLayout.NORTH);
        cntForm.add(pnlGrade, BorderLayout.WEST);
        cntForm.add(pnlCards, BorderLayout.CENTER);
        cntForm.add(pnlMensagem, BorderLayout.SOUTH);
    }

    private void carregarUsuarios(){
        try{
            List<Usuario> listaUsuarios = usuarioController.carregarTodos();

            cbUsuarios.removeAllItems();
            cbUsuarios.addItem(null);
            for (Usuario usuario : listaUsuarios) {
                cbUsuarios.addItem(usuario);
            }

        }catch(Exception ex){
             JOptionPane.showMessageDialog(this, "Erro ao renderizar usuários" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarRecursos(){
        try{
            List<Recurso> listaRecursos = recursoController.carregarTodos();

            cbRecursos.removeAllItems();
            cbRecursos.addItem(null);
            for (Recurso recurso : listaRecursos) {
                cbRecursos.addItem(recurso);
            }

        }catch(Exception ex){
             JOptionPane.showMessageDialog(this, "Erro ao renderizar recursos" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarStatus(){
        try{
            List<StatusReserva> listaStatus = statusController.listarTodos();

            cbStatus.removeAllItems();
            cbStatus.addItem(null);
            for (StatusReserva status : listaStatus) {
                cbStatus.addItem(status);
            }

        }catch(Exception ex){
             JOptionPane.showMessageDialog(this, "Erro ao renderizar status" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarEventos() {
  
        btnInicio.addActionListener(e -> navegarPara(0));
        btnAnterior.addActionListener(e -> navegarPara(indiceAtual - 1));
        btnProximo.addActionListener(e -> navegarPara(indiceAtual + 1));
        btnFinal.addActionListener(e -> navegarPara(listaReservas != null ?
                listaReservas.size() - 1 : -1));

        btnSalvar.addActionListener(e -> executarSalvar());
        btnIncluir.addActionListener(e -> iniciarInclusao());
        btnEditar.addActionListener(e -> iniciarEdicao());
        btnExcluir.addActionListener(e -> executarExclusao());
        btnBuscar.addActionListener(e -> iniciarBusca());
        btnCancelar.addActionListener(e -> carregarDados());
        btnSair.addActionListener(e -> dispose());

        btnFiltrar.addActionListener(e -> executarBusca());
    }

    private void carregarDados() {
        try {
            editando = false;
            incluindo = false;
            txtIdReserva.setEnabled(true);

            carregarRecursos();
            carregarStatus();
            carregarUsuarios();

            listaReservas = controller.listarParaTabela();
            atualizarTabela();
            if (!listaReservas.isEmpty()) {
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
        for (ReservaDTO r : listaReservas) {
            tableModel.addRow(
                 new Object[]
                    {
                      r.getId(), r.getNomeRecurso(), r.getCapacidade(),
                      r.getLocalizacao(), r.getDataInicial(), r.getDataFinal(),
                      r.getHoraInicial(), r.getHoraFinal(), r.getNomeStatusReserva(),
                      r.getNomeUsuario(), r.getEmail()
                    });
        }
    }

    private void navegarPara(int index) {
        if (listaReservas != null && index >= 0 && index < listaReservas.size()) {
            indiceAtual = index;
            ReservaDTO r = listaReservas.get(index);
            txtIdReserva.setText(String.valueOf(r.getId()));
            txtDtInicio.setText(String.valueOf(r.getDataInicial()));
            txtDtFim.setText(String.valueOf(r.getDataFinal()));
            txtHrInicio.setText(String.valueOf(r.getHoraInicial()));
            txtHrFim.setText(String.valueOf(r.getHoraFinal()));
            cbStatus.setSelectedItem(r.getNomeStatusReserva());
            cbRecursos.setSelectedItem(r.getNomeRecurso());
            tabReserva.setRowSelectionInterval(index, index);
        } else
            JOptionPane.showMessageDialog(this, "Sem registros nesta posição!");
    }

    private void executarSalvar() {
        try {
            if(!incluindo && !editando){
                JOptionPane.showMessageDialog(this, "Clique em Incluir ou Editar antes de salvar!");
                return; 
            }

            Recurso recursoSel = (Recurso) cbRecursos.getSelectedItem();
            StatusReserva statusSel = (StatusReserva) cbStatus.getSelectedItem();

            if (recursoSel == null || statusSel == null || usuarioLogado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um recurso, um status e verifique se está logado!");
                return;
            }

            int idRecurso = recursoSel.getId();
            int idStatusReserva = statusSel.getId();
            int idUsuario = usuarioLogado.getId();

            boolean ok = false;
            if (incluindo) {
                ok = controller.incluir(
                        LocalDate.parse(txtDtInicio.getText().trim()),
                        LocalDate.parse(txtDtFim.getText().trim()),
                        LocalTime.parse(txtHrInicio.getText().trim(), formatter),
                        LocalTime.parse(txtHrFim.getText().trim(), formatter),
                        idRecurso,
                        idUsuario,
                        idStatusReserva
                );
                txtIdReserva.setEnabled(true);
            }
            else if(editando){

                String idReserva = txtIdReserva.getText().trim();
                String dtInicio = txtDtInicio.getText().trim();
                String dtFim = txtDtFim.getText().trim();
                String hrInicio = txtHrInicio.getText().trim();
                String hrFim = txtHrFim.getText().trim();

                ok = controller.alterar(
                        (!idReserva.isEmpty()) ? Integer.parseInt(idReserva) : null,
                        (!dtInicio.isEmpty()) ? LocalDate.parse(dtInicio) : null,
                        (!dtFim.isEmpty()) ? LocalDate.parse(dtFim) : null,
                        (!hrInicio.isEmpty()) ? LocalTime.parse(hrInicio, formatter) : null,
                        (!hrFim.isEmpty()) ? LocalTime.parse(hrFim, formatter) : null,
                        idRecurso,
                        idUsuario,
                        idStatusReserva
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
        incluindo = true;
        editando = false;
        txtIdReserva.setEnabled(true);
        lbMensagem.setText("Digite os dados da nova reserva.");
        txtDtInicio.requestFocus();
        txtIdReserva.setEnabled(false);
    }

    private void iniciarEdicao() {
        limparCampos();
        editando = true;
        incluindo = false;
        txtIdReserva.setEnabled(true);
        lbMensagem.setText("Digite os dados que deseja alterar.");
        txtIdReserva.requestFocus();
    }

    private void iniciarBusca(){
        if(modoBusca){
            modoBusca = false;
            cardLayout.show(pnlCards, "CADASTRO");
            carregarDados();
            txtIdReserva.requestFocus();
        } else{
            modoBusca = true;
            cardLayout.show(pnlCards, "BUSCA");
            lbMensagem.setText("'Buscar' para filtrar.");
            txtDataBusca.requestFocus();
        }
    }

    private void executarExclusao() {
        try {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = controller.excluir(Integer.parseInt(txtIdReserva.getText()));
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
        if (!modoBusca) {
            return;
        }
        try {
            LocalDate data = !txtDataBusca.getText().trim().isEmpty() 
                    ? LocalDate.parse(txtDataBusca.getText().trim()) : null;
            LocalTime hora = !txtHoraBusca.getText().trim().isEmpty() 
                    ? LocalTime.parse(txtHoraBusca.getText().trim(), formatter) : null;

            Recurso rec = (Recurso) cbRecursosBusca.getSelectedItem();
            Integer idRecurso = (rec != null) ? rec.getId() : null;
            String nomeRec = (rec != null) ? rec.getNome() : null;


            Usuario userSelec = (Usuario) cbUsuariosBusca.getSelectedItem();
            StatusReserva statusSelec =  (StatusReserva) cbStatusBusca.getSelectedItem();
            String nomeUser = (userSelec != null) ? userSelec.getNome() : null;
            String status = (statusSelec != null) ? statusSelec.getNome() : null;

            listaReservas = controller.consultarReservas(idRecurso, nomeRec, data, hora, nomeUser, status);
            atualizarTabela();
            lbMensagem.setText("Registros encontrados: " + listaReservas.size());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro na busca: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtIdReserva.setText("");
        txtDtInicio.setText("");
        txtHrInicio.setText("");
        txtDtFim.setText("");
        txtHrFim.setText("");
        cbRecursos.setSelectedItem(null);
        cbUsuarios.setSelectedItem(null);
        cbStatus.setSelectedItem(null);
        cbRecursosBusca.setSelectedItem(null);
        cbUsuariosBusca.setSelectedItem(null);
        cbStatusBusca.setSelectedItem(null);
        txtIdReserva.requestFocus();
    }

}

