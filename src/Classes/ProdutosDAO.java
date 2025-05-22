package Classes;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.*;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement st;
    ResultSet resultset;
    conectaDAO conexao = new conectaDAO();
    private static final String TABELA = "produtos";
    //Orgizando as conexão
    public boolean conectar() {
        conn = conexao.connectDB();
        return( conn != null);
    }
    
    public void cadastrarProduto(ProdutosDTO produto) {
        int status = salvar(produto);
        if (status <= 0) {
            System.out.println("Falha ao cadastrar o produto.");
        }
    }
    
    public int salvar(ProdutosDTO produto) {
        int status = 0;
        if (conectar()) {
            try {
                String sql = "INSERT INTO " + TABELA + " (nome, valor, status) VALUES (?, ?, ?)";
                st = conn.prepareStatement(sql);
                st.setString(1, produto.getNome());
                st.setDouble(2, produto.getValor());
                st.setString(3, produto.getStatus());

                status = st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Erro ao salvar produto: " + ex.getMessage());
            } finally {
                desconectar();
            }
        }
        return status;
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        
        if (conectar()) {
            try {
                String sql = "SELECT * FROM " + TABELA;
                st = conn.prepareStatement(sql);
                resultset = st.executeQuery();
                
                while (resultset.next()) {
                    ProdutosDTO produto = new ProdutosDTO();
                    produto.setId(resultset.getInt("id"));
                    produto.setNome(resultset.getString("nome"));
                    produto.setValor(resultset.getDouble("valor"));
                    produto.setStatus(resultset.getString("status"));
                    
                    listagem.add(produto);
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao listar produtos: " + ex.getMessage());
            } finally {
                desconectar();
            }
        }
        
        return listagem;
    }
    
    public boolean atualizar(ProdutosDTO produto) {
        boolean atualizado = false;
        if (conectar()) {
            try {
                String sql = "UPDATE " + TABELA + " SET nome = ?, valor = ?, status = ? WHERE id = ?";
                st = conn.prepareStatement(sql);
                st.setString(1, produto.getNome());
                st.setDouble(2, produto.getValor());
                st.setString(3, produto.getStatus());
                st.setInt(4, produto.getId());

                atualizado = st.executeUpdate() > 0;
            } catch (SQLException ex) {
                System.out.println("Erro ao atualizar produto: " + ex.getMessage());
            } finally {
                desconectar();
            }
        }
        return atualizado;
    }
    
    public boolean excluir(int id) {
        boolean excluido = false;
        if (conectar()) {
            try {
                String sql = "DELETE FROM " + TABELA + " WHERE id = ?";
                st = conn.prepareStatement(sql);
                st.setInt(1, id);
                
                excluido = st.executeUpdate() > 0;
            } catch (SQLException ex) {
                System.out.println("Erro ao excluir produto: " + ex.getMessage());
            } finally {
                desconectar();
            }
        }
        return excluido;
    }
    
    public ProdutosDTO buscarPorId(int id) {
        ProdutosDTO produto = null;
        
        if (conectar()) {
            try {
                String sql = "SELECT * FROM " + TABELA + " WHERE id = ?";
                st = conn.prepareStatement(sql);
                st.setInt(1, id);
                resultset = st.executeQuery();
                
                if (resultset.next()) {
                    produto = new ProdutosDTO();
                    produto.setId(resultset.getInt("id"));
                    produto.setNome(resultset.getString("nome"));
                    produto.setValor(resultset.getDouble("valor"));
                    produto.setStatus(resultset.getString("status"));
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao buscar produto por ID: " + ex.getMessage());
            } finally {
                desconectar();
            }
        }
        
        return produto;
    }
    
    public ArrayList<ProdutosDTO> buscarPorStatus(String status) {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        
        if (conectar()) {
            try {
                String sql = "SELECT * FROM " + TABELA + " WHERE status = ?";
                st = conn.prepareStatement(sql);
                st.setString(1, status);
                resultset = st.executeQuery();
                
                while (resultset.next()) {
                    ProdutosDTO produto = new ProdutosDTO();
                    produto.setId(resultset.getInt("id"));
                    produto.setNome(resultset.getString("nome"));
                    produto.setValor(resultset.getDouble("valor"));
                    produto.setStatus(resultset.getString("status"));
                    
                    listagem.add(produto);
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao buscar produtos por status: " + ex.getMessage());
            } finally {
                desconectar();
            }
        }
        
        return listagem;
    }
    
    public void desconectar() {
        conexao.desconectar();
    }
}
