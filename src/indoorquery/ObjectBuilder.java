package indoorquery;

import java.io.*;
import java.util.*;

public class ObjectBuilder {
	
	int MAX_NUM = 1500;
	private Room[] rooms;
	private Door[] doors;
	
	public ObjectBuilder(String input_r, String input_d){
		File file1 = new File(input_r);
		BufferedReader reader = null;
		//Room[] rooms = new Room[MAX_NUM];
		//Door[] doors = new Door[MAX_NUM];
		Room tempr;
		Door tempd;
		Point p1,p2,p3,p4,plu,prd;
		int i = 0;
		int j = 0;
		int k;
		ArrayList al1 = new ArrayList(MAX_NUM);
		ArrayList al2 = new ArrayList(MAX_NUM);
		
		try{
			reader = new BufferedReader(new FileReader(file1));
			String temp = null;
			
			while((temp = reader.readLine()) != null){
				//System.out.println("line"+i+":"+temp);
				String[] str = temp.split("\t");
				//System.out.println(str[0]);
		/*		if(Integer.parseInt(str[1]) != 1){
				p1 = new Point(Integer.parseInt(str[2]),Integer.parseInt(str[3]),Integer.parseInt(str[4]));
				p2 = new Point(Integer.parseInt(str[5]),Integer.parseInt(str[6]),Integer.parseInt(str[7]));
				p3 = new Point(Integer.parseInt(str[8]),Integer.parseInt(str[9]),Integer.parseInt(str[10]));
				p4 = new Point(Integer.parseInt(str[11]),Integer.parseInt(str[12]),Integer.parseInt(str[13]));
				plu = new Point(p1.LeftUp(p2, p3, p4));
				prd = new Point(p1.RightDown(p2, p3, p4));
				tempr = new Room(Integer.parseInt(str[0]), plu, prd, Integer.parseInt(str[1])) ;
				al1.add(tempr);
				}
				else{
					p1 = new Point(Integer.parseInt(str[2]),Integer.parseInt(str[3]),Integer.parseInt(str[4]));
					p2 = new Point(0,0,0);
					tempr = new Room(Integer.parseInt(str[0]), p1, p2, 1);
					al1.add(tempr);
				}		*/	//初期的文件格式
				//System.out.println(str[2]+" "+str[3]+" " +str[6]);
				p1 = new Point(Double.parseDouble(str[2]),Double.parseDouble(str[3]),Integer.parseInt(str[6]));
				p2 = new Point(Double.parseDouble(str[4]),Double.parseDouble(str[5]),Integer.parseInt(str[6]));
				tempr = new Room(Integer.parseInt(str[0]), p1, p2, Integer.parseInt(str[1]));
				al1.add(tempr);
				
				i++;
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(IOException e1){
				}
			}
		}
		this.rooms = new Room[i];
		for(k=0; k<i; k++){
			this.rooms[k] = (Room)al1.get(k);
		}
		
		File file2 = new File(input_d);
		BufferedReader reader2 = null;
		
		Point pd;
		
		try{
			reader2 = new BufferedReader(new FileReader(file2));
			String temp2 = null;
			while((temp2 = reader2.readLine()) != null){
				String[] str2 = temp2.split("\t");
				pd = new Point(Double.parseDouble(str2[3]),Double.parseDouble(str2[4]),Integer.parseInt(str2[5]));
				tempd = new Door(Integer.parseInt(str2[0]), pd, Integer.parseInt(str2[1]), Integer.parseInt(str2[2]));
				al2.add(tempd);
				j++;
			}
			reader2.close();
		}catch(IOException e2){
			e2.printStackTrace();
		}finally{
			if(reader2 != null){
				try{
					reader2.close();
				}catch(IOException e3){
				}
			}
		}
		this.doors = new Door[j];
		for(k=0; k<j; k++){
		//	this.doors[k] = doors[k];
			this.doors[k] = (Door)al2.get(k);
		}
		
	}
	
	public Room[] RoomResult(){
		return rooms;
	}
	
	public Door[] DoorResult(){
		return doors;
	}
}
