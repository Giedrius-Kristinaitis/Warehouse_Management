package com.warehouse.servlet;

import com.warehouse.dao.StoredItemDAO;
import com.warehouse.model.StoredItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Inserts new items
 */
@WebServlet(urlPatterns = "/api/item/save")
public class ItemSaveServlet extends HttpServlet {

    /**
     * Handles post requests
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Double weight = Double.parseDouble(req.getParameter("weight"));
        Double width = Double.parseDouble(req.getParameter("width"));
        Double length = Double.parseDouble(req.getParameter("length"));
        Double height = Double.parseDouble(req.getParameter("height"));
        String category = req.getParameter("category");

        StoredItem item = new StoredItem(
                System.currentTimeMillis(),
                name,
                description,
                null,
                width,
                length,
                height,
                weight,
                category,
                0,
                null,
                null
        );

        StoredItemDAO dao = new StoredItemDAO();

        boolean saved = dao.saveItem(item);

        if (!saved) {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=stored_item&action=add&error=unknown");
        }

        // save the stored items
        Enumeration<String> params = req.getParameterNames();

        int index = 0;

        while (params.hasMoreElements()) {
            if (index < 7) {
                index++;
                params.nextElement();
                continue;
            }

            int count = Integer.parseInt(req.getParameter(params.nextElement()));
            String owner = req.getParameter(params.nextElement());
            String storageArea = req.getParameter(params.nextElement());

            dao.saveStoredItem(item.getId(), count, owner, storageArea);

            index++;
        }

        resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=stored_item&page=1&success=add");
    }
}
