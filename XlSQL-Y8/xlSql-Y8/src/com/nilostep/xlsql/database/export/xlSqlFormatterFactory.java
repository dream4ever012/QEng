/*(Header: NiLOSTEP / xlSQL)

 Copyright (C) 2004 NiLOSTEP
   NiLOSTEP Information Sciences
   http://nilostep.com
   nilo.de.roock@nilostep.com

 This program is free software; you can redistribute it and/or modify it under 
 the terms of the GNU General Public License as published by the Free Software 
 Foundation; either version 2 of the License, or (at your option) any later 
 version.

 This program is distributed in the hope that it will be useful, 
 but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 more details. You should have received a copy of the GNU General Public License 
 along with this program; if not, write to the Free Software Foundation, 
 Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
/*
 * xlDatabaseFactory.java
 *
 * Created on 9 augustus 2004, 18:18
 */
package com.nilostep.xlsql.database.export;


import java.sql.*;


/**
 * DOCUMENT ME!
 * 
 * @author Jim Caprioli
 */
public class xlSqlFormatterFactory {
    /**
     * DOCUMENT ME!
     *
     * @param type DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static ASqlFormatter create(String type) {
        ASqlFormatter ret = null;

        if (type.equals("hsqldb")) {
            ret = new xlHsqldbFormatter();
        } else if (type.equals("mysql")) {
            ret = new xlMysqlFormatter();
        } else {
            throw new IllegalArgumentException();
        }

        return ret;
    }
}