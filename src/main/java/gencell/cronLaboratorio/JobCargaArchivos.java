/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.cronLaboratorio;

import gencell.cronLaboratorio.controller.MBControllerLaboratorio;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @Modified Fredy G
 */
public class JobCargaArchivos implements Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        
        
        System.out.println("**************************************************** INICIA EJECUCION CRON lABORATORIO *************************************************************************************************************" + new Date());
        MBControllerLaboratorio controllerself = new MBControllerLaboratorio();
        controllerself.ejecutarTareaCargaArchivosLaboratorio();
        System.out.println("---------FINALIZA CRON CARGA LABORATORIO ---------");
    }
}
