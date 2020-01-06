/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Miguelangel
 */
public class ExecutionTime {
    /**
     * Parser start time.
     *
     * @since 1.0.4
     * @version 1.0.0
     */
    final long startTime ; 
    
    public ExecutionTime (){
        this.startTime = System.currentTimeMillis();
    }
    public String getDate(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
    /**
     * Print program's execution time.
     *
     * @since 1.0.0
     */
    public  String getExecutionTime() {
        float runningTime = System.currentTimeMillis() - startTime;

        String s = " completed. ";
        s = s + "Total time:";

        //Get hours
        if (runningTime > 1000 * 60 * 60) {
            int hrs = (int) Math.floor(runningTime / (1000 * 60 * 60));
            s = s + hrs + " hours ";
            runningTime = runningTime - (hrs * 1000 * 60 * 60);
        }

        //Get minutes
        if (runningTime > 1000 * 60) {
            int mins = (int) Math.floor(runningTime / (1000 * 60));
            s = s + mins + " minutes ";
            runningTime = runningTime - (mins * 1000 * 60);
        }

        //Get seconds
        if (runningTime > 1000) {
            int secs = (int) Math.floor(runningTime / (1000));
            s = s + secs + " seconds ";
            runningTime = runningTime - (secs / 1000);
        }

        //Get milliseconds
        if (runningTime > 0) {
            int msecs = (int) Math.floor(runningTime / (1000));
            s = s + msecs + " milliseconds ";
            runningTime = runningTime - (msecs / 1000);
        }

        return (s);
    }
}
