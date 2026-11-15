package DAO;

import Model.Atividade;
import Model.InstituicaoParceira;
import Util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtividadeDAO {

    // 1. CREATE (Inserir)
    public void inserir(Atividade atividade) {
        String sql = "INSERT INTO atividade (titulo, tipo, horas_complementares, id_instituicao) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, atividade.getTitulo());
            stmt.setString(2, atividade.getTipo());
            stmt.setDouble(3, atividade.getHorasComplementares());
            stmt.setInt(4, atividade.getInstituicao().getId()); // chave estrangeira
            stmt.executeUpdate();

            // Pega o id que o banco gerou e guarda no objeto
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    atividade.setId(rs.getInt(1));
                }
            }
            System.out.println("Atividade cadastrada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar atividade: " + e.getMessage());
        }
    }

    // 2. READ (Listar todas as atividades com o nome da instituição)
    public List<Atividade> listar() {
        List<Atividade> lista = new ArrayList<>();
        // O JOIN traz junto os dados da instituição que oferece cada atividade
        String sql = "SELECT a.id, a.titulo, a.tipo, a.horas_complementares, " +
                "i.id AS id_inst, i.nome, i.cnpj, i.email " +
                "FROM atividade a " +
                "INNER JOIN instituicao_parceira i ON a.id_instituicao = i.id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Primeiro monta a instituição...
                InstituicaoParceira inst = new InstituicaoParceira(
                        rs.getInt("id_inst"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("email")
                );
                // ...depois a atividade, já ligada à instituição
                Atividade atividade = new Atividade(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("tipo"),
                        rs.getDouble("horas_complementares"),
                        inst
                );
                lista.add(atividade);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar atividades: " + e.getMessage());
        }
        return lista;
    }

    // 3. UPDATE (Atualizar)
    public void atualizar(Atividade atividade) {
        String sql = "UPDATE atividade SET titulo = ?, tipo = ?, horas_complementares = ?, id_instituicao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, atividade.getTitulo());
            stmt.setString(2, atividade.getTipo());
            stmt.setDouble(3, atividade.getHorasComplementares());
            stmt.setInt(4, atividade.getInstituicao().getId());
            stmt.setInt(5, atividade.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Atividade atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma atividade encontrada com este id.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar atividade: " + e.getMessage());
        }
    }

    // 4. DELETE (Excluir)
    public void excluir(int id) {
        String sql = "DELETE FROM atividade WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Atividade excluída com sucesso!");
            } else {
                System.out.println("Nenhuma atividade encontrada com este id.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir atividade: " + e.getMessage());
        }
    }
}