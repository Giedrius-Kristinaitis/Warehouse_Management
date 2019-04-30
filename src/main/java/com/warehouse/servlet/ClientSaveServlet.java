package com.warehouse.servlet;

import com.warehouse.dao.ClientDAO;
import com.warehouse.model.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handles requests related to adding/editing clients
 */
@SuppressWarnings("Duplicates")
@WebServlet(urlPatterns = "/api/client/save")
public class ClientSaveServlet extends HttpServlet {

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

        Long oldCode = null;

        try {
            oldCode = Long.parseLong(req.getParameter("oldPersonalCode"));
        } catch (Exception ex) { }

        Long personalCode = Long.parseLong(req.getParameter("personalCode"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        Date birthDate = null;

        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthDate"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        Client client = new Client(personalCode, firstName, lastName,
                                    phone, email, birthDate);

        ClientDAO dao = new ClientDAO();

        Client saved = null;
        String action = null;

        // check if the action is update and call the correct method
        if (req.getParameter("update") == null) {
            saved = dao.save(client);
            action = "add";
        } else {
            saved = dao.update(oldCode, client);
            action = "edit";
        }

        // redirect if there was an error
        if (saved == null) {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=client&action=" + action + "&error=unknown");
        } else {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=client&page=1&success=" + action);
        }
    }
}
