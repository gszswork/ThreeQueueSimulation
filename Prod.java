import java.util.ArrayList;
public class Prod {
	
	int collision = 0;
	int index;
	double  lamda;
	ArrayList<pack> queue;
	int length;
	double currentTime;     //���ڼ�¼��ǰʱ����
	public ArrayList<Integer> lengthDis;
	public Prod(double lamda) {
		this.lamda = lamda;
		queue = new ArrayList<pack>();
		lengthDis = new ArrayList<Integer>();
	}   
	public Prod(int index,double lamda) {
		this.index = index;
		this.lamda = lamda;
		queue = new ArrayList<pack>();
		lengthDis = new ArrayList<Integer>();
	}
	public void getLength() {
		length = queue.size();
	}
	
	public void Run(int count) {
		for(int i=0;i<count;i++) { 
			//_______���ڵ���
			System.out.println("����"+index+" ��"+i);
			//-------------
			pack temppack = new pack(i);				//�����µİ�
			temppack.comeTime =  currentTime;			//���ð��ĵ���ʱ��Ϊ��ǰʱ��
			double comeInter = -Math.log(Math.random())/lamda;								//���Խ׶���������
			currentTime += comeInter;						//�����߼�ʱ��Ӧ��
			queue.add(temppack); 							//����������
		}
	}
	public void back(double binaryTime) {
		int id = 0;
		pack p = queue.remove(0);
		p.comeTime += binaryTime;
		for(id = 0; id<queue.size();id++) {
			if(p.comeTime<=queue.get(id).comeTime) {
				break;
			}
		}
		id = id ;
		queue.add(id, p);
	}
	
	public static void main(String args[]) {
		Prod p = new Prod(1,3);
		p.Run(10);
		for(int i = 0;i<10;i++) {
			System.out.print(p.queue.get(i).comeTime+ "  ");
		}
		System.out.println();
		p.back(9);
		for(int i = 0;i<10;i++) {
			System.out.print(p.queue.get(i).comeTime+ "  ");
		}
	}
}
