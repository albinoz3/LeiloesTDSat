package Classes;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;



public class conectaDAO {
    
    private Connection conn;
    
    public Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11?useSSL=false", "root", "Smeagol!2580");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    public boolean conectar(){
        Connection conn = null;
        
        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11?useSSL=false", "root", "Smeagol!2580");
        return true; 
            
        } catch (ClassNotFoundException | SQLException ex){
            System.out.println("Erro ao conectar: " + ex.getMessage());
        return false;
        }
     
    }public void desconectar(){
        try{
            if(conn != null && !conn.isClosed()){
            conn.close();
            }
        }catch(SQLException ex){
            System.out.println("Erro ao desconectar:");
        }
    }
}
