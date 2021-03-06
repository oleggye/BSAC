package hibernateFiles.entity.builder;

import hibernateFiles.entity.Flow;

public class FlowBuilder {

	private short idFlow;
	private String name;

	public FlowBuilder buildId(short idFlow) {
		this.idFlow = idFlow;
		return this;
	}

	public FlowBuilder buildName(String name) {
		this.name = name;
		return this;
	}

	public Flow build() {
		Flow flow = new Flow();
		flow.setIdFlow(idFlow);
		flow.setName(name);
		return flow;
	}
}