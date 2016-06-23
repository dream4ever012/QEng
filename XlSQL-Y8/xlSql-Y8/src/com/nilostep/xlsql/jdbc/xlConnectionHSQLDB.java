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
 * xlConnectionHSQL.java
 *
 * Created on 5 juli 2004, 23:59
 *
 * Changed by Csongor Nyulas (csny): safe shutdown of HSQLDB connection
 */
package com.nilostep.xlsql.jdbc;

import com.nilostep.xlsql.database.sql.xlSqlSelectFactory;
import com.nilostep.xlsql.database.sql.xlSqlParserFactory;
import com.nilostep.xlsql.database.export.xlSqlFormatterFactory;
import com.nilostep.xlsql.database.*;

import java.sql.*;


public class xlConnectionHSQLDB extends xlConnection {
    private static final String HSQLDB = "hsqldb";
    
    public xlConnectionHSQLDB(String url, Connection c) throws SQLException {
        dialect = HSQLDB;
        w = xlSqlFormatterFactory.create(HSQLDB);
        URL = url;
        dbCon = c;
        query = xlSqlSelectFactory.create(HSQLDB, dbCon);
        startup();
        xlsql = xlSqlParserFactory.create(HSQLDB, datastore, null);
    }

    public void shutdown() throws Exception {
    	//csny safe shutdown
    	if (! closed) {
	        logger.info("Shutting down HSQLDB ...");
	    	dbCon.createStatement().execute("SHUTDOWN");
	        dbCon.close();
	        closed = true;
            logger.info("HSQLDB is shut down");
    	}
    }
}