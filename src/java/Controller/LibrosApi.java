
package Controller;

import DataAcces.AccesoDatos;
import Entities.Libro;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Kano
 */
public class LibrosApi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccesoDatos accesoLibros = new AccesoDatos();

        JSONObject productoJSON;
        JSONArray listaJSON = new JSONArray();

        try {
            accesoLibros.conectar();
            ArrayList<Libro> lista = accesoLibros.consultaLibros();

            for (Libro lib : lista) {
                productoJSON = new JSONObject();
                productoJSON.put("libro_id", lib.getLibro_id());
                productoJSON.put("editorial_id", lib.getEditorial_id());
                productoJSON.put("titulo", lib.getTitulo());
                productoJSON.put("autor", lib.getAutor());
                productoJSON.put("precio", lib.getPrecio());

                listaJSON.add(productoJSON);
            }
            resp.getWriter().print(JSONValue.toJSONString(listaJSON));
        } catch (ClassNotFoundException ex) {
            resp.getWriter().print(ex.getMessage());
        } catch (SQLException ex) {
            resp.getWriter().print(ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try {  
            Libro libro = new Libro();
             AccesoDatos acces = new AccesoDatos();
             acces.conectar();
            
             libro.setEditorial_id(Integer.parseInt(req.getParameter("editorial_id")));
             libro.setTitulo(req.getParameter("titulo"));
             libro.setAutor(req.getParameter("autor"));
             libro.setPrecio(Float.parseFloat((req.getParameter("precio"))));
             
             acces.guardarLibro(libro);
             
             acces.desconectar();
             
        } catch (Exception e) {
            resp.getWriter().print( e.getMessage()); 
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Libro libro = new Libro();
            AccesoDatos acceso = new AccesoDatos();
            acceso.conectar();

            libro.setLibro_id(Integer.parseInt(req.getParameter("libro_id")));
            libro.setEditorial_id(Integer.parseInt(req.getParameter("editorial_id")));
            libro.setTitulo(req.getParameter("titulo"));
            libro.setAutor(req.getParameter("autor"));
            libro.setPrecio(Float.parseFloat(req.getParameter("precio")));

            acceso.modificarLibro(libro);
           

            acceso.desconectar();
        } catch (Exception e) {
            resp.getWriter().print(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int libro_id =Integer.parseInt(req.getParameter("libro_id"));
            AccesoDatos acceso = new AccesoDatos();
            acceso.conectar();

            acceso.eliminarLibro(libro_id);
            
            acceso.desconectar();
        } catch (Exception e) {
            resp.getWriter().print(e.getMessage());

        }
    }
}