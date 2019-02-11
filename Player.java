import java.util.Comparator;

public class Player {

	public String name;
	public int handicap,quota,net,gross;
	public int[] scores = new int[9];
	public int[] pars = new int[]{4,3,4,5,3,4,4,4,4};

	public Player(String[] info) {
		name=info[0];
		handicap=Integer.parseInt(info[1]);
		for(int x=2; x<11; x++) {
			scores[x-2]=Integer.parseInt(info[x]);
		}
		calculateGross();
		calculateNet();
		calculateQuota();

	}
	
	private void calculateGross() {
		for(int x=0; x<9; x++)
		{
			gross+=scores[x];
		}
	}
	
	private void calculateNet(){
		int strokes=(int)((handicap/2)*.8);
		net=gross-strokes;
	}
	
	private void calculateQuota() {
		int goal = (int)((36-handicap)/2);
		if(handicap>=33) {goal=1;}
		int total=0;
		
		for(int x=0; x<9; x++) {
			int diff = scores[x] - pars[x];
			int points=0;
			
			if(diff<=-4) {points=7;}
			if(diff==-3) {points=6;}
			if(diff==-2) {points=5;}
			if(diff==-1) {points=4;}
			if(diff==0) {points=2;}
			if(diff==1) {points=1;}
			if(diff>=2) {points=0;}

			total+=points;
		}
		
		quota=total-goal;
	}

	public static Comparator<Player> PlayerQuota = new Comparator<Player>() {

		public int compare(Player s1, Player s2) {

			int quota1 = s1.quota;
			int quota2 = s2.quota;

			return quota2-quota1;
		}};

	public static Comparator<Player> PlayerGross = new Comparator<Player>() {

		public int compare(Player s1, Player s2) {

			int gross1 = s1.gross;
			int gross2 = s2.gross;

			return gross1-gross2;
	}};
			
	public static Comparator<Player> PlayerNet = new Comparator<Player>() {

		public int compare(Player s1, Player s2) {

			int net1 = s1.net;
			int net2 = s2.net;

			return net1-net2;
	}};
}
