package com.warehouse.servlet;

import com.warehouse.dao.ClientDAO;
import com.warehouse.model.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Deletes clients
 */
@WebServlet(urlPatterns = "/api/client/delete")
public class ClientDeleteServlet extends HttpServlet {

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
        Long personalCode = Long.parseLong(req.getParameter("code"));

        ClientDAO dao = new ClientDAO();

        Client deleted = dao.delete(personalCode);

        if (deleted == null) {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=client&page=1&error=failed_deletion");
        } else {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=client&page=1&success=delete");
        }
    }
}
