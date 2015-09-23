package indoorquery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class EstRangeQuery {
	
	public EstRangeQuery(DoorGraph dg, Room[] rs, Door[] ds, double avedis, Point p){
		
		//Point testp = new Point(1000,40000,4);
		Point testp = p;
		double testrange = 20000;
		
		int rid = testp.inRoomNo(rs);
		//System.out.println(rid);
		if(rid == -1 || rs[rid].getDoorID(ds) == null){System.out.println("error point location!");}
		else{
			int[] nbdoorid;
			nbdoorid = rs[rid].getDoorID(ds);
			double[] nbdis = new double[nbdoorid.length];
			
			Queue<Integer> idq = new LinkedList<Integer>();
			Queue<Double> disq = new LinkedList<Double>();
			ArrayList al = new ArrayList();
			
			for (int i = 0; i < ds.length; i++) {
				ds[i].flag = false;
			}
			
			for (int i = 0; i < nbdoorid.length; i++) {			
				
				nbdis[i] = testp.distance_2d(ds[nbdoorid[i]].getDoorLoc());		
				//System.out.println(nbdis[i]);
				if(testrange - nbdis[i] >= 0){
				//	System.out.println("a");
					ds[nbdoorid[i]].flag = true;
					idq.offer(nbdoorid[i]);
					disq.offer(testrange - nbdis[i]);
					//System.out.println(testrange - nbdis[i]);
					if(!al.contains(ds[nbdoorid[i]].getRoom1()))al.add(ds[nbdoorid[i]].getRoom1());
					if(!al.contains(ds[nbdoorid[i]].getRoom2()))al.add(ds[nbdoorid[i]].getRoom2());
				}
				
			}
			
			while(idq.size() != 0){

				int tempid = idq.poll();
			//	System.out.println("id:"+tempid);
				double tempdis = disq.poll();
			//	System.out.println("t:"+tempdis);
				for (int i = 0; i < dg.dges.length; i++) {
					if(dg.dges[i].d1.getId() == tempid && ds[dg.dges[i].d2.getId()].flag == false){
						if(tempdis - avedis >= 0){		//用估计的门门平均距离代替具体的门距离
							int id = dg.dges[i].d2.getId();
							ds[id].flag = true;
							idq.offer(id);
							disq.offer(tempdis - avedis);
						//	System.out.println(tempdis - avedis);
							if(!al.contains(ds[id].getRoom1()))al.add(ds[id].getRoom1());
							if(!al.contains(ds[id].getRoom2()))al.add(ds[id].getRoom2());
						}
							
					}
					else if(dg.dges[i].d2.getId() == tempid && ds[dg.dges[i].d1.getId()].flag == false){
						if(tempdis - avedis >= 0){
							int id = dg.dges[i].d1.getId();
							ds[id].flag = true;
							idq.offer(id);
							disq.offer(tempdis - avedis);
							//System.out.println(tempdis - avedis);
							if(!al.contains(ds[id].getRoom1()))al.add(ds[id].getRoom1());
							if(!al.contains(ds[id].getRoom2()))al.add(ds[id].getRoom2());
						}
					}
				}			
			}
			
			int[] room = new int[al.size()];
			for (int i = 0; i < al.size(); i++) {
				room[i] = (Integer)al.get(i);
			//	System.out.println(room[i]);
			}
			System.out.println(al.size());
				
		}
		
		
	}	
}
