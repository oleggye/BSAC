package hibernateFiles.entity.builder;

import hibernateFiles.entity.Chair;

public class ChairBuilder {

	private byte idChair;
	private String nameChair;

	public ChairBuilder buildId(byte idChair) {
		this.idChair = idChair;
		return this;
	}

	public ChairBuilder buildNameChair(String nameChair) {
		this.nameChair = nameChair;
		return this;
	}

	public Chair build() {
		Chair chair = new Chair();
		chair.setIdChair(idChair);
		chair.setNameChair(nameChair);
		return chair;
	}
}