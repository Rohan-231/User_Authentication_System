import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


import javax.swing.*;
public class loginForm extends JFrame {
    final private Font mainFont = new Font("Segoe print",Font.BOLD, 18);
    JTextField jtext;
    JPasswordField jpass;
    public void initialize(){

        JLabel lbloginForm = new JLabel("Login Form", SwingConstants.CENTER);
        lbloginForm.setFont(mainFont);

        JLabel lbemail = new JLabel("Email");
        lbemail.setFont(mainFont);

        jtext = new JTextField();
        jtext.setFont(mainFont);
        
        JLabel jpassword = new JLabel("Password");
        jpassword.setFont(mainFont);

        jpass = new JPasswordField();
        jpass.setFont(mainFont);

        JPanel formpanel = new JPanel();
        formpanel.setLayout(new GridLayout(0, 1, 10,10));
        formpanel.add(lbloginForm);
        formpanel.add(lbemail);
        formpanel.add(jtext);
        formpanel.add(jpassword);
        formpanel.add(jpass);

        JButton btnlogin = new JButton("Login");
        btnlogin.setFont(mainFont);
        btnlogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               
                String email = jtext.getText();
                String password = String.valueOf(jpass.getPassword());

                User user = getAuthenticatedUser(email,password);

                if(user!=null){
                    MainFrame mainframe = new MainFrame();
                    mainframe.initialize(user);
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(loginForm.this, "Email or Password Invakid", "Try Again", JOptionPane.ERROR_MESSAGE);
                }
            }

           
        });

        JButton jbut = new JButton("Cancel");
        jbut.setFont(mainFont);

        jbut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
           
                    dispose();
            }
            
        });

        JPanel jbutpanel = new JPanel();
        jbutpanel.setLayout(new GridLayout(1, 2, 10, 0));
        jbutpanel.add(btnlogin);
        jbutpanel.add(jbut);
        
        add(formpanel,BorderLayout.NORTH);
        add(jbutpanel,BorderLayout.SOUTH);

        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400,500);
        setMinimumSize(new Dimension(350,450));
        setLocationRelativeTo(null);
        setVisible(true);
    }
     private User getAuthenticatedUser(String email, String password) {
                User user = null;
                final String DB_URL = "jdbc:mysql://localhost/mystore?serverTimezone=UTC";
                final String USERNAME = "root";
                final String PASSWORD = "";
                try{
                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    String sql = "SELECT * FROM users WHERE email =? AND password=?";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if(resultSet.next()){
                        user = new User();
                        user.name = resultSet.getString("name");
                        user.email = resultSet.getString("email");
                        user.phone = resultSet.getString("phone");
                        user.address = resultSet.getString("address");
                        user.password = resultSet.getString("password");
                    }
                    preparedStatement.close();
                    conn.close();
                }
                catch(Exception e){
                    System.out.println("Database Connection failed");
                }
                return user;
            }
            public static void main(String[] args) {
                loginForm lg = new loginForm();
                lg.initialize();
            }
            
}
