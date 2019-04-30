package com.warehouse.servlet;

import com.warehouse.dao.EmployeeDAO;
import com.warehouse.model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Inserts/updates employees
 */
@SuppressWarnings("Duplicates")
@WebServlet(urlPatterns = "/api/employee/save")
public class EmployeeSaveServlet extends HttpServlet {

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

        Long oldNumber = null;

        try {
            oldNumber = Long.parseLong(req.getParameter("oldNumber"));
        } catch (Exception ex) {}

        Long timeCardNumber = Long.parseLong(req.getParameter("number"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String authority = req.getParameter("authority");
        String workplace = req.getParameter("workplace");

        Employee employee = new Employee(
                timeCardNumber,
                firstName,
                lastName,
                authority,
                workplace
        );

        EmployeeDAO dao = new EmployeeDAO();

        Employee saved = null;
        String action = null;

        // check if the action is update and call the correct method
        if (req.getParameter("update") == null) {
            saved = dao.save(employee);
            action = "add";
        } else {
            saved = dao.update(oldNumber, employee);
            action = "edit";
        }

        // redirect if there was an error
        if (saved == null) {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=employee&action=" + action + "&error=unknown");
        } else {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=employee&page=1&success=" + action);
        }
    }
}
