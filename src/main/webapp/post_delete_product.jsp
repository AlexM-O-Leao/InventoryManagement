<%@page import="java.util.List"%>
<%@page import="inventory.controllers.InventoryControllerClass"%>
<%@page import="inventory.models.Category"%>
<%@page import="inventory.models.Product"%>
<%@page import="inventory.models.CategoryClass"%>
<%@page import="inventory.models.ProductClass"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Post_Delete_Products</title>
</head>

<body>

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

    <h2>Adicionar Produtos</h2>

    <form action="post_delete_product.jsp" method="POST">
        <p>ID Categoria: <input type="text" name="id_cat" /></p>
        <p>Nome Produto: <input type="text" name="name_form" /></p>

        <%
       String name_prod ="";
       String id1="";

       id1 = request.getParameter("id_cat");
       name_prod = request.getParameter("name_form");

                if (id1 !=null || name_prod != null) {
                	control.createProduct(id1, name_prod);
                } 
            %>
        <p><button onclick="add_P()">Adicionar Produto</button></p>
    </form>

    <h2>Eliminar Produtos</h2>

    <form action="post_delete_product.jsp" method="POST">

        <p>ID Categoria: <input type="text" name="id_cat_delete_form" /></p>
        <p>ID Produto: <input type="text" name="name_delete_form" /></p>

        <%
       String name_delete="";
       String id_delete="";

       id_delete = request.getParameter("id_cat_delete_form");
       name_delete = request.getParameter("name_delete_form");

                if (id_delete !=null || name_delete != null) {
                	control.deleteProduct(id_delete, name_delete);
                } 
            %>
        <p><button onclick="delete_P()">Eliminar Produto</button></p>
    </form>

    <h2>Alterar Nome Produto</h2>

    <form action="post_delete_product.jsp" method="POST">

        <p>ID Categoria: <input type="text" name="id_cat_form" /></p>
        <p>ID Produto: <input type="text" name="id_prod_form" /></p>
        <p>Nome Novo do Produto: <input type="text" name="name_new_form" /></p>

        <%
       String name_new_alter="";
       String id_cat_alter="";
       String id_prod_alter="";

       id_cat_alter = request.getParameter("id_cat_form");
       id_prod_alter = request.getParameter("id_prod_form");
       name_new_alter = request.getParameter("name_new_form");
       
                if (id_cat_alter !=null || id_prod_alter !=null || name_new_alter !=null) {
                	control.changeProductName(id_cat_alter, id_prod_alter, name_new_alter);
                } 
            %>

        <p><button onclick="alter_P()">Alterar Nome do Produto</button></p>
    </form>

    <p><a href="get.jsp">Ver DB</a></p>
    <p><a href="index.jsp">Regressar ao Menu Inicial</a></p>

</body>

<script>

    function delete_P() {

        alert("Produto Eliminado");

    }
    function alter_P() {

        alert("Nome do Produto Alterado");

    }
    function add_P() {

        alert("Produto Adicionado");

    }
</script>

</html>