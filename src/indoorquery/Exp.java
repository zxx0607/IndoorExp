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
			double[][] matrix = dgraph.GetShortMatrix();
			for (int i = 0; i < 50; i++) {
				for (int j = 0; j < 50; j++) {
					System.out.print(matrix[i][j]+"\t");
				}
				System.out.println();
			}
			
			
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
//			int leafnum = rtl.ls.length;
//			LeafStat[] ls = new LeafStat[leafnum];
//			for(int i=0; i<leafnum; i++)
//			{
//				ls[i] = rtl.ls[i];
//			//	System.out.println(ls[i].toString());
//				ls[i].avedis(rooms, doors);
//				System.out.println(ls[i].toString());
//			}
			
			
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
}
