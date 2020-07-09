<%@page import="java.util.List"%>
<%@page import="inventory.controllers.InventoryControllerClass"%>
<%@page import="inventory.models.Category"%>
<%@page import="inventory.models.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <h1>Lista de Categorias e Produtos Existentes na DB</h1>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lista Categorias</title>
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
    <a href="index.jsp">Menu Principal</a>
</body>

</html>