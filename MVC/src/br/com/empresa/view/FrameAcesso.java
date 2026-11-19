package br.com.empresa.view;

import br.com.empresa.controller.AcessoController;
import br.com.empresa.model.Usuario;
import java.awt.*;
import javax.swing.*;

public class FrameAcesso extends JDialog{
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar, btnCancelar, btnNovoAcesso;
    private FramePrincipal framePrincipal;
    private AcessoController acessoController;


    public FrameAcesso(FramePrincipal parent) {
        super(parent, "Tela de Login", true);
        this.framePrincipal = parent;
        this.acessoController = new AcessoController();
        initComponents();
    }

    private void initComponents(){
        setSize(380, 200);
        setLocationRelativeTo(getParent());
        setResizable(true);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pnlCampos = new JPanel(new GridLayout(2, 2, 5, 10));
        pnlCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        pnlCampos.add(new JLabel("Login:"));
        txtLogin = new JTextField();
        pnlCampos.add(txtLogin);

        pnlCampos.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        pnlCampos.add(txtSenha);

        add(pnlCampos, BorderLayout.CENTER);

        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEntrar = new JButton("Entrar");
        btnCancelar = new JButton("Sair");
        btnNovoAcesso = new JButton("Criar Login");


        pnlBotoes.add(btnNovoAcesso);
        pnlBotoes.add(btnEntrar);
        pnlBotoes.add(btnCancelar);
        add(pnlBotoes, BorderLayout.SOUTH);

        btnNovoAcesso.addActionListener(e -> {try {
                                                    FrameInsAcesso telaNovoAcesso = new FrameInsAcesso(this);
                                                    telaNovoAcesso.setVisible(true);
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                    JOptionPane.showMessageDialog(this, 
                                                        "Erro ao abrir janela de cadastro de login: " + ex.getMessage(), 
                                                        "Erro de Abertura", 
                                                        JOptionPane.ERROR_MESSAGE);
                                        }});
        btnEntrar.addActionListener(e -> realizarAutenticacao());
        btnCancelar.addActionListener(e -> System.exit(0));
    }

    private void realizarAutenticacao(){
        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if(login.isEmpty() || senha.isEmpty()){
            JOptionPane.showMessageDialog(this, "Informe login e senha.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try{
            Usuario user = acessoController.autenticarAcesso(login, senha);
            if (user != null){
                framePrincipal.sucessoLogin(user);
                this.dispose();
            }
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Erro ao autenticar: " + ex.getMessage(), "Falha no Login", JOptionPane.ERROR_MESSAGE);
        }
    }
        
}


