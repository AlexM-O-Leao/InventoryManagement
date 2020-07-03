package inventory.views;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import inventory.controllers.InventoryController;
import inventory.controllers.InventoryControllerClass;

@WebServlet("/categories/*")
public class Rest extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryId, productId;
        InventoryController controller = new InventoryControllerClass();

        resp.setContentType("application/json");
        JsonObjectBuilder jsonObjectBuilder = null;

        String[] splits = req.getRequestURI().split("/");
        switch(splits.length) {
            case 3: // /categories
                jsonObjectBuilder = controller.getCategoryJsonObjectBuilder();
                break;
            case 4: // /categories/<categoryId>
                categoryId = splits[3];
                if(!controller.hasCategoryById(categoryId)) {
                    jsonObjectBuilder = setError("There is no category with id "+categoryId+".");
                }
                else {
                    jsonObjectBuilder = controller.getCategoryById(categoryId).getJsonObjectBuilder();
                }
                break;
            case 5: // /categories/<categoriesId>/<productId>
                categoryId = splits[3];
                productId = splits[4];
                if(!controller.hasCategoryById(categoryId)) {
                    jsonObjectBuilder = setError("There is no category with id "+categoryId+".");
                }
                else if (!controller.hasProduct(categoryId, productId)) {
                    jsonObjectBuilder = setError("There is no product with id "+productId+" in category with id "+categoryId+".");
                }
                else {
                    jsonObjectBuilder = controller.getCategoryById(categoryId).getProduct(productId).getJsonObjectBuilder();
                }
                break;
            default:
                jsonObjectBuilder = setError(resp, "Unsupported operation.");
        }

        JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
        jsonWriter.writeObject(jsonObjectBuilder.build());
        jsonWriter.close();

        controller.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryId;
        String productId;
        String categoryName;
        int productQtd;
        InventoryController controller = new InventoryControllerClass();

        resp.setContentType("application/json");
        JsonObjectBuilder jsonObjectBuilder = null;

        JsonReader jsonReader = Json.createReader(req.getReader());
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        String[] splits = req.getRequestURI().split("/");
        switch(splits.length) {
            case 4: // /categories/<categoriesId>/
                categoryId = splits[3];
                categoryName = jsonObject.getString("name");
                if(!controller.hasCategoryById(categoryId)) {
                    jsonObjectBuilder = setError("No such task list.");
                }
                else {
                    jsonObjectBuilder = controller.changeCategoryNameById(categoryId, categoryName).getJsonObjectBuilder();
                }
                break;
            case 5: // /categories/<categoriesId>/<productId>
                categoryId = splits[3];
                productId = splits[4];
                productQtd = jsonObject.getInt("quantity");
                if(!controller.hasCategoryById(categoryId)) {
                    jsonObjectBuilder = setError("There is no category with id "+categoryId+".");
                }
                else if (!controller.hasProduct(categoryId, productId)) {
                    jsonObjectBuilder = setError("There is no product with id "+productId+" in category with id "+categoryId+".");
                }
                else {
                    jsonObjectBuilder = controller.changeProductQtd(categoryId, productId, productQtd).getJsonObjectBuilder();
                }
                break;
            default:
                jsonObjectBuilder = setError(resp, "Unsupported operation.");
        }

        JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
        jsonWriter.writeObject(jsonObjectBuilder.build());
        jsonWriter.close();

        controller.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryId, productId, categoryName, productName;
        InventoryController controller = new InventoryControllerClass();

        resp.setContentType("application/json");
        JsonObjectBuilder jsonObjectBuilder = null;

        JsonReader jsonReader = Json.createReader(req.getReader());
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        String[] splits = req.getRequestURI().split("/");
        switch(splits.length) {
            case 3: // /categories
                categoryName = jsonObject.getString("name");
                if(controller.hasCategoryByName(categoryName)) {
                    jsonObjectBuilder = setError("There is already a category with name "+categoryName+".");
                }
                else {
                    jsonObjectBuilder = controller.createCategory(categoryName).getJsonObjectBuilder();
                }
                break;
            case 4: // /categories/<categoriesId>/
                categoryId = splits[3];
                productName = jsonObject.getString("name");
                if(!controller.hasCategoryById(categoryId)) {
                    jsonObjectBuilder = setError("No such category.");
                }
                else {
                    jsonObjectBuilder = controller.createProduct(categoryId, productName).getJsonObjectBuilder();
                }
                break;
            case 5: // /categories/<categoriesId>/<productId>
                categoryId = splits[3];
                productId = splits[4];
                productName = jsonObject.getString("name");
                if(!controller.hasCategoryById(categoryId)) {
                    jsonObjectBuilder = setError("There is no category with id "+categoryId+".");
                }
                else if (!controller.hasProduct(categoryId, productId)) {
                    jsonObjectBuilder = setError("There is no product with id "+productId+" in category with id "+categoryId+".");
                }
                else {
                    jsonObjectBuilder = controller.changeProductName(categoryId, productId, productName).getJsonObjectBuilder();
                }
                break;
            default:
                jsonObjectBuilder = setError(resp, "Unsupported operation.");
        }

        JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
        jsonWriter.writeObject(jsonObjectBuilder.build());
        jsonWriter.close();

        controller.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryId, productId;
        InventoryController controller = new InventoryControllerClass();

        resp.setContentType("application/json");
        JsonObjectBuilder jsonObjectBuilder = null;

        String[] splits = req.getRequestURI().split("/");
        switch(splits.length) {
            case 4: // /categories/<categoriesId>/
                categoryId = splits[3];
                if(!controller.hasCategoryById(categoryId)) {
                    jsonObjectBuilder = setError("No such category.");
                }
                else {
                    controller.deleteCategory(categoryId);
                    jsonObjectBuilder = setSuccess("Category removed successfully.");
                }
                break;
            case 5: // /categories/<categoriesId>/<productId>
                categoryId = splits[3];
                productId = splits[4];
                if(!controller.hasCategoryById(categoryId)) {
                    jsonObjectBuilder = setError("There is no category with id "+categoryId+".");
                }
                else if (!controller.hasProduct(categoryId, productId)) {
                    jsonObjectBuilder = setError("There is no product with id "+productId+" in category with id "+categoryId+".");
                }
                else {
                    controller.deleteProduct(categoryId, productId);
                    jsonObjectBuilder = setSuccess("Product removed successfully.");
                }
                break;
            default:
                jsonObjectBuilder = setError(resp, "Unsupported operation.");
        }

        JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
        jsonWriter.writeObject(jsonObjectBuilder.build());
        jsonWriter.close();

        controller.close();
    }

    private static JsonObjectBuilder setMessage(String key, String message) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add(key, message);
        return jsonObjectBuilder;
    }

    private static JsonObjectBuilder setSuccess(String message) {
        return setMessage("message", message);
    }

    private static JsonObjectBuilder setError(String message) {
        return setMessage("error", message);
    }

    private static JsonObjectBuilder setError(HttpServletResponse resp, String message) {
        resp.setStatus(400);
        return setError(message);
    }
}
