package assignment1;
import java.util.*;
import java.io.*;

public class Assignment1 {
	static int idcnt=1;

	public static class patient{
		String name;
		float temp;
		int o2;
		int age;
		int id;
		int recovery;
		String admittedinstitute;
		int removed;
		
		patient(String name1,float temp1,int o21,int age1){
			name=name1;
			temp=temp1;
			o2=o21;
			age=age1;
			id=idcnt;
			idcnt++;
			recovery=0;
			admittedinstitute="";
			removed=0;
		}
		
		void print() {
			System.out.println("ID : "+id);
			System.out.println("Name : "+name);
			System.out.println("Temperature is "+temp);
			System.out.println("Oxygen Level is "+o2);
			if (admittedinstitute.compareTo("")==0) {
				System.out.println("Admission Status : Not Admitted");
			}
			else {
				System.out.println("Admission Status : Admitted");
				System.out.println("Admitting Institute : "+admittedinstitute);
			}
			System.out.println();
			//System.out.println(age);
		}
		
	}
	
	static ArrayList<patient> nonadmit=new ArrayList<patient>();
	
	public static class healthcare{
		Scanner sc1=new Scanner(System.in);
		String name2;
		float maxtemp;
		int mino2;
		int availablebeds;
		ArrayList<patient> admitted;
		
		healthcare(String name3,float maxt1,int mino1,int avbed){
			name2=name3;
			maxtemp=maxt1;
			mino2=mino1;
			availablebeds=avbed;
			admitted=new ArrayList<patient>();
		}
		
		void print() {
			System.out.println(name2);
			System.out.println("Temperature should be <= "+maxtemp);
			System.out.println("Oxygen Levels should be >= "+mino2);
			System.out.println("No. of availbale beds = "+availablebeds);
			if (availablebeds>0)
				System.out.println("Admission Status - OPEN");
			else
				System.out.println("Admission Status - CLOSED");
		}
		
		void admitpatients(patient p[]) {
			
			if (availablebeds<=0) {
				System.out.println("NO patient Admitted");
				return;
			}
			
			ArrayList<Integer> temp=new ArrayList<Integer>();
			
			for (int i=0;i<nonadmit.size();i++) {
				patient p1=nonadmit.get(i);
				if (p1.o2>=mino2) {
					admitted.add(p1);
					p1.admittedinstitute=name2;
					p[p1.id-1].admittedinstitute=name2;
					temp.add(i);
					availablebeds--;
					if (availablebeds<=0) {
						break;
					}
				}
			}
			
			Collections.sort(temp);
			
			for (int i=0;i<temp.size();i++) {
				int z=temp.get(i)-i;
				nonadmit.remove(z);
			}
			
			temp=new ArrayList<Integer>();
			
			if (availablebeds>0) {
				for (int i=0;i<nonadmit.size();i++) {
					patient p1=nonadmit.get(i);
					if (p1.temp<=maxtemp) {
						admitted.add(p1);
						p1.admittedinstitute=name2;
						p[p1.id-1].admittedinstitute=name2;
						temp.add(i);
						availablebeds--;
						if (availablebeds<=0) {
							break;
						}
					}
				}
				
				Collections.sort(temp);
				
				for (int i=0;i<temp.size();i++) {
					int z=temp.get(i)-i;
					nonadmit.remove(z);
				}
			}
			
			for (int i=0;i<admitted.size();i++) {
				patient p1=admitted.get(i);
				System.out.println("Recovery date for id = "+p1.id+" : ");
				p1.recovery=sc1.nextInt();
				p[p1.id-1].recovery=p1.recovery;
			}
			
		}
		
		int openclose() {
			if (availablebeds>0)
				return 1;
			else
				return 0;
		}
		
		void currentstatus() {
			for (int j=0;j<admitted.size();j++) {
				patient p1=admitted.get(j);
				System.out.println(p1.name+", Recovery time is "+p1.recovery+" days");
			}
		}
		
		void removedlist() {
			
			ArrayList<Integer> temp1=new ArrayList<Integer>();
			for (int j=0;j<admitted.size();j++) {
				patient p1=admitted.get(j);
				temp1.add(j);
				System.out.println(p1.id);
			}
			
			Collections.sort(temp1);
			
			for (int j=0;j<temp1.size();j++) {
				int z=temp1.get(j)-j;
				admitted.remove(z);
				availablebeds++;
			}
		}
	}
	
