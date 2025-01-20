package dao;

import entities.AutoEcole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class AutoEcoleDAO {
    private static Connection connection;
    public static void save(AutoEcole autoEcole) {
        Map<String,Object>map=new HashMap<>();
        map.put("nom",String.class);
        map.put("adresse",String.class);
        map.put("numTelephone",Integer.class);
        map.put("email",String.class);
        DAO<AutoEcole>dao=new DAO<>(AutoEcole.class,map,"auto_ecole");
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "DELETE * FROM auto_ecole";
        try {
            stmt=connection.createStatement();
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException("cant delete auto_ecole precedente");
        }
        dao.save(autoEcole);
    }
    public static AutoEcole getAutoEcole() {
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "select * FROM auto_ecole";
        try {
            stmt=connection.createStatement();
            rs=stmt.executeQuery(sql);
            while (rs.next()) {
                String nom=rs.getString("nom");
                String adresse=rs.getString("adresse");
                Integer numTelephone=rs.getInt("numTelephone");
                String email=rs.getString("email");
                return new AutoEcole(nom,adresse,numTelephone,email);
            }

        }catch(SQLException e) {
            throw new RuntimeException("cant get auto_ecole");
        }
        return null;
    }
}
