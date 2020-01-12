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
    String codigo;
    String msj;
    public GouScriptException(String codigo, String msj) {
        super(msj);
        this.codigo = codigo;
        this.msj = msj;
    }  

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    
    
    
}
