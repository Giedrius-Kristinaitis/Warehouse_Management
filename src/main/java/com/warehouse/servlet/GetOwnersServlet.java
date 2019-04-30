package com.warehouse.servlet;

import com.warehouse.dao.StoredItemDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Gets all possible clients
 */
@WebServlet(urlPatterns = "/api/item/get_owners")
public class GetOwnersServlet extends HttpServlet {

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
        resp.setCharacterEncoding("UTF-8");

        StoredItemDAO dao = new StoredItemDAO();

        List<String> owners = dao.getOwners();

        for (String owner: owners) {
            resp.getWriter().print(owner + "|");
        }
    }
}
