/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.model.exception;

/**
 *  EXCEPTION DE GOU SCRIPT
 *  CODIGO 400 = ERROR ATM TRANS
 *  CODIGO 401 = ERROR DSTIP (VRF) NO ESTA ES LA TABLA IPRT DEL NODOB
 *  CODIGO 402 = EL NODEB NO ESTA CARGADA A LA BD
 *  CODIGO 403 = EL NODEB NO SE ENCUENTRA EN LA RNC
 * @author Miguelangel
 */
public class GouScriptException extends Exception{

    public GouScriptException(String codigo, String string) {
        super(codigo+" -> "+string);
    }  
    
}
