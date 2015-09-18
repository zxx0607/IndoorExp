package indoorquery;

import java.util.ArrayList;

import spatialindex.spatialindex.Region;

public class LeafStat {//存储R树一个节点内的信息
	
	private int[] roomid;
	private int leafid;
	private int leafid_rtree;
	private Region reg;
	
//	private Point p1,p2;
	public int roomnum, doornum;
	
	private Room[] rooms;
	private Door[] doors;
	private DoorGraph dg;
	public double ave_door_dis = 0;
	public double area = 0;
	
	public LeafStat(int id, int leafid_rtree, int[] roomid, Region reg){
		this.leafid = id;
		this.leafid_rtree =leafid_rtree;
		this.roomid = new int[roomid.length];
		for(int i=0; i<roomid.length; i++){
			this.roomid[i] = roomid[i];
		}
		this.reg = reg;
		this.roomnum = roomid.length;
		
	}
	
	public void avedis(Room[] rs, Door[] ds, double[][] m){//计算总面积、总门数、平均门门距离
		ArrayList al1 = new ArrayList();
		ArrayList al2 = new ArrayList();
		int i,j;
		int count = 0;
		for(i=0; i<roomnum; i++){
			al1.add(rs[roomid[i]]);
			count++;
		}
		rooms = new Room[count];
		for(i=0; i<roomnum; i++){
			rooms[i] = (Room)al1.get(i);
			area += rooms[i].getArea();
		}
		count = 0;
		for(i=0; i<ds.length; i++){
			int rid1 = ds[i].getRoom1();
			int rid2 = ds[i].getRoom2();
			for(j=0; j<rooms.length; j++){
				int rid0 = roomid[j];
				if(rid1 == rid0 || rid2 == rid0){
					al2.add(ds[i]);
					count++;
					break;
				}
			}
		}
		doors = new Door[count];
		doornum = count;
		for(i=0; i<doornum; i++){
			doors[i] = (Door)al2.get(i);
		}
		double sumdis = 0;
		for (i = 0; i < doornum; i++) {
			for (j = 0; j < doornum; j++){
				double temp = m[doors[i].getId()][doors[j].getId()]/1000;
				if(temp > 0)
					sumdis = sumdis + temp;
			}
		}
		ave_door_dis = sumdis / (doornum * (doornum-1));
		//dg = new DoorGraph(doors, rs);
		//ave_door_dis = dg.GetAveDis();
//		for(i=0; i<roomnum; i++){
//			System.out.println(rooms[i].getId()+" "+roomid[i]);			
//		}
//		System.out.println("doornum: "+doornum);
//		for(i=0; i<doornum; i++){
//			System.out.println(doors[i].getId());			
//		}
		
		
	}
	
//	public LeafStat(Point p1, Point p2, Room[] rs, Door[] ds, int roomnum){
//		this.p1 = p1;
//		this.p2 = p2;
//		ave_door_dis = 0;
//		int i,j,k;
//		j=0;
//		k=0;
//		ArrayList al1 = new ArrayList();
//		ArrayList al2 = new ArrayList();
//		for(i=0; i<rs.length; i++){
//			if(rs[i].inRegion_2d(p1, p2))
//			{
//				al1.add(rs[i]);
//				j++;
//			}
//		}
//		this.roomnum = j;
//		//if(this.roomnum != roomnum)System.out.println("error in roomnum!");
//		rooms = new Room[j];
//		for(i=0; i<j; i++){
//			rooms[i] = (Room)al1.get(i);
//		}
//		
//		for(i=0; i<ds.length; i++){
//			if(ds[i].getDoorLoc().inRegion_2d(p1, p2))
//			{
//				al2.add(ds[i]);
//				k++;
//			}
//		}
//		this.doornum = k;
//		//if(this.roomnum != roomnum)System.out.println("error in roomnum!");
//		doors = new Door[k];
//		for(i=0; i<k; i++){
//			doors[i] = (Door)al2.get(i);
//		}
//		
//		dg = new DoorGraph(doors);
//		ave_door_dis = dg.ave_dis;
//		
//		
//	}
	
	
	public String toString(){
	//	String str = "Region: "+p1.x+" "+p1.y+" "+p2.x+" "+p2.y+"\n"+"roomnum:"+this.roomnum+"\n"+"doornum: "+this.doornum+"\n"+"ave_door_dis: "+this.ave_door_dis+"\n";
		String str ="\nleafid: "+leafid+"\nroomnum: "+roomnum+"\ndoornum: "+doornum;
		str += "\nave_door_dis: "+this.ave_door_dis; 
		return str;
	}
}
