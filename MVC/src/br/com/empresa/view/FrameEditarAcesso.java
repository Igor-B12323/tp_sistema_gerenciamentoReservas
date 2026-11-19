package br.com.empresa.view;

import br.com.empresa.controller.AcessoController;
import br.com.empresa.controller.UsuarioController;
import br.com.empresa.model.Acesso;
import br.com.empresa.model.Usuario;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

public class FrameEditarAcesso extends JInternalFrame{
    private JTextField txtLogin, txtSenha, txtNovoLogin, txtNovaSenha;
    private JButton btnSalvar, btnCancelar;
    private AcessoController acessoController;
    private UsuarioController usuarioController;
    private Usuario usuarioLogado;
    private Acesso loginOriginal;


    public FrameEditarAcesso(Usuario userLogado){
        this.usuarioLogado = userLogado;
        this.acessoController = new AcessoController();
        this.usuarioController = new UsuarioController();
        
        try{
            this.loginOriginal = acessoController.buscar(usuarioLogado.getId());
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Erro ao buscar Acesso: "+ ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        initComponents();

    }

    private void initComponents(){
        setTitle("Manutenção de Acessos");
        setSize(420, 250);
        setResizable(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pnlCampos = new JPanel(new GridLayout(4, 2, 5, 10));
        pnlCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        pnlCampos.add(new JLabel("login:"));
        txtLogin = new JTextField();
        txtLogin.setEnabled(false);
        pnlCampos.add(txtLogin);

        pnlCampos.add(new JLabel("Senha:"));
        txtSenha = new JTextField();
        txtSenha.setEnabled(false);
        pnlCampos.add(txtSenha);

        pnlCampos.add(new JLabel("Novo login:"));
        txtNovoLogin = new JTextField();
        pnlCampos.add(txtNovoLogin);

        pnlCampos.add(new JLabel("Nova Senha:"));
        txtNovaSenha = new JTextField();
        pnlCampos.add(txtNovaSenha);

        add(pnlCampos, BorderLayout.CENTER);

        txtLogin.setText(loginOriginal.getLogin());
        txtSenha.setText(loginOriginal.getSenha());


        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Sair");

        pnlBotoes.add(btnSalvar);
        pnlBotoes.add(btnCancelar);
        add(pnlBotoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvar(){

        String login = txtNovoLogin.getText().trim();
        String senha = txtNovaSenha.getText().trim();

        String loginNovo = (!login.isEmpty()) ? login : txtLogin.getText();
        String senhaNova = (!senha.isEmpty()) ? senha : txtSenha.getText();
        Integer idAcesso = loginOriginal.getId();
        LocalDate dataCadastro = loginOriginal.getDataCadastro();

        try{
            acessoController.alterar(idAcesso, loginNovo, senhaNova, dataCadastro, usuarioLogado.getId());
            JOptionPane.showMessageDialog(this, "Acesso alterado com sucesso! ", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Erro ao alterar Acesso: "+ ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}


