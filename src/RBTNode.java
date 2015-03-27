class RBTnode {
	private int value;
	private RBTnode left;
	private RBTnode right;
	private boolean cbit; //colour bit
	public RBTnode (int x, boolean color) {
		value = x;
		left = null;
		right = null;
		cbit=color;
	}
	
	void set_value(int x) {
		value=x;
	}
	
	int get_value() {
		return value;
	}
	void set_left(RBTnode l) {
		left=l;
	}
	
	RBTnode get_left() {
		return left;
	}
	
	void set_right(RBTnode r){
		right=r;
	}
	
	RBTnode get_right() {
		return right;
	}
	
	void set_cbit(boolean val){
		cbit=val;
	}
	
	boolean get_cbit(){
		return cbit;
	}
}
