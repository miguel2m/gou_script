/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.model.script;

import tmve.local.model.IpPath;
import tmve.local.model.Iprt;
import tmve.local.model.Sctplnk;


/**
 *
 * @author Miguelangel
 */
public class NodeGouScriptDAO {
    private Iprt iprt;
    private IpPath ippath;
    private Sctplnk sctplnk;

    public NodeGouScriptDAO(Iprt iprt, IpPath ippath, Sctplnk sctplnk) {
        this.iprt = iprt;
        this.ippath = ippath;
        this.sctplnk = sctplnk;
    }

    public Iprt getIprt() {
        return iprt;
    }

    public void setIprt(Iprt iprt) {
        this.iprt = iprt;
    }

    public IpPath getIppath() {
        return ippath;
    }

    public void setIppath(IpPath ippath) {
        this.ippath = ippath;
    }

    public Sctplnk getSctplnk() {
        return sctplnk;
    }

    public void setSctplnk(Sctplnk sctplnk) {
        this.sctplnk = sctplnk;
    }
    
    
}
