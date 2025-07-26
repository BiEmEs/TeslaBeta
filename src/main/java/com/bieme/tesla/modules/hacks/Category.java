package com.bieme.tesla.modules.hacks;

public enum Category {
	COMBAT(false),
	MOVEMENT(false),
	RENDER(false),
	MISC(false),
	CHAT(false),
	CLIENT(true);

	private final boolean hidden;

	Category(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean is_hidden() {
		return hidden;
	}
}