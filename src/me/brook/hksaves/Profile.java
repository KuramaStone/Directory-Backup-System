package me.brook.hksaves;

public class Profile {

	public String name;

	public long lastSaved;

	public Profile(String name, long lastSaved) {
		this.name = name;
		this.lastSaved = lastSaved;
	}

	public Profile() {
	}

	public String getName() {
		return name;
	}

	public long getLastSaved() {
		return lastSaved;
	}

}
