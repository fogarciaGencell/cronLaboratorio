/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos;

import gencell.croncargaarchivos.controller.MBControllerSelfdecode;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author JOSEPH
 */
public class JobCargaArchivos implements Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        /*System.out.println("**************************************************** INICIA EJECUCION *************************************************************************************************************" + new Date());
        System.out.println("---------INICIO CRON CARGA ARCHIVOS VARSOME---------");
        MBController controller = new MBController();
        controller.ejecutarTareaCargaArchivosVarsome();
        System.out.println("---------FINALIZA CRON CARGA VARSOME---------");

        System.out.println("**************************************************** INICIA EJECUCION SANITAS *************************************************************************************************************" + new Date());
        System.out.println("---------INICIO CRON CARGA ARCHIVOS VARSOME-SANITAS ---------");
        MBControllerSanitas controllerSanitas = new MBControllerSanitas();
        controllerSanitas.ejecutarTareaCargaArchivosSanitas();
        System.out.println("---------FINALIZA CRON CARGA VARSOME-SANITAS ---------");*/
        
        System.out.println("**************************************************** INICIA EJECUCION SELFDECODE *************************************************************************************************************" + new Date());
        System.out.println("---------INICIO CRON CARGA ARCHIVOS LABORATORIO---------");
        MBControllerSelfdecode controllerself = new MBControllerSelfdecode();
        controllerself.ejecutarTareaCargaArchivosSelfdecode();
        System.out.println("---------FINALIZA CRON CARGA SELFDECODE ---------");
        
//        System.out.println("**************************************************** INICIA EJECUCION LABORATORIO *************************************************************************************************************" + new Date());
//        System.out.println("---------INICIO CRON CARGA ARCHIVOS LABORATORIO---------");
//        MBControllerLaboratorio controllerLaboratorio = new MBControllerLaboratorio();
//        controllerLaboratorio.ejecutarTareaCargaArchivosLaboratorio();
//        System.out.println("---------FINALIZA CRON CARGA LABORATORIO ---------");
    }
}
