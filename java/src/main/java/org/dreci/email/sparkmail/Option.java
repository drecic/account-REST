package org.drecic.email.sparkmail;

/*
 * Used when a function needs to return one object in the "affirmative" 
 * case and a different object in the "negative" case
 * 
 * Ideally, methods that return an Option<> object should not return null
 */
public class Option<A, N> {
	private boolean success;
	private A affirmative;
	private N negative;
	private Throwable error;

	public Option(A affirmative) {
		this.affirmative = affirmative;
		this.success = true;
	}

	public Option(N negative, Throwable error) {
		this.negative = negative;
		this.error = error;
		this.success = false;
	}

	public boolean success() {
		return success;
	}

	public A affirmative() {
		return affirmative;
	}

	public N negative() {
		return negative;
	}
	
	public boolean hasError() {
		return (this.error != null);
	}

	public Throwable getError() {
		return error;
	}
}
