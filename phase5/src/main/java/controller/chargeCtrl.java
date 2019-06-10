package controller;

import domain.*;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.Tool;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class chargeCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out =new PrintWriter (new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
        response.setContentType("application/json ; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String result = "";
        Integer Credit = 0;

        try{

            JSONObject json = new JSONObject((String)request.getAttribute("body"));
            String credit = json.getString("credit");

            String user = (String)request.getAttribute("user");
            if(user == null)
                user = "";


            Credit = InfoBank.getUserByUsername(user).getCredit();


            if(InfoBank.getUserByUsername(user) != null) {

                if (Tools.IsItNum(credit))

                    if (Tools.POSTreq(InfoBank.apiKey, "http://139.59.151.5:6664/bank/pay", user, Tools.decode(credit))) {

                        Individual ind = InfoBank.getUserByUsername(user);

                        ind.charge(Tools.decode(credit));

                        ORMapper.UpdateUserCredit(ind);

                        response.setStatus(202);
                        result = "OK";
                        Credit = InfoBank.getUserByUsername(user).getCredit();
                    }
                    else {
                        response.setStatus(503);
                        result = "server unavailable";
                        //request.setAttribute("msg", "ارتباط با سرور بانک امکان پذیر نمی باشد .");
                    }
                else
                    throw new IllegalArgumentException("");
            }

        }catch(IllegalArgumentException ie){
            response.setStatus(406);
            result = "Not acceptable credit";
            //request.setAttribute("msg", "اعتبار وارد شده صحیح نمی باشد .");
        }
        catch(NullPointerException ne){
            response.setStatus(401);
            result = "User Not Found";
            //request.setAttribute("msg", "کاربر نامعتبر است .");
        }catch (Exception e){}
        out.println("{ \"result\" : \"" + result + "\", \"newCredit\" : " + Credit + "}");
        /// BANK
        /*request.setAttribute("user",InfoBank.getUserByUsername(request.getParameter("user")));
        request.getRequestDispatcher("form.jsp").forward(request, response);*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

}
