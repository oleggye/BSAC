package by.bsac.timetable.hibernateFiles.entity.builder;

import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;

public class LecturerBuilder {

	private short idLecturer;
	private Chair chair;
	private String nameLecturer;

	public LecturerBuilder buildId(short idLecturer) {
		this.idLecturer = idLecturer;
		return this;
	}

	public LecturerBuilder buildChair(Chair chair) {
		this.chair = chair;
		return this;
	}

	public LecturerBuilder buildNameLecturer(String nameLecturer) {
		this.nameLecturer = nameLecturer;
		return this;
	}

	public Lecturer build() {
		Lecturer lecturer = new Lecturer();
		lecturer.setIdLecturer(idLecturer);
		lecturer.setChair(chair);
		lecturer.setNameLecturer(nameLecturer);
		return lecturer;
	}
}