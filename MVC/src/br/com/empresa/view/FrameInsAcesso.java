package br.com.empresa.view;

import br.com.empresa.controller.AcessoController;
import br.com.empresa.controller.UsuarioController;
import br.com.empresa.model.Usuario;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class FrameInsAcesso extends JDialog{
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnSalvar, btnCancelar;
    private AcessoController acessoController;
    private UsuarioController usuarioController;
    private JComboBox<Usuario> cbUsuarios;


    public FrameInsAcesso(Window owner) {
        super(owner, "Criar Novo Acesso / Login", ModalityType.APPLICATION_MODAL);
        this.acessoController = new AcessoController();
        this.usuarioController = new UsuarioController();
        initComponents();
        carregarUsuarios();
    }

    private void initComponents(){
        setSize(420, 250);
        setLocationRelativeTo(getParent());
        setResizable(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pnlCampos = new JPanel(new GridLayout(3, 2, 5, 10));
        pnlCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        pnlCampos.add(new JLabel("Novo login:"));
        txtLogin = new JTextField();
        pnlCampos.add(txtLogin);

        pnlCampos.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        pnlCampos.add(txtSenha);

        pnlCampos.add(new JLabel("Selecione o usuário:"));
        cbUsuarios = new JComboBox<>();
        pnlCampos.add(cbUsuarios);

        add(pnlCampos, BorderLayout.CENTER);

        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Sair");

        pnlBotoes.add(btnSalvar);
        pnlBotoes.add(btnCancelar);
        add(pnlBotoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void carregarUsuarios(){
        try{
            List<Usuario> listaUsuarios = usuarioController.carregarTodos();

            cbUsuarios.removeAllItems();
            for (Usuario usuario : listaUsuarios) {
                cbUsuarios.addItem(usuario);
            }

        }catch(Exception ex){
             JOptionPane.showMessageDialog(this, "Erro ao renderizar usuários" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvar(){
        Usuario usuarioSelecionado = (Usuario) cbUsuarios.getSelectedItem();
        
        if(usuarioSelecionado == null){
            JOptionPane.showMessageDialog(this, "Nenhum usuário selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if(login.isEmpty() || senha.isEmpty()){
            JOptionPane.showMessageDialog(this, "Informe login e senha.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try{
            acessoController.incluir(login, senha, usuarioSelecionado.getId());
            JOptionPane.showMessageDialog(this, "Acesso criado com sucesso! ", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Erro ao criar Acesso: "+ ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}


