/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airline.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Phani Rahul
 */
public class Database {

    private Connection conn;
    private ConnectionParameters connectionParameters;
    private PreparedStatement ps;
    private ResultSet rs;

    public Connection getConn() {
        return conn;
    }

    public ConnectionParameters getConnectionParameters() {
        return connectionParameters;
    }
    

    public static Database getConnection(ConnectionParameters cp)
            throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Database inst = new Database();
        inst.connectionParameters = cp;
        String url = cp.getUrl();
        String db = cp.getDb();
        String driver = cp.getDriver();
        String username = cp.getUsername();
        String password = cp.getPassword();
        Class.forName(driver).newInstance();
        inst.conn = DriverManager.getConnection(url + db, username, password);

        return inst;
    }
     public PreparedStatement getPreparedStatement(String query) throws SQLException{
      
            ps=conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return ps;
        }
     
     public ResultSet runPreparedStatement(PreparedStatement ps) throws SQLException{
         rs= ps.executeQuery();
         return rs;
     }
}
