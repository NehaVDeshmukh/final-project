package server;

public class IncorrectIDException extends Exception {
	public String toString() {
		return "Not a valid critter id";
	}
}
