package dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DAO<T> {
    private static Connection conn = ConxDB.getInstance();
    private Class<T> type;
    private Map<String,Object>map;
    private String nomTableau;


    public DAO(Class<T> type, Map<String,Object>list, String nomTableau) {
        this.type = type;
        this.map = list;
        this.nomTableau = nomTableau;
    }

    private String getAttribus(){
        String result = "";
        for(String a:map.keySet()){
            result = result + a + ",";
        }
        return  result = result.substring(0, result.length() - 1);
    }

    private Class<?>[] getTypesDeConstructeur() {
        Class<?>[] types = new Class<?>[map.size()];
        int i = 0;
        for (Object valeur : map.values()) {
            types[i] = (Class<?>) valeur;
            i++;
        }
        return types;
    }
    private List<String>getColomnNames(String attribus){
        List<String>columnNames=Arrays.asList(attribus.split(","));
        return columnNames;
    }



    public List<T> findAll() {
        List<T> liste = new ArrayList<>();
        String attribus=getAttribus();
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT "+attribus+" FROM " + nomTableau;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            List<String>columnNames=getColomnNames(attribus);
            while (rs.next()) {
                Object[] obj = new Object[columnNames.size()];
                Class<?>[] typesDeConstructeur = getTypesDeConstructeur();
                for (int i = 0; i < columnNames.size(); i++) {
                    obj[i] = rs.getObject(i + 1);
                }
                try {
                    Constructor<T> constructor = type.getDeclaredConstructor(typesDeConstructeur);
                    T instance = constructor.newInstance(obj);
                    liste.add(instance);
                } catch (Exception e) {
                    throw new RuntimeException("Erreur lors de l'instanciation de " + type.getSimpleName(), e);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return liste;
    }


    public int save(T objet) {
        Field[] champs = type.getDeclaredFields();

        StringBuilder sql = new StringBuilder("INSERT INTO " + nomTableau + " (");
        StringBuilder valeurs = new StringBuilder("VALUES (");

        for (int i = 0; i < champs.length; i++) { // Commencer à 0, car `id` n'existe plus
            sql.append(champs[i].getName()).append(",");
            valeurs.append("?,");

            champs[i].setAccessible(true);
            try {
                Object valeur = champs[i].get(objet);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Erreur lors de l'accès aux champs", e);
            }
        }

        sql.setLength(sql.length() - 1);
        valeurs.setLength(valeurs.length() - 1);
        sql.append(") ").append(valeurs).append(")");

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < champs.length; i++) {
                champs[i].setAccessible(true);
                Object valeur = champs[i].get(objet);

                if (valeur == null) {
                    throw new RuntimeException("Le champ '" + champs[i].getName() + "' est NULL !");
                }

                preparedStatement.setObject(i + 1, valeur);
            }

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("L'insertion a échoué, aucune ligne affectée.");
            }

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de " + type.getSimpleName(), e);
        }
        return -1;
    }
    public int save1(Map<String, Object> data) {
        String sql = "INSERT INTO " + nomTableau + " (";
        String valeurs = "VALUES (";

        // Generate column names and placeholders
        for (String key : data.keySet()) {
            sql += key + ",";
            valeurs += "?,";  // Placeholder for prepared statement
        }

        // Remove last commas
        sql = sql.substring(0, sql.length() - 1);
        valeurs = valeurs.substring(0, valeurs.length() - 1);
        sql += ") " + valeurs + ")";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            for (Object value : data.values()) {
                if (value == null) {
                    throw new RuntimeException("Le champ est NULL !");
                }
                preparedStatement.setObject(index++, value);  // Set value to prepared statement
            }

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("L'insertion a échoué, aucune ligne affectée.");
            }

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement", e);
        }
        return -1;
    }
    public int save2(List<Object> valeurs) {
        // Get the column names (assuming 'getAttribus' returns a comma-separated string of column names)
        String attribus = getAttribus();
        List<String> columnNames = getColomnNames(attribus);

        // Construct the SQL query dynamically based on the column names
        String sql = "INSERT INTO " + nomTableau + " (";
        String valuePlaceholders = "VALUES (";

        // Generate column names and placeholders
        for (String column : columnNames) {
            sql += column + ",";
            valuePlaceholders += "?,";  // Placeholder for prepared statement
        }

        // Remove the last commas
        sql = sql.substring(0, sql.length() - 1);
        valuePlaceholders = valuePlaceholders.substring(0, valuePlaceholders.length() - 1);
        sql += ") " + valuePlaceholders + ")";

        // Now prepare the statement and set the values from the 'valeurs' list
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set the values dynamically from the 'valeurs' list
            if (valeurs.size() != columnNames.size()) {
                throw new RuntimeException("Le nombre de valeurs ne correspond pas au nombre de colonnes.");
            }

            int index = 1;
            for (Object value : valeurs) {
                if (value == null) {
                    throw new RuntimeException("Le champ est NULL !");
                }
                preparedStatement.setObject(index++, value);  // Set value to prepared statement
            }

            // Execute the update and check if rows were affected
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("L'insertion a échoué, aucune ligne affectée.");
            }

            // Retrieve the generated keys (e.g., the ID of the inserted record)
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement", e);
        }

        return -1;
    }
    public T findById(int id) {
        String sql = "SELECT * FROM " + nomTableau + " WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Object[] obj = new Object[map.size()];
                List<String> columnNames = getColomnNames(getAttribus());
                for (int i = 0; i < columnNames.size(); i++) {
                    obj[i] = rs.getObject(i + 1);
                }
                Constructor<T> constructor = type.getDeclaredConstructor(getTypesDeConstructeur());
                return constructor.newInstance(obj);
            }
        } catch (SQLException | ReflectiveOperationException e) {
            throw new RuntimeException("Error retrieving element by ID", e);
        }
        return null;
    }

    public boolean update(T objet, int id) {
        StringBuilder sql = new StringBuilder("UPDATE " + nomTableau + " SET ");
        Method[] methods = type.getDeclaredMethods();
        List<String> columns = getColomnNames(getAttribus());

        for (String column : columns) {
            sql.append(column).append(" = ?, ");
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql.toString())) {
            int index = 1;
            for (String column : columns) {
                Method method = type.getMethod("get" + capitalize(column));
                Object value = method.invoke(objet);
                preparedStatement.setObject(index++, value);
            }

            preparedStatement.setInt(index, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ReflectiveOperationException e) {
            throw new RuntimeException("Error updating element", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM " + nomTableau + " WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting element", e);
        }
    }

    /*public int getId(T object) {
        String sql = "SELECT id FROM " + nomTableau + " WHERE ";
        List<String> columnNames = getColomnNames(getAttribus());
        Object value = null;
        String columnName = null;

        for (String column : columnNames) {
            Method method = type.getMethod("get" + capitalize(column));
            value = method.invoke(object);
            if (value != null) {
                columnName = column;
                break;
            }
        }

        if (columnName == null) {
            throw new IllegalArgumentException("No valid field found to query by.");
        }

        sql += columnName + " = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setObject(1, value);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException | ReflectiveOperationException e) {
            throw new RuntimeException("Error retrieving ID for object", e);
        }

        return -1;
    }*/

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}






