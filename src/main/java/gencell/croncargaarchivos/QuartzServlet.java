/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            JobDetail job = new JobDetail();
            job.setName("JobContabCargaArchivos");
            job.setJobClass(JobCargaArchivos.class);

            CronTrigger trigger = new CronTrigger();
            trigger.setName("TriggerContabCargaArchivos");
            trigger.setCronExpression("0 */1 * * * ?");
            //trigger.setCronExpression("0 0 * ? * * *");//cada hora
            //trigger.setCronExpression("0 */5 * * * ?");//cada 15 minutos
            //trigger.setCronExpression("0 */3 * * * ?");//cada 3 minutos
            //trigger.setCronExpression("0 * / 2 * * *"); //cada dos horas
            //trigger.setCronExpression("0 * * * *"); //cada dos horas           
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException ex) {
            Logger.getLogger(QuartzServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(QuartzServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}
