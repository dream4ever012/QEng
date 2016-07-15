package TableEngines;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

import org.h2.api.TableEngine;
import org.h2.command.ddl.CreateTableData;
import org.h2.engine.Session;
import org.h2.index.BaseIndex;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.index.PageDataIndex;
import org.h2.index.SingleRowCursor;
import org.h2.message.Trace;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.table.TableBase;
import org.h2.table.TableFilter;
import org.h2.util.New;
import org.h2.value.DataType;


//an XLS table Factory
public class TestXLSTableEngine implements TableEngine{

	//TODO: determine if I should use a global
	//public static CreateTableData createTableData;


	//XLS table implementation
	private static class TestXLSTable extends TableBase {


	    private Index scanIndex;
	    private long rowCount;
	    private volatile Session lockExclusiveSession;
	    private HashSet<Session> lockSharedSessions = New.hashSet();

	    /**
	     * The queue of sessions waiting to lock the table. It is a FIFO queue to
	     * prevent starvation, since Java's synchronized locking is biased.
	     */
	    private final ArrayDeque<Session> waitingSessions = new ArrayDeque<Session>();
	   // private final Trace traceLock;
	    private final ArrayList<Index> indexes = New.arrayList();
	    private long lastModificationId;
	    private boolean containsLargeObject;
	  //  private final PageDataIndex mainIndex;
	    private int changesSinceAnalyze;
	    private int nextAnalyze;
	    private Column rowIdColumn;

	    volatile Row row;
	    
	    
	  //TODO: implement the scan.
	  		public TestXLSTable(CreateTableData data) {
	  			super(data);
	  			scanIndex = new XLSScan(this);
	  			// TODO adapt this implementation for XLS
	  			regTableConst(data);
	  			
	  		}
	  		
	  		private void regTableConst(CreateTableData data)
	  		{
	  	/*		nextAnalyze = database.getSettings().analyzeAuto;
	  	        this.isHidden = data.isHidden;
	  	        for (Column col : getColumns()) {
	  	            if (DataType.isLargeObject(col.getType())) {
	  	                containsLargeObject = true;
	  	            }
	  	        }
	  	        if (data.persistData && database.isPersistent()) {
	  	            mainIndex = new PageDataIndex(this, data.id,
	  	                    IndexColumn.wrap(getColumns()),
	  	                    IndexType.createScan(data.persistData),
	  	                    data.create, data.session);
	  	            scanIndex = mainIndex;
	  	        } else {
	  	          //  mainIndex = null;
	  	         //   scanIndex = new ScanIndex(this, data.id,
	  	         //           IndexColumn.wrap(getColumns()), IndexType.createScan(data.persistData));
	  	        }
	  	        indexes.add(scanIndex);
	  	       // traceLock = database.getTrace(Trace.LOCK);
*/	  		}
	    
	    
		
		public class XLSScan extends BaseIndex {

			XLSScan(Table table) {
				initBaseIndex(table, table.getId(), table.getName() + "_SCAN",
						IndexColumn.wrap(table.getColumns()), IndexType.createScan(false));
			}

			@Override
			public long getRowCountApproximation() {
				return table.getRowCountApproximation();
			}

			@Override
			public long getDiskSpaceUsed() {
				return table.getDiskSpaceUsed();
			}

			@Override
			public long getRowCount(Session session) {
				return table.getRowCount(session);
			}

			@Override
			public void checkRename() {
				// do nothing
			}

			@Override
			public void truncate(Session session) {
				// do nothing
			}

			@Override
			public void remove(Session session) {
				// do nothing
			}

			@Override
			public void remove(Session session, Row r) {
				// do nothing
			}

			@Override
			public boolean needRebuild() {
				return false;
			}

			@Override
			public double getCost(Session session, int[] masks,
					TableFilter[] filters, int filter, SortOrder sortOrder,
					HashSet<Column> allColumnsSet) {
				return 0;
			}

			@Override
			public Cursor findFirstOrLast(Session session, boolean first) {
				return new SingleRowCursor(row);
			}

			@Override
			public Cursor find(Session session, SearchRow first, SearchRow last) {
				return new SingleRowCursor(row);
			}

			@Override
			public void close(Session session) {
				// do nothing
			}

			@Override
			public boolean canGetFirstOrLast() {
				return true;
			}

			@Override
			public void add(Session session, Row r) {
				// do nothing
			}

		}

		

		@Override
		public boolean lock(Session session, boolean exclusive, boolean forceLockEvenInMvcc) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close(Session session) {
			// TODO Auto-generated method stub

		}
		
		@Override
		public void unlock(Session s) {
			// TODO Auto-generated method stub

		}

		@Override
		public Index addIndex(Session session, String indexName, int indexId, IndexColumn[] cols, IndexType indexType,
				boolean create, String indexComment) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void removeRow(Session session, Row row) {
			// TODO Auto-generated method stub

		}




		@Override
		public void truncate(Session session) {
			// TODO Auto-generated method stub

		}




		@Override
		public void addRow(Session session, Row row) {
			// TODO Auto-generated method stub

		}




		@Override
		public void checkSupportAlter() {
			// TODO Auto-generated method stub

		}




		@Override
		public String getTableType() {
			// TODO Auto-generated method stub
			return null;
		}




		@Override
		public Index getScanIndex(Session session) {
			// TODO Auto-generated method stub
			return null;
		}




		@Override
		public Index getUniqueIndex() {
			// TODO Auto-generated method stub
			return null;
		}




		@Override
		public ArrayList<Index> getIndexes() {
			// TODO Auto-generated method stub
			return null;
		}




		@Override
		public boolean isLockedExclusively() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public long getMaxDataModificationId() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isDeterministic() {
			// TODO Auto-generated method stub
			return false;
		}




		@Override
		public boolean canGetRowCount() {
			// TODO Auto-generated method stub
			return false;
		}




		@Override
		public boolean canDrop() {
			// TODO Auto-generated method stub
			return false;
		}




		@Override
		public long getRowCount(Session session) {
			// TODO Auto-generated method stub
			return 0;
		}




		@Override
		public long getRowCountApproximation() {
			// TODO Auto-generated method stub
			return 0;
		}




		@Override
		public long getDiskSpaceUsed() {
			// TODO Auto-generated method stub
			return 0;
		}




		@Override
		public void checkRename() {
			// TODO Auto-generated method stub

		}


	}

	@Override
	public Table createTable(CreateTableData data) {
		return new TestXLSTable(data);
	}


}
