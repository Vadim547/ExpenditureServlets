package controller;

import DAO.BillingDAO;
import model.Billing;
import service.BillingService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "BillingServlet", value = "/Deposit")

public class BillingServlet extends HttpServlet {
    BillingService billingService = new BillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String param = request.getParameter("action");
        switch (param) {
            case "total":
                billingService.totalAmount(request, response);
                break;
            case "who-payed":
                billingService.whoPayed(request, response);
                break;
            case "description":
                billingService.whoOwesToWhom(request, response);
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        billingService.saveAmount(request, response);
    }
}
