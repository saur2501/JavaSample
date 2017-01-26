package java.designPatterns;

interface ATMState {
	// Different states expected
	// HasCard, NoCard, HasPin, NoCash
	void insertCard();
	void ejectCard();
	void insertPin(int pinEntered);
	void requestCash(int cashToWithdraw);	
}

class ATMMachine {
	ATMState hasCard;     
	ATMState noCard;
	ATMState hasCorrectPin;
	ATMState atmOutOfMoney;
	ATMState atmState;	//original state
	int cashInMachine = 2000;
	boolean correctPinEntered = false;
	public ATMMachine(){
		hasCard = new HasCard(this);
		noCard = new NoCard(this);
		hasCorrectPin = new HasPin(this);
		atmOutOfMoney = new NoCash(this);
		atmState = noCard;
		if(cashInMachine < 0){
			atmState = atmOutOfMoney;	
		}	
	}
	void setATMState(ATMState newATMState){
		atmState = newATMState;	
	}
	public void setCashInMachine(int newCashInMachine){
		cashInMachine = newCashInMachine;	
	}
	public void insertCard() {
		atmState.insertCard();
	}
	public void ejectCard() {
		atmState.ejectCard();	
	}
	public void requestCash(int cashToWithdraw) {
		atmState.requestCash(cashToWithdraw);	
	}
	public void insertPin(int pinEntered){
		atmState.insertPin(pinEntered);	
	}
	public ATMState getHasCardState() { return hasCard; }
	public ATMState getNoCardState() { return noCard; }
	public ATMState getHasPin() { return hasCorrectPin; }
	public ATMState getNoCashState() { return atmOutOfMoney; }	
}

class HasCard implements ATMState {
	ATMMachine atmMachine;
	public HasCard(ATMMachine newATMMachine){
		atmMachine = newATMMachine;	
	}
	public void insertCard() {
		System.out.println("You can only insert one card at a time");	
	}
	public void ejectCard() {
		System.out.println("Your card is ejected");
		atmMachine.setATMState(atmMachine.getNoCardState());	
	}
	public void requestCash(int cashToWithdraw) {
		System.out.println("You have not entered your PIN");	
	}
	public void insertPin(int pinEntered) {
		if(pinEntered == 1234){
			System.out.println("You entered the correct PIN");
			atmMachine.correctPinEntered = true;
			atmMachine.setATMState(atmMachine.getHasPin());
		} else {
			System.out.println("You entered the wrong PIN");
			atmMachine.correctPinEntered = false;
			System.out.println("Your card is ejected");
			atmMachine.setATMState(atmMachine.getNoCardState());	
		}
	}	
}

class NoCard implements ATMState {
	ATMMachine atmMachine;
	public NoCard(ATMMachine newATMMachine){
		atmMachine = newATMMachine;	
	}
	public void insertCard() {
		System.out.println("Please enter your pin");
		atmMachine.setATMState(atmMachine.getHasCardState());	
	}
	public void ejectCard() {
		System.out.println("You didn't enter a card");	
	}
	public void requestCash(int cashToWithdraw) {
		System.out.println("You have not entered your card");	
	}
	public void insertPin(int pinEntered) {
		System.out.println("You have not entered your card");
	}
}

class HasPin implements ATMState {
	ATMMachine atmMachine;
	public HasPin(ATMMachine newATMMachine){
		atmMachine = newATMMachine;	
	}
	public void insertCard() {
		System.out.println("You already entered a card");	
	}
	public void ejectCard() {
		System.out.println("Your card is ejected");
		atmMachine.setATMState(atmMachine.getNoCardState());	
	}
	public void requestCash(int cashToWithdraw) {
		if(cashToWithdraw > atmMachine.cashInMachine){
			System.out.println("You don't have that much cash available");
			System.out.println("Your card is ejected");
			atmMachine.setATMState(atmMachine.getNoCardState());
		} else {
			System.out.println(cashToWithdraw + " is provided by the machine");
			atmMachine.setCashInMachine(atmMachine.cashInMachine - cashToWithdraw);
			System.out.println("Your card is ejected");
			atmMachine.setATMState(atmMachine.getNoCardState());
			if(atmMachine.cashInMachine <= 0){ 
				atmMachine.setATMState(atmMachine.getNoCashState());
			}
		} 
	}
	public void insertPin(int pinEntered) {
		System.out.println("You already entered a PIN");	
	}
}

class NoCash implements ATMState {
	ATMMachine atmMachine;
	public NoCash(ATMMachine newATMMachine){
		atmMachine = newATMMachine;	
	}
	public void insertCard() {
		System.out.println("We don't have any money");
		System.out.println("Your card is ejected");	
	}
	public void ejectCard() {
		System.out.println("We don't have any money");
		System.out.println("There is no card to eject");	
	}
	public void requestCash(int cashToWithdraw) {
		System.out.println("We don't have any money");	
	}
	public void insertPin(int pinEntered) {
		System.out.println("We don't have any money");		
	}	
}

public class StatePattern {
public static void main(String[] args){
		ATMMachine atmMachine = new ATMMachine();
		atmMachine.insertCard();
		atmMachine.ejectCard();
		atmMachine.insertCard();
		atmMachine.insertPin(1234);
		atmMachine.requestCash(2000);
		atmMachine.insertCard();
		atmMachine.insertPin(1234);		
	}	
}
