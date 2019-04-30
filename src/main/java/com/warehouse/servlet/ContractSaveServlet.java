package com.warehouse.servlet;

import com.warehouse.dao.ContractDAO;
import com.warehouse.model.Contract;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * Inserts/updates contracts
 */
@SuppressWarnings("Duplicates")
@WebServlet(urlPatterns = "/api/contract/save")
public class ContractSaveServlet extends HttpServlet {

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

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Long oldId = null;

        try {
            oldId = Long.parseLong(req.getParameter("oldId"));
        } catch (Exception ex) {}

        Date date = null;
        Date storingFrom = null;
        Date storingUntil = null;

        try {
            date = format.parse(req.getParameter("date"));
            storingFrom = format.parse(req.getParameter("storingFrom"));
            storingUntil = format.parse(req.getParameter("storingUntil"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        double rentedArea = Double.parseDouble(req.getParameter("rentedArea"));
        String status = req.getParameter("status");
        String client = req.getParameter("client");
        String employee = req.getParameter("employee");

        ContractDAO dao = new ContractDAO();

        Long id = System.currentTimeMillis();

        Contract contract = new Contract(
            id,
            date,
            storingFrom,
            storingUntil,
            rentedArea,
            status,
            null,
            null
        );

        Contract saved = null;
        String action = null;

        // check if the action is update and call the correct method
        if (req.getParameter("update") == null) {
            saved = dao.save(contract, client, employee);
            action = "add";
        } else {
            saved = dao.update(oldId, contract, client, employee);
            action = "edit";
        }

        // redirect if there was an error
        if (saved == null) {
            resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=contract&action=" + action + "&error=unknown");
        }

        // save the bills
        Enumeration<String> params = req.getParameterNames();

        int index = 0;

        while (params.hasMoreElements()) {
            if (index < 7) {
                index++;
                params.nextElement();
                continue;
            }

            if (!params.hasMoreElements()) {
                break;
            }

            String name = req.getParameter(params.nextElement());

            if (!params.hasMoreElements()) {
                break;
            }

            String description = req.getParameter(params.nextElement());

            if (!params.hasMoreElements()) {
                break;
            }

            double amount = Double.parseDouble(req.getParameter(params.nextElement()));

            dao.saveBill(oldId == null ? id : oldId, name, description, amount);

            index++;
        }

        resp.sendRedirect(req.getContextPath() + "/page/form.jsp?form=contract&page=1&success=" + action);
    }
}
