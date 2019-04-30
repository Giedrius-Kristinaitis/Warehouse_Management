package com.warehouse.servlet;

import com.warehouse.dao.ClientDAO;
import com.warehouse.dao.ContractDAO;
import com.warehouse.model.Client;
import com.warehouse.model.Contract;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Deletes contracts
 */
@WebServlet(urlPatterns = "/api/contract/delete")
public class ContractDeleteServlet extends HttpServlet {

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

        ContractDAO dao = new ContractDAO();

        Contract deleted = dao.delete(id);

        if (deleted == null) {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=contract&page=1&error=failed_deletion");
        } else {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=contract&page=1&success=delete");
        }
    }
}
