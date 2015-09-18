package indoorquery;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.StringTokenizer;

import indoorquery.RTreeLoad.MyVisitor;
import spatialindex.rtree.RTree;
import spatialindex.spatialindex.IData;
import spatialindex.spatialindex.INode;
import spatialindex.spatialindex.ISpatialIndex;
import spatialindex.spatialindex.IVisitor;
import spatialindex.spatialindex.Point;
import spatialindex.spatialindex.Region;
import spatialindex.storagemanager.DiskStorageManager;
import spatialindex.storagemanager.IBuffer;
import spatialindex.storagemanager.IStorageManager;
import spatialindex.storagemanager.PropertySet;
import spatialindex.storagemanager.RandomEvictionsBuffer;

public class RTreeLoad {
	
	//public int[] childnum;
	//public double[] px1,px2,py1,py2;
	public LeafStat[] ls;
	
	public static void main(String[] args)
	{	
		new RTreeLoad("input_hos_3d_data2.txt","tree",20,"10NN");	
	}
	
	public RTreeLoad(String input, String output, int capacity, String querytype){
		LineNumberReader lr = null;
		try{
			try
			{			
				lr = new LineNumberReader(new FileReader(input));
			}
			catch (FileNotFoundException e)
			{
				System.err.println("Cannot open data file " + input + ".");
				System.exit(-1);
			}
			PropertySet ps = new PropertySet();

			Boolean b = new Boolean(true);
			ps.setProperty("Overwrite", b);
			//overwrite the file if it exists.

			ps.setProperty("FileName", output);
			// .idx and .dat extensions will be added.

			Integer i = new Integer(4096);
			ps.setProperty("PageSize", i);
			// specify the page size. Since the index may also contain user defined data
			// there is no way to know how big a single node may become. The storage manager
			// will use multiple pages per node if needed. Off course this will slow down performance.
			IStorageManager diskfile = new DiskStorageManager(ps);

			IBuffer file = new RandomEvictionsBuffer(diskfile, 10, false);
			// applies a main memory random buffer on top of the persistent storage manager
			// (LRU buffer, etc can be created the same way).

		// Create a new, empty, RTree with dimensionality 2, minimum load 70%, using "file" as
		// the StorageManager and the RSTAR splitting policy.
			PropertySet ps2 = new PropertySet();
			Double f = new Double(0.7);
			ps2.setProperty("FillFactor", f);

			i = capacity;
			ps2.setProperty("IndexCapacity", i);
			ps2.setProperty("LeafCapacity", i);
			// Index capacity and leaf capacity may be different.

			i = new Integer(3);
			ps2.setProperty("Dimension", i);

			ISpatialIndex tree = new RTree(ps2, file);
			int count = 0;
			int indexIO = 0;
			int leafIO = 0;
			int id, op;
			double x1, x2, y1, y2, z1, z2;
			double[] f1 = new double[3];
			double[] f2 = new double[3];

			long start = System.currentTimeMillis();
			String line = lr.readLine();

			while (line != null)
			{
//			StringTokenizer st = new StringTokenizer(line);
//			op = new Integer(st.nextToken()).intValue();
//			id = new Integer(st.nextToken()).intValue();
//			x1 = new Double(st.nextToken()).doubleValue();
//			y1 = new Double(st.nextToken()).doubleValue();
//			x2 = new Double(st.nextToken()).doubleValue();
//			y2 = new Double(st.nextToken()).doubleValue();
			
				String[] temps = line.split("\t");
				op = new Integer(temps[0]).intValue();
				id = new Integer(temps[1]).intValue();
				x1 = new Double(temps[2]).doubleValue();
				y1 = new Double(temps[3]).doubleValue();
				z1 = new Double(temps[4]).doubleValue();
				x2 = new Double(temps[5]).doubleValue();	
				y2 = new Double(temps[6]).doubleValue();
				z2 = new Double(temps[7]).doubleValue();
		//	System.out.println(op+" "+id+" "+x1+x2+y1+y2);
			

				if (op == 0)
				{
				//delete

					f1[0] = x1; f1[1] = y1; f1[2] = z1;
					f2[0] = x2; f2[1] = y2; f2[2] = z2;
					Region r = new Region(f1, f2);

					if (tree.deleteData(r, id) == false)
					{
						System.err.println("Cannot delete id: " + id + " , count: " + count + ".");
						System.exit(-1);
					}
				}
				else if (op == 1)
				{
				//insert

					f1[0] = x1; f1[1] = y1; f1[2] = z1;
					f2[0] = x2; f2[1] = y2; f2[2] = z2;
					Region r = new Region(f1, f2);

					String data = r.toString();
					// associate some data with this region. I will use a string that represents the
					// region itself, as an example.
					// NOTE: It is not necessary to associate any data here. A null pointer can be used. In that
					// case you should store the data externally. The index will provide the data IDs of
					// the answers to any query, which can be used to access the actual data from the external
					// storage (e.g. a hash table or a database table, etc.).
					// Storing the data in the index is convinient and in case a clustered storage manager is
					// provided (one that stores any node in consecutive pages) performance will improve substantially,
					// since disk accesses will be mostly sequential. On the other hand, the index will need to
					// manipulate the data, resulting in larger overhead. If you use a main memory storage manager,
					// storing the data externally is highly recommended (clustering has no effect).
					// A clustered storage manager is NOT provided yet.
					// Also you will have to take care of converting you data to and from binary format, since only
					// array of bytes can be inserted in the index (see RTree::Node::load and RTree::Node::store for
					// an example of how to do that).

				//tree.insertData(data.getBytes(), r, id);

					tree.insertData(null, r, id);
					// example of passing a null pointer as the associated data.
				}
				else if (op == 2)
				{
				//query

					f1[0] = x1; f1[1] = y1;
					f2[0] = x2; f2[1] = y2;

					MyVisitor vis = new MyVisitor();

					if (querytype.equals("intersection"))
					{
						Region r = new Region(f1, f2);
						tree.intersectionQuery(r, vis);
						// this will find all data that intersect with the query range.
					}
				else if (querytype.equals("10NN"))
				{
					Point p = new Point(f1);
					tree.nearestNeighborQuery(10, p, vis);
						// this will find the 10 nearest neighbors.
				}
				else
				{
					System.err.println("Unknown query type.");
					System.exit(-1);
				}
			}

			if ((count % 1000) == 0) System.err.println(count);

			count++;
			line = lr.readLine();
		}

			long end = System.currentTimeMillis();
		
			System.err.println("Operations: " + count);
			System.err.println(tree);
			System.err.println("Minutes: " + ((end - start) / 1000.0f) / 60.0f);
			
		// since we created a new RTree, the PropertySet that was used to initialize the structure
		// now contains the IndexIdentifier property, which can be used later to reuse the index.
		// (Remember that multiple indices may reside in the same storage manager at the same time
		//  and every one is accessed using its unique IndexIdentifier).
			Integer indexID = (Integer) ps2.getProperty("IndexIdentifier");
			System.err.println("Index ID: " + indexID);

			
			
			
			boolean ret = tree.isIndexValid();
			if (ret == false) System.err.println("Structure is INVALID!");

			
			
			
			
			
			
			int leafnum = tree.getLeafNum();
			ls = new LeafStat[leafnum];
			//System.out.println("leafnum"+leafnum);
			int[] leafID = new int[leafnum];
			leafID = tree.getLeafID();
			int ci;
			for(ci=0; ci<leafID.length; ci++){
			//	System.out.println(leafID[ci]);
				int[] childid = tree.getChildID(leafID[ci]);
				Region reg = tree.getNodeRegion(leafID[ci]);
				ls[ci] = new LeafStat(ci, leafID[ci], childid, reg);
			//	System.out.println(ls[ci].toString());
			//	System.out.println("leafid:"+leafID[ci]);
//				for(int cj=0; cj<childid.length;cj++){
//					System.out.println(childid[cj]);
//				}
			}
			
			
			
			
		//	int childnum[];
		//	childnum = tree.getLeafChildrenNum();
		//	for(int ci=0; ci<childnum.length; ci++)
		//		System.out.println(childnum[ci]);
			
			
//			Region reg[];
//			reg = tree.getLeafRegion();
//			
//			px1 = new double[childnum.length];
//			px2 = new double[childnum.length];
//			py1 = new double[childnum.length];
//			py2 = new double[childnum.length];
//			
//			for(int ci=0; ci<childnum.length; ci++){
//			//	System.out.println(reg[ci].m_pLow[0]+" "+reg[ci].m_pLow[1]);
//			//	System.out.println(reg[ci].toString());
//				px1[ci] = reg[ci].m_pLow[0];
//				py1[ci] = reg[ci].m_pLow[1];
//				px2[ci] = reg[ci].m_pHigh[0];
//				py2[ci] = reg[ci].m_pHigh[1];
//			}
			
		// flush all pending changes to persistent storage (needed since Java might not call finalize when JVM exits).
			tree.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	class MyVisitor implements IVisitor
	{
		public void visitNode(final INode n) {}

		public void visitData(final IData d)
		{
			System.out.println(d.getIdentifier());
				// the ID of this data entry is an answer to the query. I will just print it to stdout.
		}
	}
}
