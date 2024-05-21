package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EtudiantM {
    private Connection connection;

    public EtudiantM() {
        this.connection = Connexion.getCn();
    }

    public boolean create(Etudiant o) {
        String query = "INSERT INTO Etudiant (nom, prenom, sexe, filiere) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, o.getNom());
            stmt.setString(2, o.getPrenom());
            stmt.setString(3, o.getSexe());
            stmt.setString(4, o.getFiliere());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Etudiant o) {
        String query = "DELETE FROM Etudiant WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, o.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Etudiant o) {
        String query = "UPDATE Etudiant SET nom = ?, prenom = ?, sexe = ?, filiere = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, o.getNom());
            stmt.setString(2, o.getPrenom());
            stmt.setString(3, o.getSexe());
            stmt.setString(4, o.getFiliere());
            stmt.setInt(5, o.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Etudiant findById(int id) {
        String query = "SELECT * FROM Etudiant WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Etudiant(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("sexe"),
                        rs.getString("filiere")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Etudiant> findAll() {
        List<Etudiant> etudiants = new ArrayList<>();
        String query = "SELECT * FROM Etudiant";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                etudiants.add(new Etudiant(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("sexe"),
                        rs.getString("filiere")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }
}