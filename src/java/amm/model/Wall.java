/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.model;

/**
 *
 * @author aless
 */
public class Wall {
    
    protected int wallid;
    protected String owner;
    protected String name;

    
    public int getWallid() {
        return wallid;
    }

    public void setWallid(int wallid) {
        this.wallid = wallid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
       
}
