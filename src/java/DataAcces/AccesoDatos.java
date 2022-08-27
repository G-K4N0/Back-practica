package DataAcces;

import Entities.Libro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Kano
 */
public class AccesoDatos {
     private Connection conexion;
    public byte conectar() throws ClassNotFoundException, SQLException
    {
        byte responde = 0;
        Class.forName("com.mysql.jdbc.Driver");      
        conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dblibreria","root","talento");
        if (conexion !=null)
            responde =1;
        return responde;
    }
    
    public void desconectar() throws SQLException
    {
        if (!conexion.isClosed())    
        conexion.close();
    }
    
     public ArrayList<Libro>consultaLibros(String name, String pwd) throws SQLException
    {
        ArrayList<Libro> listaLibros = new ArrayList<Libro>();
        
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("select "+ name+","+ pwd +" from libros");
        
        while (rs.next())
        {
            Libro libro = new Libro();
            libro.setLibro_id(rs.getInt("libro_id"));
            libro.setEditorial_id(rs.getInt("editorial_id"));
            libro.setTitulo(rs.getString("titulo"));
            libro.setAutor(rs.getString("autor"));
            libro.setPrecio(rs.getFloat("precio"));
            listaLibros.add(libro);
        }
        rs.close();
        return listaLibros;
    }
     
     public void guardarLibro(Libro libro) throws SQLException
     {
        String sql;
        String titulo,autor;
        float precio;
        int editorial;
        editorial = libro.getEditorial_id();
        titulo = libro.getTitulo();
        autor = libro.getAutor();
        precio = libro.getPrecio();
        
        sql = "INSERT INTO libros (editorial_id,titulo,autor,precio)  values ("+editorial+",'"+titulo+"','"+autor
                +"',"+precio+")";
            Statement st = conexion.createStatement();
            st.executeUpdate(sql); 
     }
     public int modificarLibro(Libro libro)
     {
         int res=0;
        String sql;
        int libro_id,editorial_id;
        String titulo,autor;
        float precio;
        libro_id = libro.getLibro_id();
        editorial_id = libro.getEditorial_id();
        titulo = libro.getTitulo();
        autor = libro.getAutor();
        precio = libro.getPrecio();
        
        sql = "UPDATE libros SET "+editorial_id+","+titulo+"," +autor+","+precio+ "WHERE libro_id="+libro_id;
        try {
            Statement st = conexion.createStatement();
            res =st.executeUpdate(sql);
            
        } catch (Exception e) {
            
        }
        return res;
     }
     
     
     public void eliminarLibro(int libro_id)
     {
        String sql;
        sql = "DELETE FROM libros WHERE libro_id="+libro_id;
        try {
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            
        } catch (Exception e) {
            
        }
     }
     public Libro buscarLibro(int libro_id) throws SQLException
     {
         Libro lib = new Libro();
          Statement st = conexion.createStatement();
          ResultSet rs = st.executeQuery("select * from libros where libro_id="+libro_id);
          
          if (rs.next()) {
             lib.setLibro_id(rs.getInt("libro_id"));
             lib.setEditorial_id(rs.getInt("editorial_id"));
             lib.setTitulo(rs.getString("titulo"));
             lib.setAutor(rs.getString("autor"));
             lib.setPrecio(rs.getFloat("precio"));
         }else{
              lib = null;
          }
          return lib;
     }
}
