package DAO;

import Model.InstituicaoParceira;
import Util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstituicaoParceiraDAO {

    // 1. CREATE (Inserir)
    public void inserir(InstituicaoParceira instituicao) {
        String sql = "INSERT INTO instituicao_parceira (nome, cnpj, email) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, instituicao.getNome());
            stmt.setString(2, instituicao.getCnpj());
            stmt.setString(3, instituicao.getEmail());
            stmt.executeUpdate();

            // Pega o id que o banco gerou e guarda no objeto
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    instituicao.setId(rs.getInt(1));
                }
            }
            System.out.println("Instituição cadastrada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar instituição: " + e.getMessage());
        }
    }

    // 2. READ (Listar todas as instituições)
    public List<InstituicaoParceira> listar() {
        List<InstituicaoParceira> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cnpj, email FROM instituicao_parceira";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Cria o objeto com os dados de cada linha do banco
                InstituicaoParceira inst = new InstituicaoParceira(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("email")
                );
                lista.add(inst);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar instituições: " + e.getMessage());
        }
        return lista;
    }

    // 3. UPDATE (Atualizar)
    public void atualizar(InstituicaoParceira instituicao) {
        String sql = "UPDATE instituicao_parceira SET nome = ?, cnpj = ?, email = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, instituicao.getNome());
            stmt.setString(2, instituicao.getCnpj());
            stmt.setString(3, instituicao.getEmail());
            stmt.setInt(4, instituicao.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Instituição atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma instituição encontrada com este id.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar instituição: " + e.getMessage());
        }
    }

    // 4. DELETE (Excluir)
    public void excluir(int id) {
        String sql = "DELETE FROM instituicao_parceira WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Instituição excluída com sucesso!");
            } else {
                System.out.println("Nenhuma instituição encontrada com este id.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir instituição: " + e.getMessage());
        }
    }
}