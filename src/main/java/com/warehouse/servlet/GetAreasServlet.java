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
 * Gets all possible storage areas
 */
@WebServlet(urlPatterns = "/api/item/get_areas")
public class GetAreasServlet extends HttpServlet {

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

        List<String> areas = dao.getAreas();

        for (String area: areas) {
            resp.getWriter().print(area + "|");
        }
    }
}
