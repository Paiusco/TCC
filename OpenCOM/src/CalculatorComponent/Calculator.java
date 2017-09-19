/*
 * Calculator.java
 *
 * OpenCOMJ is a flexible component model for reconfigurable reflection developed at Lancaster University.
 * Copyright (C) 2005 Paul Grace
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, 
 * write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package CalculatorComponent;
import OpenCOM.*;
import AdderComponent.*;
import SubtractComponent.*;
import java.util.*;
import java.lang.reflect.*;

/**
 * Calculator component supporting arithmetic plug-in components.
 * @author  Paul Grace
 * @version 1.3
 */

public class Calculator extends OpenCOMComponent implements ICalculator, IConnections, ILifeCycle, IUnknown, IMetaInterface {
    

    /**
     * Requires Interface of type IAdd.
     */
    public OCM_SingleReceptacle<IAdd> m_PSR_IAdd;
    
    /**
     * Requires Interface of type ISubtract.
     */
    public OCM_SingleReceptacle<ISubtract> m_PSR_ISubtract;

    /** Creates a new instance of Calculator */
    public Calculator(IUnknown binder) {
        super(binder);
        m_PSR_IAdd = new OCM_SingleReceptacle<IAdd>(IAdd.class);
        m_PSR_ISubtract = new OCM_SingleReceptacle<ISubtract>(ISubtract.class);
    }
    
    //Interface ICalculator
    public int add(int a, int b) {
        return m_PSR_IAdd.m_pIntf.add(a, b);
    }
    
    public int subtract(int a, int b) {
      return m_PSR_ISubtract.m_pIntf.subtract(a, b);
    }
    
    public String display(String message) {
        String returnStr = message.concat(":: From Calculator");
	return returnStr;
    }
    
    public void Wait(long seconds) {
        long time0 = System.currentTimeMillis();
        long time1 = -1;
        while(time1 < (seconds*1000)){
            time1 = System.currentTimeMillis()-time0;
        }
    }  

    // IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("AdderComponent.IAdd")){
		return m_PSR_IAdd.connectToRecp(pSinkIntf, riid, provConnID);
	}
        else if(riid.toString().equalsIgnoreCase("SubtractComponent.ISubtract")){
		return m_PSR_ISubtract.connectToRecp(pSinkIntf, riid, provConnID);
	}

	return false;
    }
    
    public boolean disconnect(String riid, long connID) {
        
	if(riid.toString().equalsIgnoreCase("AdderComponent.IAdd")){
		return m_PSR_IAdd.disconnectFromRecp(connID);
	}
        else if(riid.toString().equalsIgnoreCase("SubtractComponent.ISubtract")){
		return m_PSR_ISubtract.disconnectFromRecp(connID);
	}
	return false;
    }
    
    // ILifeCycle Interface
    public boolean shutdown() {
        return true;
    }
    
    public boolean startup(Object pIOCM) {
        return true;
    }  
    
}