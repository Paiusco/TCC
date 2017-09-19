/*
 * MetaInterface.java
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

package OpenCOM;
import java.lang.reflect.*;
import java.util.*;
/**
 * Each OpenCOM component contains this object to implement the interface meta-model.
 * Methods for introspecting interfaces, and receptacles are available. Furthermore,
 * meta-data can be attached and read as name-value pairs from both the interfaces
 * and the receptacles.
 *
 * @author  Paul Grace.
 * @version 1.3
 */
public class MetaInterface{
    
    private IOpenCOM m_pRTintf;
    private IUnknown m_Comp;
    
    /** Creates a new instance of MetaInterface */
    public MetaInterface(IOpenCOM pRTintf, IUnknown component) {
        m_pRTintf = pRTintf;
        m_Comp = component;
    }
    
    /** 
     * Get the interfaces (as Java classes) of a component and stores them in the given vector.
     * The operation is recursive to find inherited interfaces.
     * @param compClass The class of the component.
     * @param intfList The Vector to be filled with the components interfaces.
     */
    public void GetInterfaces(Class c, Vector<Class> a){
        Class[] theInterfaces = c.getInterfaces();
        if(theInterfaces.length==0){
            //base case
            return;
        }
        else{
            for(int i=0; i<theInterfaces.length; i++){
                boolean found=false;
                for(int j=0; j<a.size();j++){
                    if(a.get(j)==theInterfaces[i])
                        found=true;
                }
                if(!found)
                    a.add(theInterfaces[i]);
                GetInterfaces(theInterfaces[i], a);
            }
        }
    }
    
    public void ReadInterfaceNames(Class c, Vector<String> a){
        Class[] theInterfaces = c.getInterfaces();
        if(theInterfaces.length==0){
            //base case
            return;
        }
        else{
            for(int i=0; i<theInterfaces.length; i++){
                boolean found=false;
                for(int j=0; j<a.size();j++){
                    String VectorString = (String) a.get(j);
                    if(VectorString.equalsIgnoreCase(theInterfaces[i].getName()))
                        found=true;
                }
                if(!found)
                    a.add(theInterfaces[i].getName());
                ReadInterfaceNames(theInterfaces[i], a);
            }
        }
    }
    
    /** 
     * Get the interfaces (as Java classes) of a component and stores them in the given vector.
     * @param compRef The reference to the instance of the component.
     * @param intfList The Vector to be filled with the components interfaces.
     * @return An integer describing the number of interfaces on this component.
     */
    public int enumIntfs(Object o, java.util.Vector<Class> ppIntf) {
        Class d = o.getClass();
        GetInterfaces(d, ppIntf);
        return ppIntf.size();
    }
    
    
    /** 
     * Get the receptacles of a component and stores them in the given vector.
     * @param compRef The reference to the instance of the component.
     * @param intfList The Vector to be filled with the component's receptacles.
     * @return An integer describing the number of receptacles on this component.
     * @see OpenCOM.OCM_RecpMetaInfo_t
     */
    public int enumRecps(IUnknown comp, java.util.Vector<OCM_RecpMetaInfo_t> ppRecpMetaInfo) {
        
        int count=0;
        // Receptacles are public fields in each component - so we first read the component's fields
        Class compType = comp.getClass();
        Field[] publicFields = compType.getFields();
        
        String fieldType=null;
        Class typeClass=null;
        String type;
        Field[] publicFields1 =null;

        for (int i = 0; i < publicFields.length; i++) {
            typeClass = publicFields[i].getType();
            fieldType = typeClass.getName();
            
            type = null;
            if(fieldType.toString().equalsIgnoreCase("OpenCOM.OCM_SingleReceptacle")){
                // add to vector
                 type= "OCM_SINGLE_RECEPTACLE";
            }
            if(fieldType.toString().equalsIgnoreCase("OpenCOM.OCM_MultiReceptacle")){
                // add to vector
                 type= "OCM_MULTI_RECEPTACLE";
            }
            if(fieldType.toString().equalsIgnoreCase("OpenCOM.OCM_MultiReceptacleParallel")){
                // add to vector
                 type= "OCM_MULTI_RECEPTACLE";
            }
            if(fieldType.toString().equalsIgnoreCase("OpenCOM.OCM_MultiReceptacleContext")){
                // add to vector
                 type= "OCM_MULTI_RECEPTACLE";
            }
            if(type!=null){
                try{
                    // Extract the 
                    Object Value=publicFields[i].get(comp);
                    Class c1 = Value.getClass();
                    publicFields1 = c1.getFields();

                    String a= (String) publicFields1[0].get(Value).toString();
		    String iid = a.substring(10);
                    OCM_RecpMetaInfo_t pp= new OCM_RecpMetaInfo_t(iid, type);
                    ppRecpMetaInfo.add(pp);
                }
                catch(Exception e){
                    e.printStackTrace();
                    return count;
                }
                count++;
            }
        }

        return count;
    }
    
