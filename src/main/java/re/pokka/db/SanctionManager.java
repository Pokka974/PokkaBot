/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.db;


import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import re.pokka.pokkabot.Sanction;

public class SanctionManager
{

    private byte[] keyBytes;
    private DatabaseEntry key;
    private DatabaseEntry data;
    private static SanctionManager m_instance;
    private Cursor cursor = null;

    public SanctionManager()
    {
        SetupEnvironment.get_Instance();
    }
    
    //When the Server start he loading the all Client
    public ArrayList<Sanction> getGSanctionList()
    {

        ArrayList<Sanction> sanctionList = new ArrayList<>();

        try
        {

            // Open the cursor.
            cursor = SetupEnvironment.get_Instance().getSanctionDB().openCursor(null, null);

            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();

            //Load all Gerant from the db and put them in the hashtable
            while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS)
            {

                Sanction sanction = (Sanction) new SanctionBinding().entryToObject(foundData);
                sanctionList.add(sanction);

            }

        } catch (Exception de)
        {
            Logger.getLogger(Sanction.class.getName()).log(Level.SEVERE, "getSanctionList() Error on cursor reading Sanction", de);
        } finally
        {

            try
            {
                // Cursors must be closed.
                cursor.close();

            } catch (Exception io)
            {
                Logger.getLogger(Sanction.class.getName()).log(Level.SEVERE, "getSanctionList() Error on cursor close", io);
            }

        }
        return sanctionList;

    }

    //Return the number of record in db sanction
    public synchronized int getNumberOfSanction()
    {

        try
        {

            int i = 0;

            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();

            // Open the cursor.
            cursor = SetupEnvironment.get_Instance().getSanctionDB().openCursor(null, null);

            while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS)
            {
                i++;
            }

            return i;

        } catch (Exception de)
        {

            Logger.getLogger(Sanction.class.getName()).log(Level.SEVERE, "get_CountOfAllGerant() Error on get", de);

            return 0;
        } finally
        {

            try
            {
                // Cursors must be closed.
                cursor.close();

            } catch (Exception io)
            {

                Logger.getLogger(Sanction.class.getName()).log(Level.SEVERE, "get_CountOfAllGerant() Error on cursor close", io);

            }

        }
    }
    
    
    /**
     *
     * @param k
     * @return
     */
    public synchronized Sanction getSanction(String k)
    {

        try
        {

            // A Sanction key that's known to exist
            keyBytes = k.getBytes("UTF-8");
            // A new DatabaseEntry
            key = new DatabaseEntry(keyBytes);
            // A new DatabaseEntry
            data = new DatabaseEntry();

            cursor = SetupEnvironment.get_Instance().getSanctionDB().openCursor(null, null);

            OperationStatus status = cursor.getSearchKey(key, data, LockMode.DEFAULT);

            if (status == OperationStatus.SUCCESS)
            {

                Sanction sanction = (Sanction) new SanctionBinding().entryToObject(data);
                return sanction;

            } else
            {
                return null;
            }

        } catch (Exception io)
        {
            Logger.getLogger(Sanction.class.getName()).log(Level.SEVERE, "Error on cursor reading Sanction, with key : {0} : {1}", new Object[]
            {
                k, io
            });
            return null;
        } finally
        {

            try
            {
                cursor.close();
            } catch (DatabaseException ex)
            {
                Logger.getLogger(Sanction.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    
    /**
     *
     * @param dbkey
     * @param client
     */
    public synchronized void putSanction(String dbkey, Sanction sanction)
    {
        
        try
        {

            // The key data.
            keyBytes = dbkey.getBytes("UTF-8");

            // The DataBaseEntry for the Key
            key = new DatabaseEntry(keyBytes);

            data = new DatabaseEntry();


            TupleBinding binding = new SanctionBinding();
            binding.objectToEntry(sanction, data);

            // Store the object in db 
            cursor = SetupEnvironment.get_Instance().getSanctionDB().openCursor(null, null);
            OperationStatus status = cursor.put(key, data);

            if (status == OperationStatus.SUCCESS)
            {
                Logger.getLogger(Sanction.class.getName()).log(Level.INFO, "Operation success for PUT OPERATION for Sanction n\u00b0 {0}\n", key);
                System.out.print("Ajout de : " + sanction.toString());
            } else
            {
                Logger.getLogger(Sanction.class.getName()).log(Level.SEVERE, "Operation unsuccess for PUT OPERATION for Sanction n\u00b0 {0}\n", key);
            }
            cursor.close();

            //Sync DB
            SetupEnvironment.get_Instance().getEnvironment().sync();
            Logger.getLogger(Sanction.class.getName()).log(Level.INFO, "Success for Synchronized Data Sanction" + "\n");

        } catch (Exception en)
        {

            Logger.getLogger(Sanction.class.getName()).log(Level.SEVERE, "Operation PUT Error for Sanction {0}", en);
        }

    }

    /**
     *
     * @param k
     */
    public synchronized void DeleteSanction(String k)
    {

        try
        {

            // A Gerant key that's known to exist
            keyBytes = k.getBytes("UTF-8");
            // A new DatabaseEntry
            key = new DatabaseEntry(keyBytes);
            // A new DatabaseEntry
            data = new DatabaseEntry();

            cursor = SetupEnvironment.get_Instance().getSanctionDB().openCursor(null, null);

            OperationStatus status = cursor.getSearchKey(key, data, LockMode.DEFAULT);

            if (status == OperationStatus.SUCCESS)
            {
                cursor.delete();
            }

            cursor.close();

        } catch (Exception io)
        {
            Logger.getLogger(Sanction.class.getName()).log(Level.SEVERE, "DeleteSanctionFromDB() Error on cursor reading Sanction, with key : {0} : {1}", new Object[]
            {
                k, io
            });
        }

    }

    /**
     *
     */
    public synchronized void deleteAllSanction()
    {

        int i = 0;

        try
        {

            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();

            // Open the cursor.
            cursor = SetupEnvironment.get_Instance().getSanctionDB().openCursor(null, null);

            while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS)
            {
                cursor.delete();
                i++;
            }

        } catch (Exception de)
        {

            Logger.getLogger(Sanction.class.getName()).log(Level.SEVERE, "DeleteAllSanction() Error ", de);

        } finally
        {

            try
            {

                // Cursors must be closed.
                cursor.close();

                //Sync the env of db
                SetupEnvironment.get_Instance().getEnvironment().sync();

            } catch (Exception io)
            {

                Logger.getLogger(Sanction.class.getName()).log(Level.SEVERE, "DeleteAllSanction() Error on cursor close operation : ", io);

            }

        }

    }

    public static synchronized SanctionManager get_Instance()
    {

        if (m_instance == null)
        {
            m_instance = new SanctionManager();
        }

        return m_instance;

    }

}
