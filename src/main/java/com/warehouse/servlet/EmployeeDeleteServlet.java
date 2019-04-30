package com.warehouse.servlet;

import com.warehouse.dao.ClientDAO;
import com.warehouse.dao.EmployeeDAO;
import com.warehouse.model.Client;
import com.warehouse.model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Deletes employees
 */
@WebServlet(urlPatterns = "/api/employee/delete")
public class EmployeeDeleteServlet extends HttpServlet {

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
        Long number = Long.parseLong(req.getParameter("number"));

        EmployeeDAO dao = new EmployeeDAO();

        Employee deleted = dao.delete(number);

        if (deleted == null) {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=employee&page=1&error=failed_deletion");
        } else {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=employee&page=1&success=delete");
        }
    }
}