    /** 
     * Set the value of a name value pair on either an interface or receptacle.
     * @param pIUnk The reference to the instance of the component the interface or receptacle is hosted on.
     * @param Runtime Reference to the OpenCOM runtime interface.
     * @param iid The type of the interface or receptacle.
     * @param Kind A string which is either "Interface" or "Receptacle".
     * @param Name A string describing the attribute name.
     * @param Type A string describing the attribute type.
     * @param Value An object holding the attribute value.
     * @return A boolean indicating if the attribute value was added.
     */
    public boolean SetAttributeValue(String iid, String Kind, String Name, String Type, Object Value){
        if(Kind.equalsIgnoreCase("Interface")){
            // Get meta interception interface
            IMetaInterception pMetaIc = (IMetaInterception) m_pRTintf.QueryInterface("OpenCOM.IMetaInterception");
            IDelegator pIDel =  pMetaIc.GetDelegator(m_Comp, iid);
            return pIDel.SetAttributeValue(Name, Type, Value);
        }
        else  if(Kind.equalsIgnoreCase("Receptacle")){
            Class c = m_Comp.getClass();
            Field[] publicFields = c.getFields();
            for (int i = 0; i < publicFields.length; i++) {
                Class typeClass = publicFields[i].getType();
                String fieldType = typeClass.getName();

                String type = null;
                if(fieldType.toString().equalsIgnoreCase("OpenCOM.OCM_SingleReceptacle")){
                    try{
                        IReceptacle recp = (IReceptacle) publicFields[i].get(m_Comp);
                        recp.putData(Name, Type, Value);
                        return true;
                    }
                    catch(Exception e){
                        return false;
                    }
                }
            }
        }
        return false;
        
    }
    
    /** 
     * Retrieve the value of a name value pair on either an interface or receptacle.
     * @param pIUnk The reference to the instance of the component the interface or receptacle is hosted on.
     * @param Runtime Reference to the OpenCOM runtime interface.
     * @param iid The type of the interface or receptacle.
     * @param Kind A string which is either "Interface" or "Receptacle".
     * @param Name A string describing the attribute name.
     * @return An object holding the value of the attribute.
     */
    public TypedAttribute GetAttributeValue(String iid, String Kind, String Name){
        if(Kind.equalsIgnoreCase("Interface")){
            IMetaInterception pMetaIc = (IMetaInterception) m_pRTintf.QueryInterface("OpenCOM.IMetaInterception");
            IDelegator pIDel =  pMetaIc.GetDelegator(m_Comp, iid);
            return pIDel.GetAttributeValue(Name);
            
        }
        else  if(Kind.equalsIgnoreCase("Receptacle")){
            Class c = m_Comp.getClass();
            Field[] publicFields = c.getFields();
            for (int i = 0; i < publicFields.length; i++) {
                Class typeClass = publicFields[i].getType();
                String fieldType = typeClass.getName();

                String type = null;
                if(fieldType.toString().equalsIgnoreCase("OpenCOM.OCM_SingleReceptacle")){
                    try{
                        IReceptacle recp = (IReceptacle) publicFields[i].get(m_Comp);
                        return (TypedAttribute) recp.getValue(Name);
                    }
                    catch(Exception e){
                        return null;
                    }
                }
            }
        }
        return null;
    }

    /** 
     * Retrieve all the  name value pairs on either an interface or receptacle.
     * @param pIUnk The reference to the instance of the component the interface or receptacle is hosted on.
     * @param iid The type of the interface or receptacle.
     * @param Kind A string which is either "Interface" or "Receptacle".
     * @return An object holding the value of the attribute.
     */
    public Hashtable<String, TypedAttribute> GetAllValues(String Kind, String iid){
        if(Kind.equalsIgnoreCase("Interface")){
            return null;
        }
        else  if(Kind.equalsIgnoreCase("Receptacle")){
            Class c = m_Comp.getClass();
            Field[] publicFields = c.getFields();
            for (int i = 0; i < publicFields.length; i++) {
                Class typeClass = publicFields[i].getType();
                String fieldType = typeClass.getName();

                String type = null;
                if(fieldType.toString().equalsIgnoreCase("OpenCOM.OCM_SingleReceptacle")){
                    try{
                        IReceptacle recp = (IReceptacle) publicFields[i].get(m_Comp);
                        return recp.getValues();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
