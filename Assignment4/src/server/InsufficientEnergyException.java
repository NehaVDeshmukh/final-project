package server;

public class InsufficientEnergyException extends Exception {
	InsufficientEnergyException(String message)
	{
		super(message);
	}
}