	public static void menu() {
		System.out.println("1. Enter Health Care Institute");
		System.out.println("2. Remove account of admitted patients");
		System.out.println("3. Remove account of CLOSED healthcare institute");
		System.out.println("4. Display no. of non admitted patients");
		System.out.println("5. Display no. of OPEN health care institute");
		System.out.println("6. Display details of health care institute");
		System.out.println("7. Display patient details");
		System.out.println("8. Display all patients");
		System.out.println("9. Display names of patients in an institute");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter No. of patients");
		int t=sc.nextInt();
		patient p[]=new patient[t];
		for (int i=0;i<t;i++) {
			String name=sc.next();
			float temp=sc.nextFloat();
			int o2=sc.nextInt();
			int age=sc.nextInt();
			p[i]=new patient(name,temp,o2,age);
			nonadmit.add(p[i]);
		}
		
		ArrayList<healthcare> centres=new ArrayList<healthcare>();
		
		menu();
		
		while (true) {
			
			System.out.println("Enter Query number");
			int query=sc.nextInt();
			
			if (query==1) {
				
				System.out.println("Hospital Name :");
				String name3=sc.next();
				System.out.println("Max Temperature :");
				float maxt1=sc.nextFloat();
				System.out.println("Min O2 level :");
				int mino1=sc.nextInt();
				System.out.println("Available Beds :");
				int avbed=sc.nextInt();
				healthcare h=new healthcare(name3,maxt1,mino1,avbed);
				centres.add(h);
				h.print();
				h.admitpatients(p);
				
			}
			else if (query==2) {
				
//				for (int i=0;i<centres.size();i++) {
//					healthcare h=centres.get(i);
//					h.removedlist();
//					
//				}
				for (int i=0;i<t;i++) {
					if (p[i].admittedinstitute.compareTo("")!=0) {
						System.out.println(p[i].id);
						p[i].removed=1;
					}
				}
				
			}
			else if (query==3) {
				
				ArrayList<Integer> temp1=new ArrayList<Integer>();
				for (int i=0;i<centres.size();i++) {
					
					healthcare h=centres.get(i);
					if (h.openclose()==0) {
						System.out.println(h.name2);
						temp1.add(i);
					}
				}		
				
				Collections.sort(temp1);
				
				for (int i=0;i<temp1.size();i++) {
					int z=temp1.get(i)-i;
					centres.remove(z);
				}
				
			}
			else if (query==4) {
				
				System.out.println(nonadmit.size()+" patients");
				
			}
			else if (query==5) {
				
				int cnt=0;
				
				for (int i=0;i<centres.size();i++) {
					
					healthcare h=centres.get(i);
					if (h.openclose()==1)
						cnt++;
				}
				
				System.out.println(cnt);
				
			}
			else if (query==6) {
				
				System.out.println("Enter Health care name :");
				String name3=sc.next();
				
				for (int i=0;i<centres.size();i++) {
					
					healthcare h=centres.get(i);
					if (name3.compareTo(h.name2)==0) {
						h.print();
					}
				}
				
				
			}
			else if (query==7) {
				
				System.out.println("Enter patient ID :");
				int id3=sc.nextInt();
				
				for (int i=0;i<t;i++) {
					if (p[i].id==id3) {
						if (p[i].removed==0) {
							p[i].print();
						}
						else {
							System.out.println("Account does not Exist");
						}
					}
					
				}
				
			}
			else if (query==8) {
				
//				for (int i=0;i<t;i++) {
//					System.out.println(p[i].id+" "+p[i].name);
//				}
				
				for (int i=0;i<t;i++) {
					if (p[i].removed==0) {
						System.out.println(p[i].id+" "+p[i].name);
					}
				}
				
			}
			else if (query==9) {
				
				System.out.println("Enter Health care name :");
				String name3=sc.next();
				
				for (int i=0;i<centres.size();i++) {
					
					healthcare h=centres.get(i);
					if (name3.compareTo(h.name2)==0) {
						h.currentstatus();
					}
				}				
				
			}
//			else if (query==10) {
//				for (int i=0;i<nonadmit.size();i++) {
//					System.out.println(nonadmit.get(i).name);
//				}
//			}
			else {
				break;
			}
			
		}
		
	}

}

/*
 
5
Ram 98.4 94 25
Sam 100.4 92 55
Jim 104 91 61
Tim 99 93 60
Kim 100 91 48

AIIMS
102
92
2

DMS
104
91
3


7
Ram 1000 100 25
Sam 1000 50 55
Jim 1000 25 61
Tim 1000 100 60
Kim 1000 100 48
Ram1 98.4 0 25
Sam1 1000 25 55


3 HOSPITAL
AIIMS
0
51
3

PG
100
26
2

PGG
100
1
2
 
*/