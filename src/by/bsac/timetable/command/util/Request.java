package by.bsac.timetable.command.util;

import java.util.HashMap;
import java.util.Map;

public class Request {

	private final Map<String, Object> requestParam = new HashMap<>();

	public void putParam(String key, Object value) {
		requestParam.put(key, value);
	}

	public Object getValue(String key) {
		return requestParam.get(key);
	}

	public Map<String, Object> getAllParam() {
		return requestParam;
	}
}