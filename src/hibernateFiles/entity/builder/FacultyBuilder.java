package hibernateFiles.entity.builder;

import hibernateFiles.entity.Faculty;

public class FacultyBuilder {

	private byte idFaculty;
	private String nameFaculty;

	public FacultyBuilder buildId(byte idFaculty) {
		this.idFaculty = idFaculty;
		return this;
	}

	public FacultyBuilder buildId(String nameFaculty) {
		this.nameFaculty = nameFaculty;
		return this;
	}

	public Faculty build() {
		Faculty faculty = new Faculty();
		faculty.setIdFaculty(idFaculty);
		faculty.setNameFaculty(nameFaculty);
		return faculty;
	}
}