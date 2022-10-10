public class Unit{
	private int slot;
	private String name;
	private int CR;
	private boolean ally;
	private boolean fastestUnit;
	private boolean fastestAlly;

	
	
	public Unit(){
		//
	}
	
	public Unit(int slot_, String name_, int CR_, String ally_, String fastestUnit_, String fastestAlly_){
		slot = slot_;
		name = name_;
		CR = CR_;
		if(ally_.equals("A")){
			ally = true;
		}
		else{
			ally = false;
		}
		if(fastestUnit_.equals("Y")){
			fastestUnit = true;
		}
		else{
			fastestUnit = false;
		}
		if(fastestAlly_.equals("Y")){
			fastestAlly = true;
		}
		else{
			fastestAlly = false;
		}
		
	}
	
	public int getSlot(){
		return slot;
	}
	public String getName(){
		return name;
	}
	public int getCR(){
		return CR;
	}
	public boolean getAlly(){
		return ally;
	}
	public boolean getFastestUnit(){
		return fastestUnit;
	}
	public boolean getFastestAlly(){
		return fastestAlly;
	}
}
