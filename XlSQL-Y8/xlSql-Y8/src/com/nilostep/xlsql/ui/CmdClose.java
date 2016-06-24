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
 * Close database.
 * 
 * @author Jim Caprioli
 */
public class CmdClose implements IStateCommand {
    private XlUi xldba;

    /**
     * Creates a new instance of this class.
     * 
     * @param dba object
     */
    public CmdClose(final XlUi dba) {
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
        try {
            // if a JDBC connection was established: commit changes
            if (xldba.con != null) {
                xldba.con.close();
                xldba.con = null;
                System.out.println("JDBC connection closed");
            }
            else {
                System.out.println("Exporter closed");
            }

            
        } catch (SQLException sqe) {
            System.out.println("SQL engine error: " + sqe.getMessage() + ":"
                               + sqe.getSQLState() + " Changes may be lost..?");
        } finally {
            ret = xldba.CONNECTED;
            System.out.println("");
        }
        
        return ret;
    }
}

