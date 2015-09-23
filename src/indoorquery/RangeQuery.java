package indoorquery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RangeQuery {
	
	public RangeQuery(DoorGraph dg, Room[] rs, Door[] ds, Point p){//ʵ��range query
		
		//Point testp = new Point(1000,40000,4);
		Point testp = p;
		double testrange = 20000;
		
		int rid = testp.inRoomNo(rs); //��õ����ڷ���ID
		//System.out.println(rid);
		if(rid == -1 || rs[rid].getDoorID(ds) == null){System.out.println("error point location!");}//�㲻�ڷ����л��߷���û��
		else{
			int[] nbdoorid;
			nbdoorid = rs[rid].getDoorID(ds);//���ص����ڷ����������
			double[] nbdis = new double[nbdoorid.length];
			
			Queue<Integer> idq = new LinkedList<Integer>();//��id����
			Queue<Double> disq = new LinkedList<Double>();//��֮��������
			ArrayList al = new ArrayList();
			
			for (int i = 0; i < ds.length; i++) {//�����ŵķ��ʼ�¼��Ϊ��
				ds[i].flag = false;
			}
			
			for (int i = 0; i < nbdoorid.length; i++) {			
				
				nbdis[i] = testp.distance_2d(ds[nbdoorid[i]].getDoorLoc());		//����㵽���ڷ����ŵľ���
				//System.out.println(nbdis[i]);
				if(testrange - nbdis[i] >= 0){	//rangeֵ���ڵ㵽�ž���
				//	System.out.println("a");
					ds[nbdoorid[i]].flag = true;
					idq.offer(nbdoorid[i]);		//��ID������
					disq.offer(testrange - nbdis[i]);	//ʣ����������
					//System.out.println(testrange - nbdis[i]);
					if(!al.contains(ds[nbdoorid[i]].getRoom1()))al.add(ds[nbdoorid[i]].getRoom1());	//�����Ӧ�ķ�����Ϣ
					if(!al.contains(ds[nbdoorid[i]].getRoom2()))al.add(ds[nbdoorid[i]].getRoom2());
				}
				
			}
			
			while(idq.size() != 0){ //���зǿ�

				int tempid = idq.poll();
			//	System.out.println("id:"+tempid);
				double tempdis = disq.poll();
			//	System.out.println("t:"+tempdis);
				for (int i = 0; i < dg.dges.length; i++) {	//������ͼ�ı�����
					if(dg.dges[i].d1.getId() == tempid && ds[dg.dges[i].d2.getId()].flag == false){	//һ���ߵ�һ�������ǳ����е���ID������һ������û�����ʹ�
						if(tempdis - dg.dges[i].length >= 0){	//ʣ����� ��ȥ �߳�
							int id = dg.dges[i].d2.getId();
							ds[id].flag = true;
							idq.offer(id);
							disq.offer(tempdis - dg.dges[i].length);
						//	System.out.println(tempdis - dg.dges[i].length);
							if(!al.contains(ds[id].getRoom1()))al.add(ds[id].getRoom1());
							if(!al.contains(ds[id].getRoom2()))al.add(ds[id].getRoom2());
						}
							
					}
					else if(dg.dges[i].d2.getId() == tempid && ds[dg.dges[i].d1.getId()].flag == false){ //ͬ��һ��IFѭ�����ߵ�id�жϴ�d1����d2
						
					
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
			System.out.println(al.size());  //�����ѯ���ķ�������
				
		}
		
		
	}
}
