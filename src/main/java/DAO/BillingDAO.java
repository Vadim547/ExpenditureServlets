package DAO;

import dto.DescriptionDto;
import dto.ResultDto;
import dto.TotalDto;
import model.Billing;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class BillingDAO {

    public void addAmount(Billing billing) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(billing);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public TotalDto getTotalAmount() {
        TotalDto totalDto = new TotalDto();
        double total = 0;
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Billing ");
            List<Billing> billings = query.list();
            if (billings != null && !billings.isEmpty()) {
                for (Billing billing : billings) {
                    total += billing.getAmount();

                }

                BigDecimal totalAmount = new BigDecimal(total).setScale(2, RoundingMode.CEILING);
                totalDto.setTotal(totalAmount);
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.getMessage();
        }
        return totalDto;
    }

    public List<ResultDto> whoHasPayed() {
        String hql = "select  i.firstName , b.amount from Billing b right join b.user i on b.userId = i.id";

        List<ResultDto> resultDtos = new LinkedList<>();

        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery(hql);

            List<Object[]> listResult = query.list();
            for (Object[] result : listResult) {
                ResultDto resultDto = new ResultDto();
                resultDto.setName((String) result[0]);
                resultDto.setAmount((Double) result[1]);
                resultDtos.add(resultDto);
            }

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.getMessage();
        }
        return resultDtos;
    }

    public List<DescriptionDto> whoOwesMe() {
        List<DescriptionDto> descriptionDtos = new LinkedList<>();

        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            String hql = "select sum(b.amount)/count(i.firstName) from Billing b right join b.user i on b.userId = i.id";
            String hql1 = "select  i.firstName , b.amount from Billing b right join b.user i on b.userId = i.id";

            Double avgSum = (Double) session.createQuery(hql).getSingleResult();
            Query query = session.createQuery(hql1);

            List<Object[]> nameAndAmountList = query.list();
            for (Object[] result : nameAndAmountList) {
                String name = (String) result[0];
                Double amount = (result[1] == null) ? 0 : ((Double) result[1]);
                Double shouldGive = round(avgSum - amount);
                Double shouldReceive = round(amount - avgSum);

                DescriptionDto descriptionDto = new DescriptionDto();
                descriptionDto.setName(name);
                descriptionDto.setAmount(round(amount));
                descriptionDto.setAverage(round(avgSum));
                descriptionDto.setShouldPay((avgSum > amount) ? shouldGive : 0.0);
                descriptionDto.setShouldReceive((avgSum < amount) ? shouldReceive : 0.0);
                descriptionDtos.add(descriptionDto);
            }

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.getMessage();
        }
        return descriptionDtos;
    }

    private double round(double number) {
        double roundOff = Math.round(number * 100.0) / 100.0;
        return roundOff;
    }
}
