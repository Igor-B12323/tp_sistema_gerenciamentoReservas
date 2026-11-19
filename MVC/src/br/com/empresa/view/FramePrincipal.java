package br.com.empresa.view;

import br.com.empresa.model.Usuario;
import java.awt.*;
import javax.swing.*;

public class FramePrincipal extends JFrame {

    private Usuario usuarioLogado;
    
    private JDesktopPane panPrincipal; 
    
    private JPanel pnlBotoes;
    static JLabel statusBar1;

    private JButton btnUsuarios, btnRecursos, btnStatus,
                    btnReservas, btnConsultaAcessos, btnEditarAcesso, btnSair;

    public FramePrincipal(){
        initComponents();
        setBotoesAtivos(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                solicitarLogin();
            }
        });
    }

    private void initComponents(){
        setTitle("Sistema de Reserva de Laboratórios e Salas - Painel Principal");

        btnUsuarios = criarBotaoComIcone("Usuários", "src/resources/Add.png");
        btnRecursos = criarBotaoComIcone("Recursos", "src/resources/COPY.png");
        btnStatus = criarBotaoComIcone("Status", "src/resources/about.png");
        btnReservas = criarBotaoComIcone("Reservar", "src/resources/WINNEXT.png");
        btnConsultaAcessos = criarBotaoComIcone("Auditoria", "src/resources/Oeil2.png");
        btnEditarAcesso = criarBotaoComIcone("Editar Acesso", "src/resources/user.png");
        btnSair = criarBotaoComIcone("Sair", "src/resources/abort.png");

        pnlBotoes = new JPanel();
        pnlBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));

        pnlBotoes.add(btnUsuarios);
        pnlBotoes.add(btnRecursos);
        pnlBotoes.add(btnStatus);
        pnlBotoes.add(btnReservas);
        pnlBotoes.add(btnConsultaAcessos);
        pnlBotoes.add(btnEditarAcesso);
        pnlBotoes.add(btnSair);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Container cntForm = getContentPane();
        cntForm.setLayout(new BorderLayout());
        cntForm.add(pnlBotoes, BorderLayout.NORTH);

        panPrincipal = new JDesktopPane();
        panPrincipal.setBackground(new Color(235, 240, 245));
        cntForm.add(panPrincipal, BorderLayout.CENTER);

        JPanel pnlRodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlRodape.setBorder(BorderFactory.createEtchedBorder());
        
        String nomeUsuario = (usuarioLogado != null && usuarioLogado.getNome() != null) 
                             ? usuarioLogado.getNome() 
                             : "Utilizador Autenticado";
        
        statusBar1 = new JLabel(" Usuário conectado: " + nomeUsuario);
        statusBar1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pnlRodape.add(statusBar1);
        cntForm.add(pnlRodape, BorderLayout.SOUTH);

       
        btnUsuarios.addActionListener(e -> abrirJanela(new FrameUsuarios()));
        btnRecursos.addActionListener(e -> abrirJanela(new FrameRecursos()));
        btnStatus.addActionListener(e -> abrirJanela(new FrameStatus()));
        btnConsultaAcessos.addActionListener(e -> abrirJanela(new FrameAuditoria()));
        btnEditarAcesso.addActionListener(e -> abrirJanela(new FrameEditarAcesso(usuarioLogado)));
        btnReservas.addActionListener(e -> abrirJanela(new FrameReservas(usuarioLogado)));
        btnSair.addActionListener(e -> System.exit(0));
        
    }

    private void setBotoesAtivos(boolean ativo) {
        btnUsuarios.setEnabled(ativo);
        btnRecursos.setEnabled(ativo);
        btnStatus.setEnabled(ativo);
        btnReservas.setEnabled(ativo);
        btnConsultaAcessos.setEnabled(ativo);
        btnEditarAcesso.setEnabled(ativo);
    }


    private void solicitarLogin(){
        try {
        FrameAcesso frameAcesso = new FrameAcesso(this);
        frameAcesso.setVisible(true);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Erro ao carregar ecra de login: " + ex.getMessage(), 
            "Erro de Inicialização", 
            JOptionPane.ERROR_MESSAGE);
    }
    }

    
    public void sucessoLogin(Usuario usuario){
        this.usuarioLogado = usuario;
        statusBar1.setText(" Usuário conectado: " + usuario.getNome());
        setBotoesAtivos(true);
    }


    private JButton criarBotaoComIcone(String texto, String caminhoIcone) {
        JButton btn;
        try {
            btn = new JButton(texto, new ImageIcon(caminhoIcone));
        } catch (Exception e) {
            btn = new JButton(texto);
        }
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        return btn;
    }


    private void abrirJanela(JInternalFrame frame) {
        panPrincipal.add(frame);
        frame.setVisible(true);
    }
}