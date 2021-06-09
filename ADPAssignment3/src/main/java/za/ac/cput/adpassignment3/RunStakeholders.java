/**
 *
 * @author zelin
 */
package za.ac.cput.adpassignment3;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import za.ac.cput.ADPAssignment3.Customer;
import za.ac.cput.ADPAssignment3.Supplier;

/**
 *
 * @author
 */
public class RunStakeholders {
    private final String stakeholderOutput = "stakeholder.ser";
    FileWriter fw;
    BufferedWriter buf;
    InputStream instream;
    ObjectInputStream objstream;
   
    public void writeFile(String writeFile)
    {
        try
        {
            fw = new FileWriter(new File(writeFile));
            fw.write(writeFile);
            fw.close();
            System.out.println(" Has been created"+ writeFile );
            
           
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
            System.exit(1);
        }
    }
    private ArrayList<Customer> cList()
    {
     ArrayList<Customer> customers = new ArrayList<>();
     try
     {
        instream = new FileInputStream(stakeholderOutput);
        objstream = new ObjectInputStream(instream);
            while (true)
            {
                Object obj = objstream .readObject();
                if (obj instanceof Customer)
                {
                    customers.add((Customer) obj);
                }
            }
           
        } catch (EOFException eofe)
        {
           
        } catch (IOException | ClassNotFoundException e)
        {
           e.printStackTrace();
           System.exit(1);
           
        } finally
         //closeFile();
        {
            try
            {
               fw.close();
                objstream.close();
               
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if (!customers.isEmpty())
        {
            Collections.sort(customers,
                    (Customer c1, Customer c2) ->
                     c1.getStHolderId().compareTo(c2.getStHolderId())
            );
        }
        return customers;
    }
    private void customerOutPut()
    {
        String formula = "%s\t%-10s\t%-10s\t%-10s\t%-10s\n";
        String line = "===========================================================\n";
       
        try
        {  
            System.out.print( "======================= CUSTOMERS =========================\n");
           
            System.out.printf(formula, "ID", "Name", "Surname",
                    "Date Of Birth", "Age");
           
            System.out.print(line);
           
            for (int i = 0; i < cList().size(); i++)
            {  
                System.out.printf(
                        formula,
                        cList().get(i).getStHolderId(),
                        cList().get(i).getFirstName(),
                        cList().get(i).getSurName(),
                        formatDate(cList().get(i).getDateOfBirth()),
                        calculateAge(cList().get(i).getDateOfBirth())
                        
                );
            }
           
           System.out.print("\nNumber of customers who can rent: " + canRent());
           System.out.println("\nNumber of customers who cannot rent: "+ canNotRent());
           
        } catch (Exception e)
        {
           e.printStackTrace();
        }
    }
    private String formatDate(String time)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd MMM yyyy",
                Locale.ENGLISH);

        LocalDate parTome = LocalDate.parse(time);
        return parTome.format(formatter);
    }
   
    private int calculateAge(String time)
    {
        LocalDate parseDob = LocalDate.parse(time);
        int dobYear  = parseDob.getYear();
        ZonedDateTime todayDate = ZonedDateTime.now();
        int currentYear = todayDate.getYear();
        return currentYear - dobYear;
    }
   
    private int canRent()
    {
        int canRent = 0;
        for (int i = 0; i < cList().size(); i++)
        {
            // check to see who can rent
            if (cList().get(i).getCanRent())
            {
                canRent += 1;
            }
        }
        return canRent;
    }
   
    private int canNotRent()
    {
        int canNotRent = 0;
        for (int i = 0; i < cList().size(); i++)
        {
            if (!cList().get(i).getCanRent())
            {
                canNotRent += 1;
            }
        }
        return canNotRent;
    }
    
    
    
    private ArrayList<Supplier> sList()
    {
        ArrayList<Supplier> suppliers = new ArrayList<>();
       
        try
        {
            instream = new FileInputStream(new File(stakeholderOutput));
            objstream = new ObjectInputStream(instream);
            while (true)
            {
                Object obj = objstream.readObject();
                if (obj instanceof Supplier)
                {
                    suppliers.add((Supplier) obj);
                }
            }
           
        } catch (EOFException eofe)
        {
         
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                instream.close();
                objstream.close();
               
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if (!suppliers.isEmpty())
        {
            Collections.sort(
            suppliers,
            (Supplier su, Supplier sou) ->
            su.getName().compareTo(sou.getName())
            );
        }
        return suppliers;
    }
   
    private void supplierOutPut()
    {   String formula = "%s\t%-20s\t%-10s\t%-10s\n";
        String line = "===========================================================\n";
        try
        {
            System.out.print("======================= SUPPLIERS =========================\n");
            System.out.printf(formula, "ID", "Name", "Prod Type", "Description");
            System.out.print(line);
            for (int i = 0; i < sList().size(); i++)
            {
               System.out.printf(
                        formula,
                        sList().get(i).getStHolderId(),
                        sList().get(i).getName(),
                        sList().get(i).getProductType(),
                        sList().get(i).getProductDescription()
                );
            }

           
        } catch (Exception e)
        {
 
            e.printStackTrace();
        }
    }
   
    public void closeFile(String myFile)
    {
        try
        {   
            fw.write(myFile);
            fw.close();
            System.out.println(myFile + " has been closed");

        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        RunStakeholders sheetprint = new RunStakeholders();
        sheetprint.writeFile("customerOutFile.txt");
        sheetprint.customerOutPut();
        sheetprint.closeFile("customerOutFile.txt");
        System.out.println("---------------------------------------------------");
        sheetprint.writeFile("supplierOutFile.txt");
        sheetprint.supplierOutPut();
        sheetprint.closeFile("supplierOutFile.txt");

    }
}