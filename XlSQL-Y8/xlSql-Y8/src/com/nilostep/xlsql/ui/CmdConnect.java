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

import com.nilostep.xlsql.database.xlException;
import com.nilostep.xlsql.database.xlInstance;


/**
 * Connect command.
 * 
 * @author Jim Caprioli
 */
public class CmdConnect implements IStateCommand {
    private XlUi xldba;

    /**
     * Creates a new instance of this class.
     * @param dba object
     */
    public CmdConnect(final XlUi dba) {
        xldba = dba;
    }

    /**
     * Establishes a connection to an instance.
     * 
     * @return state connected
     * 
     * @throws xlException
     */
    public final int execute() {
        int ret = 0;

        try {
            if (xldba.commandline != null) {
                xldba.instance = xlInstance.getInstance(
                                         xldba.commandline.getOptionValue(
                                                 "c"));
                System.out.println("Connected\n");
                ret = xldba.CONNECTED;
            } else {
                throw new xlException("Commandline?..");
            }
        } catch (xlException xle) {
            System.out.println("ERR: " + xle.getMessage());
        }

        return ret;
    }
}

