package com.warehouse.servlet;

import com.warehouse.dao.ClientDAO;
import com.warehouse.dao.StoredItemDAO;
import com.warehouse.model.Client;
import com.warehouse.model.StoredItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Deletes stored items
 */
@WebServlet(urlPatterns = "/api/item/delete")
public class ItemDeleteServlet extends HttpServlet {

    /**
     * Handles get requests
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        StoredItemDAO dao = new StoredItemDAO();

        StoredItem deleted = dao.delete(id);

        if (deleted == null) {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=stored_item&page=1&error=failed_deletion");
        } else {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=stored_item&page=1&success=delete");
        }
    }
}
