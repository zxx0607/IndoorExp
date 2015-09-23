package indoorquery;

//import java.applet.Applet;
//import java.awt.*;
import java.awt.Frame;
//import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;

public class Exp {
	public static void main(String[] args) {
		File file = new File("parameter.txt");
		String[] str1, str2;
		int o_range=5;  //移动对象不确定的移动半径	
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
			String temp = null;
			temp = reader.readLine();
			//System.out.println(temp);
			str1 = temp.split("\t");
			temp = reader.readLine();
			str2 = temp.split("\t");
			
			ObjectBuilder ob = new ObjectBuilder(str1[0],str1[1]);
			
			Room[] rooms = ob.RoomResult();
			Door[] doors = ob.DoorResult();
			DataGenerator dg = new DataGenerator();
			o_range = Integer.parseInt(str1[2]);
			
			DoorGraph dgraph = new DoorGraph(doors, rooms);
			
			double rx;
			double ry;
			int rz=0;
			
			for (int i = 0; i < 100; i++) {
				rx = Math.random()*59400+600;
				ry = Math.random()*34000+12000;
				rz = (int)(Math.random()*8)+1;	
			
				
			Point rp = new Point(rx,ry,rz);
			
			EstRangeQuery erq = new EstRangeQuery(dgraph, rooms, doors, 2600.87, rp);//1870.87
			RangeQuery rq = new RangeQuery(dgraph, rooms, doors, rp);
			}
			System.exit(0);
			double sumdpr = 0;
			for (int i = 0; i < rooms.length; i++) {
				double ndp = 2*(double)rooms[i].doornum(doors);
				if(ndp==0)continue;
				double p =  rooms[i].getArea();
			//	System.out.println("\t\t"+p);
				double r = Math.sqrt(p/Math.PI);
			//	System.out.println("\t\t\t"+r);
				double sita = 2*Math.PI/ndp;
			//	System.out.println("\t\t\t\t"+sita);
			//	double dpr = (4*r*Math.sin(sita/4)*Math.sin(sita*ndp/4)*Math.sin((sita-sita*ndp)/r))/(ndp*(Math.cos(sita/2)-1));
				double dpr = 2 * r * Math.sin(sita/2) / ( 1 - Math.cos(sita/2)  ) / ndp;
				sumdpr += dpr;
				rooms[i].dpr = dpr;
			//	System.out.println(dpr);
			//	if(dpr<0){break;}
			}
			System.out.println("sum:"+sumdpr);
			System.out.println("ave:"+sumdpr/rooms.length);
			
			sumdpr = 0;
			for (int i = 0; i < rooms.length; i++) {
			//	System.out.println(rooms[i].aveDisInRoom(doors));
				sumdpr += rooms[i].aveDisInRoom(doors);
			}
			System.out.println("sum:"+sumdpr);
			System.out.println("ave:"+sumdpr/rooms.length);
			
			double[][] matrix = dgraph.GetShortMatrix();
			

			
//			double area = 0;
//			for (int i = 0; i < rooms.length; i++) {
//				area = area + rooms[i].getArea();
//			}
//			System.out.println("total area:"+area);
//			
//			double np = rooms.length;
//			double nd2 = doors.length;			
//			double ndp = 2*nd2/np;
//			System.out.println("ndp:"+ndp);
//			double pa = area/np;
//			System.out.println("pa:"+pa);
//			double r = Math.sqrt(pa/Math.PI);
//			System.out.println("r:"+r);
//			double sita = 2*Math.PI/ndp;
//			System.out.println("sita:"+sita);
//			double dpr = (4*r*Math.sin(sita/4)*Math.sin(sita*ndp/4)*Math.sin((sita-sita*ndp)/r))/(ndp*(Math.cos(sita/2)-1));
//			System.out.println("dpr:"+dpr);
//			
//			double totalDtoD = 0;
//			for (int i = 0; i < matrix.length; i++) {
//				for (int j = 0; j < matrix[i].length; j++) {
//					if(matrix[i][j] >0)
//						totalDtoD = totalDtoD + matrix[i][j]/1000;
//				}
//			}
//			System.out.println("totalDtoD:"+totalDtoD);
//			double dpr2;
//			dpr2 = totalDtoD/(doors.length * (doors.length-1));
//			System.out.println("ave_DtoD_dis:"+dpr2);
			
			
			
			
			
			
			
//			switch(Integer.parseInt(str1[3])){
//			case 1:
//				dg = new DataGenerator(Integer.parseInt(str2[0]),Integer.parseInt(str2[1]),Integer.parseInt(str2[2]),Integer.parseInt(str2[3]));
//				break;
//			case 2:
//				dg = new DataGenerator(Integer.parseInt(str2[0]),Integer.parseInt(str2[1]),Integer.parseInt(str2[2]),Integer.parseInt(str2[3]),Integer.parseInt(str2[4]),Integer.parseInt(str2[5]),Integer.parseInt(str2[6]),Integer.parseInt(str2[7]));
//				break;
//			case 3:
//				dg = new DataGenerator(Integer.parseInt(str2[0]),Integer.parseInt(str2[1]),Integer.parseInt(str2[2]),Integer.parseInt(str2[3]), o_range, rooms);
//				break;
//			case 4:
//				dg = new DataGenerator(Integer.parseInt(str2[0]),Integer.parseInt(str2[1]),Integer.parseInt(str2[2]),Integer.parseInt(str2[3]),Integer.parseInt(str2[4]),Integer.parseInt(str2[5]),Integer.parseInt(str2[6]),Integer.parseInt(str2[7]),o_range,rooms);
//				break;
//			}
			
			//DataGenerator dg = new DataGenerator(600, 600, 1000, 2, 50, 50, 250, 250, o_range, rooms);
			//Point[] points = dg.getPointResult();
			//DoorGraph dga = new DoorGraph(doors);
			//RTreeLoad rtl = new RTreeLoad("input_mall_2-1_data.txt","tree",20,"10NN");
		//	RTreeLoad rtl = new RTreeLoad("input_hos_3d_data2.txt","tree",20,"10NN");
			RTreeLoad_x rtl_x = new RTreeLoad_x("input_hos_3d_data2.txt","tree",20,"10NN",matrix,doors);
		//	RTreeLoad_x rtl_x = new RTreeLoad_x("mall_data.txt","tree",20,"10NN",matrix,doors);
			
			System.out.println("\nLevel0 Pages' Result:");
			
			int leafnum = rtl_x.ls.length;
			LeafStat[] ls = new LeafStat[leafnum];
			//int roomcount = 0;
			for(int i=0; i<leafnum; i++)
			{
				double leafdpr = 0;
				double leafave = 0;
				
				ls[i] = rtl_x.ls[i];
				for (int j = 0; j < ls[i].roomid.length; j++) {
					leafdpr += rooms[ls[i].roomid[j]].dpr;
					leafave += rooms[ls[i].roomid[j]].avedis;
				}
				System.out.println("id:"+ls[i].leafid);
				System.out.println("dpr:"+ leafdpr/ls[i].roomid.length);
				System.out.println("avedis:"+ leafave/ls[i].roomid.length);
//				ls[i].avedis(rooms, doors,matrix);
//				System.out.println(ls[i].toString());
//				roomcount += ls[i].roomnum;
//				double np_l = ls[i].roomnum;
//				double nd2_l = ls[i].doornum;
//				double ndp_l = 2*nd2_l/np_l;
//				
//				double pa_l = ls[i].area/np_l;
//				
//				double r_l = Math.sqrt(pa_l/Math.PI);
//		
//				double sita_l = 2*Math.PI/ndp_l;
//				
//				double dpr_l = (4*r_l*Math.sin(sita_l/4)*Math.sin(sita_l*ndp_l/4)*Math.sin((sita_l-sita_l*ndp_l)/r_l))/(ndp_l*(Math.cos(sita_l/2)-1));
//				System.out.println("dpr:"+dpr_l);
			}
			
			System.out.println("\n\nLevel1 Pages' Result:");
			
			int indexnum = rtl_x.ns.length;
			LeafStat[] ns = new LeafStat[indexnum];
			//int roomcount2 = 0;
			for (int i = 0; i < indexnum; i++) {
				ns[i] = rtl_x.ns[i];
				
				double leafdpr = 0;
				double leafave = 0;
				
				ns[i] = rtl_x.ns[i];
				for (int j = 0; j < ns[i].roomid.length; j++) {
					leafdpr += rooms[ns[i].roomid[j]].dpr;
					leafave += rooms[ns[i].roomid[j]].avedis;
				}
				System.out.println("id:"+ns[i].leafid);
				System.out.println("dpr:"+ leafdpr/ns[i].roomid.length);
				System.out.println("avedis:"+ leafave/ns[i].roomid.length);
//				ns[i].avedis(rooms, doors, matrix);
//				
//				System.out.println(ls[i].toString());
//				
//				roomcount2 += ns[i].roomnum;
//			
//				double np_l = ns[i].roomnum;
//				double nd2_l = ns[i].doornum;
//				double ndp_l = 2*nd2_l/np_l;
//				
//				double pa_l = ns[i].area/np_l;
//				
//				double r_l = Math.sqrt(pa_l/Math.PI);
//		
//				double sita_l = 2*Math.PI/ndp_l;
//				
//				double dpr_l = (4*r_l*Math.sin(sita_l/4)*Math.sin(sita_l*ndp_l/4)*Math.sin((sita_l-sita_l*ndp_l)/r_l))/(ndp_l*(Math.cos(sita_l/2)-1));
//				
//				System.out.println("dpr:"+dpr_l);
			}
			
//			Point[] p1 = new Point[rtl.childnum.length];
//			Point[] p2 = new Point[rtl.childnum.length];
//			LeafStat[] ls = new LeafStat[rtl.childnum.length];
//			
//			for(int i=0; i<rtl.childnum.length; i++){
//				p1[i] = new Point(rtl.px1[i], rtl.py1[i], 3);//floor!
//				p2[i] = new Point(rtl.px2[i], rtl.py2[i], 3);
//				ls[i] = new LeafStat(p1[i], p2[i], rooms, doors, rtl.childnum[i]);
//			}
//			
//			for(int i=0; i<rtl.childnum.length; i++){
//				System.out.println(ls[i].toString());
//			}
			
			
			
//			Frame f = new Frame("Show Result");//draw
//			Draw d = new Draw(rooms, doors, points, o_range,p1,p2);
//			f.add(d);
//			f.setSize(1200, 650);
//			f.setVisible(true);
//			f.addWindowListener(new WindowAdapter(){
//				public void windowClosing(WindowEvent e){
//					System.exit(0);
//				}
//			});	
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
			
		
		//DataGenerator dg = new DataGenerator(600, 600, 1000, 2);
		
		//ObjectBuilder ob = new ObjectBuilder("input_room2.txt","input_door2.txt");
		//Room[] rooms = ob.RoomResult();
		//Door[] doors = ob.DoorResult();
		//DataGenerator dg = new DataGenerator(600, 600, 1000, 2);
		//DataGenerator dg = new DataGenerator(600, 600, 1000, 2, 100, 50, 250, 300);
		//DataGenerator dg = new DataGenerator(600, 600, 1000, 2, o_range, rooms);
//		DataGenerator dg = new DataGenerator(600, 600, 1000, 2, 50, 50, 250, 250, o_range, rooms);
//		Point[] points = dg.getPointResult();
//		Frame f = new Frame("Show Result");
//		Draw d = new Draw(rooms, doors, points, o_range);
//		f.add(d);
//		f.setSize(1200, 600);
//		f.setVisible(true);
//		f.addWindowListener(new WindowAdapter(){
//			public void windowClosing(WindowEvent e){
//				System.exit(0);
//			}
//		});


	}

	private static void exit(int i) {
		// TODO Auto-generated method stub
		
	}
}
