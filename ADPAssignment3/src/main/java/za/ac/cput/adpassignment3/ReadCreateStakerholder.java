/**
 *
 * @author Zelino Pestana 218325991
 */
package za.ac.cput.adpassignment3;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import za.ac.cput.ADPAssignment3.Customer;
import za.ac.cput.ADPAssignment3.Supplier;

public class ReadCreateStakerholder {
    
private ObjectInputStream input; 

Customer customer; 
Customer[] customerArray = new Customer[6];
Supplier supplier;
Supplier[] supplierArray = new Supplier[5];

public void openFile (){
    try {        
    input = new ObjectInputStream( new FileInputStream( "stakeholder.ser" ) );
    }
    catch (IOException ioe){
        System.out.println("error opening ser file: " + ioe.getMessage());
        System.exit(1);
        }
    }
    
public void closeFile(){
    try{
    input.close( ); 
        }
    catch (IOException ioe){            
        System.out.println("error closing ser file: " + ioe.getMessage());
        System.exit(1);
        }        
    }      

    public void readObject (){
        try{
          while(true){
                customer = (Customer)input.readObject();
                System.out.println(customer);
                supplier = (Supplier)input.readObject();
                System.out.println(supplier);
            }//end while
        }//end try 
        
        catch(EOFException eofe){
            System.out.println("End Of File reached");
            
        } catch (IOException | ClassNotFoundException ieo) {
         System.out.println("ERROR reading ser file" + ieo);
    }
        finally{
        closeFile();
        System.out.println("File has been closed");
        
        }
          
    
    }
    
}
