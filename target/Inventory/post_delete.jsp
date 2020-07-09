<%@page import="java.util.List"%>
<%@page import="inventory.controllers.InventoryControllerClass"%>
<%@page import="inventory.models.Category"%>
<%@page import="inventory.models.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Post_Delete</title>
</head>


<h1>Lista de Categorias e Produtos Existentes na DB</h1>

<%
    InventoryControllerClass control = new InventoryControllerClass();
    List<Category> arrC = control.readCategories();
%>

<table border="2">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Produtos</th>
        </tr>
    </thead>

    <tbody>
        <% if(arrC != null) {
        for (int i = 0; i < arrC.size(); i++) {
    %>
        <tr>
            <td>
                <%= arrC.get(i).getId()%>
            </td>
            <td>
                <%= arrC.get(i).getName()%>
            </td>
            <td>
                <%= arrC.get(i).getProducts().toString()%>
            </td>
        </tr>
        <% }
    } %>
    </tbody>
</table>

<br>
<br>


<h2>Adicionar Categorias</h2>

<form action="post_delete.jsp" method="POST">

    <p>Nome Categoria: <input type="text" name="name" /></p>
    <%
       String name ="";   
       name = request.getParameter("name");
                if (name != null) {
                	 control.createCategory(name);
                } 
            %>
    <p><button onclick="add_C()">Adicionar Categoria</button></p>
</form>

<h2>Eliminar Categorias</h2>

<form action="post_delete.jsp" method="POST">

    <p>Eliminar Categoria (ID): <input type="text" name="id" /></p>
    <%
       String name_delete="";   
       name_delete = request.getParameter("id");
                if (name_delete != null) {
                	control.deleteCategory(name_delete);
                } 
            %>
    <p><button onclick="delete_C()">Eliminar Categoria</button></p>
</form>


<p><a href="get.jsp">Ver DB</a></p>
<p><a href="index.jsp">Regressar ao Menu Inicial</a></p>

<script>

    function delete_C() {

        alert("Categoria Apagada");

    }
    function delete_T() {

        alert("Teacher successfully deleted");

    }
    function add_C() {

        alert("Categoria Criada");

    }
    function add_T() {

        alert("Teacher successfully added");

    }
</script>

</html>