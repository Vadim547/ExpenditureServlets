package service;

import DAO.BillingDAO;
import com.google.gson.GsonBuilder;
import dto.DescriptionDto;
import dto.ResultDto;
import com.google.gson.Gson;
import dto.TotalDto;
import model.Billing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class BillingService {
    BillingDAO billingDAO = new BillingDAO();

    public void saveAmount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = request.getReader();
        Billing billing = gson.fromJson(bufferedReader, Billing.class);
        billingDAO.addAmount(billing);
        PrintWriter out = response.getWriter();
        out.write(String.valueOf(billing));
    }

    public void totalAmount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        TotalDto totalAmount = billingDAO.getTotalAmount();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        out.write(gson.toJson(totalAmount));
    }

    public void whoPayed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        List<ResultDto> resultDtos = billingDAO.whoHasPayed();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        out.write(gson.toJson(resultDtos));
    }

    public void whoOwesToWhom(HttpServletRequest request, HttpServletResponse response)throws  IOException {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        List<DescriptionDto> descriptionDtoList = billingDAO.whoOwesMe();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        out.write(gson.toJson(descriptionDtoList));
    }

}
