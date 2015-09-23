package indoorquery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RangeQuery {
	
	public RangeQuery(DoorGraph dg, Room[] rs, Door[] ds, Point p){//实际range query
		
		//Point testp = new Point(1000,40000,4);
		Point testp = p;
		double testrange = 20000;
		
		int rid = testp.inRoomNo(rs); //获得点所在房间ID
		//System.out.println(rid);
		if(rid == -1 || rs[rid].getDoorID(ds) == null){System.out.println("error point location!");}//点不在房间中或者房间没门
		else{
			int[] nbdoorid;
			nbdoorid = rs[rid].getDoorID(ds);//返回点所在房间的门数组
			double[] nbdis = new double[nbdoorid.length];
			
			Queue<Integer> idq = new LinkedList<Integer>();//门id队列
			Queue<Double> disq = new LinkedList<Double>();//门之间距离队列
			ArrayList al = new ArrayList();
			
			for (int i = 0; i < ds.length; i++) {//所有门的访问记录置为否
				ds[i].flag = false;
			}
			
			for (int i = 0; i < nbdoorid.length; i++) {			
				
				nbdis[i] = testp.distance_2d(ds[nbdoorid[i]].getDoorLoc());		//计算点到所在房间门的距离
				//System.out.println(nbdis[i]);
				if(testrange - nbdis[i] >= 0){	//range值大于点到门距离
				//	System.out.println("a");
					ds[nbdoorid[i]].flag = true;
					idq.offer(nbdoorid[i]);		//门ID进队列
					disq.offer(testrange - nbdis[i]);	//剩余距离进队列
					//System.out.println(testrange - nbdis[i]);
					if(!al.contains(ds[nbdoorid[i]].getRoom1()))al.add(ds[nbdoorid[i]].getRoom1());	//保存对应的房间信息
					if(!al.contains(ds[nbdoorid[i]].getRoom2()))al.add(ds[nbdoorid[i]].getRoom2());
				}
				
			}
			
			while(idq.size() != 0){ //队列非空

				int tempid = idq.poll();
			//	System.out.println("id:"+tempid);
				double tempdis = disq.poll();
			//	System.out.println("t:"+tempdis);
				for (int i = 0; i < dg.dges.length; i++) {	//遍历门图的边数组
					if(dg.dges[i].d1.getId() == tempid && ds[dg.dges[i].d2.getId()].flag == false){	//一条边的一个顶点是出队列的门ID，且另一个顶点没被访问过
						if(tempdis - dg.dges[i].length >= 0){	//剩余距离 减去 边长
							int id = dg.dges[i].d2.getId();
							ds[id].flag = true;
							idq.offer(id);
							disq.offer(tempdis - dg.dges[i].length);
						//	System.out.println(tempdis - dg.dges[i].length);
							if(!al.contains(ds[id].getRoom1()))al.add(ds[id].getRoom1());
							if(!al.contains(ds[id].getRoom2()))al.add(ds[id].getRoom2());
						}
							
					}
					else if(dg.dges[i].d2.getId() == tempid && ds[dg.dges[i].d1.getId()].flag == false){ //同上一个IF循环，边的id判断从d1换成d2
						
					
						if(tempdis - dg.dges[i].length >= 0){
							int id = dg.dges[i].d1.getId();
							ds[id].flag = true;
							idq.offer(id);
							disq.offer(tempdis - dg.dges[i].length);
						//	System.out.println(tempdis - dg.dges[i].length);
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
			System.out.println(al.size());  //输出查询到的房间数量
				
		}
		
		
	}
}
