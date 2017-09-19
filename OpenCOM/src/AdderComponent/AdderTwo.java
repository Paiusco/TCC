/*
 * AdderTwo.java
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

package AdderComponent;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOMComponent;
/**
 * Simple component offering methods to add integers and receive messages.
 * @author  Paul Grace
 * @version 1.3
 */
public class AdderTwo extends OpenCOMComponent implements IUnknown, IAdd, IParallel, IMetaInterface, ILifeCycle {
    
    private String Owner;

    /** Creates a new instance of Adder */
    public AdderTwo(IUnknown pRuntime) {
        super(pRuntime);
        Owner = "Joe Bloggs";
    }
    
    // IAdd interface
    public int add(int a, int b) {
        return a+b+8;
    }
    
    // IParallel Interface
    public void ParallelExecution(String x){
        System.out.println("The Owner is: "+Owner);
        System.out.println("The message sent from the calculator is: "+x);
    }

    // ILifeCycle Interface
    public boolean startup(Object pIOCM) {
        SetAttributeValue("AdderComponent.IParallel", "Interface", "Owner", "String", Owner);
        return true;
    }
    
    public boolean shutdown() {
        return true;
    }
    
    public void Test() {
    }
    
}
