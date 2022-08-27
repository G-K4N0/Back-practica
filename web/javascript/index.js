function obtener()
{
    get();
}

function agregar(){
  post();
}
function borrar()
{
    delet();
}
function actualizar(){
 
}
function buscar(){
 
}
function get()
{
    $.get("http://192.168.1.70:8090/AppLibreriaKano/librosApi", function (data) {
        try {
          
            $.each(JSON.parse(data), function (index, obj) {
                $("#info").append('</br>');
                $("#info").append('id: ' + obj.libro_id + ' Editorial: '+ obj.editorial_id + ' Titulo: ' + obj.titulo +' Autor: ' + obj.autor + ' Precio:' + obj.precio + '</br>');
            });
        } catch (error) {
            alert('Hubo un error con el servidor');
        }
    }).fail(function () {
        alert('No se obtuvieron datos del servidor');
    });
}

function post()
{
  $(document).ready(function(){
      var titulo = $("#txtTitulo").val();
      var editorial = $("#txtEditorial_id").val();
      var autor = $("#txtAutor").val();
      var precio = $("#txtPrecio").val();
      
      
      $.post("http://192.168.1.70:8090/AppLibreriaKano/librosApi",{titulo:titulo,editorial_id:editorial,
          autor:autor,precio:precio},function(data){
        $("#info").html(data);
      });
  }).fail(function(){
      alert('No se pudo acceder e ingresar datos al servidor');
  });
}
