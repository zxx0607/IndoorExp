package indoorquery;

import java.util.*;

public class DataGenerator {//模拟数据生成
	
	private Point[] points;
	private MovingObj[] mobjs;
	
	public DataGenerator(){
		int i;
	}
	
	public DataGenerator(int sizex, int sizey, int num, int floornum){//随机分布
		points = new Point[num];
		Random rand = new Random();
		int a,b,c,i;
		for(i=0 ; i<num; i++){
			a = rand.nextInt(sizex);
			b = rand.nextInt(sizey);
			c = rand.nextInt(floornum)+1;
			points[i]= new Point(a,b,c);	
		}
	}
	
	public DataGenerator(int sizex, int sizey, int num, int floornum, int range, Room[] rooms){//随机分布在房间内
		mobjs =new MovingObj[num];
		points = new Point[num];
		Point tempp;
		Random rand = new Random();
		int a,b,c,i,j;
		i=0;
		while(i != num){
			a = rand.nextInt(sizex);
			b = rand.nextInt(sizey);
			c = rand.nextInt(floornum)+1;
			tempp = new Point(a,b,c);
			j = tempp.inRoomNo(rooms);
			if(j>=0){
				mobjs[i] = new MovingObj(tempp, range, j);
				points[i]= new Point(tempp);
				i++;
			}
			
		}
	}
	
	public DataGenerator(int sizex, int sizey, int num, int floornum, double sigmax, double sigmay, double miux, double miuy){
		//二维正态分布
		points = new Point[num];
		Random rand = new Random();
		double a,b;
		int c;
		int i;
		for(i=0 ; i<num; i++){
			a = -1; b=-1;
			while(a<0 || a>sizex){		
				a = rand.nextGaussian()*sigmax+miux;
			}
			while(b<0 || b>sizey){
				b = rand.nextGaussian()*sigmay+miuy;
			}
			//System.out.println(a);
			c = rand.nextInt(floornum)+1;		
			//System.out.println(c);
			points[i]= new Point((int)a,(int)b,c);			
		}
	}
	
	public DataGenerator(int sizex, int sizey, int num, int floornum, double sigmax, double sigmay, double miux, double miuy, int range, Room[] rooms){
		//二维正态分布在房间内
		points = new Point[num];
		mobjs =new MovingObj[num];
		Point tempp;
		Random rand = new Random();
		double a,b;
		int c,j;
		int i=0;
		while(i<num){
			a = -1; b=-1;
			while(a<0 || a>sizex){		
				a = rand.nextGaussian()*sigmax+miux;
			}
			while(b<0 || b>sizey){
				b = rand.nextGaussian()*sigmay+miuy;
			}
			//System.out.println(a);
			c = rand.nextInt(floornum)+1;		
			//System.out.println(c);
			tempp = new Point((int)a,(int)b,c);
			//points[i]= new Point((int)a,(int)b,c);	
			j = tempp.inRoomNo(rooms);
			if(j>=0){
				mobjs[i] = new MovingObj(tempp, range, j);
				points[i]= new Point(tempp);
				i++;
			}
		}
	}
	
	public Point[] getPointResult(){
		return points;
	}
	
	public MovingObj[] getObjResult(){
		return mobjs;
	}
}
