/*(Header: NiLOSTEP / xlSQL)

 Copyright (C) 2004 NiLOSTEP
   NiLOSTEP Information Sciences
   http://nilostep.com
   nilo.de.roock@nilostep.com

 This program is free software; you can redistribute it and/or modify it 
 under the terms of the GNU General Public License as published by the Free 
 Software Foundation; either version 2 of the License, or (at your option) 
 any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 more details. You should have received a copy of the GNU General Public 
 License along with this program; if not, write to the Free Software 
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package com.nilostep.xlsql.ui;

import java.sql.Driver;
import java.sql.SQLException;

import java.util.Properties;


/**
 * Open command.
 * 
 * @author Jim Caprioli
 */
public class CmdOpen implements IStateCommand {
    private XlUi xldba;

    /**
     * Creates a new instance of this class.
     * 
     * @param dba object
     */
    public CmdOpen(final XlUi dba) {
        xldba = dba;
    }

    /**
     * Open database.
     * 
     * @return state connected
     * 
     */
    public final int execute() {
        int ret = 0;

        // assume: commandline not null
        try {
            String database;

            if (xldba.commandline.hasOption("o")) {
                database = xldba.instance.getDatabase();
            } else { // assume "open"
                database = xldba.commandline.getOptionValue("open");
                xldba.instance.setDatabase(database);
            }

            String driver = "com.nilostep.xlsql.jdbc.xlDriver";
            Driver d = (Driver) Class.forName(driver).newInstance();
            ExcelDriver ed = new ExcelDriver(d);
            String protocol = "jdbc:nilostep:excel";
            String url = protocol + ":" + database;
            xldba.con = ed.connect(url, new Properties());
            ret = xldba.OPEN;
            System.out.println(database + " open");
        } catch (ClassNotFoundException nfe) {
            System.out.println("driver not found. Classpath set ?");
        } catch (InstantiationException ie) {
            System.out.println("ERR: while instantiating. ???");
        } catch (IllegalAccessException iae) {
            System.out.println("ERR: illegal access. Privileges?");
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage() + " :" + sqe.getSQLState());
        } catch (Exception xe) {
            System.out.println(xe.getMessage()
                               + "'... use xldba `ping` to establish cause");
        } finally {
            System.out.println("");
        }
        return ret;
    }
}

