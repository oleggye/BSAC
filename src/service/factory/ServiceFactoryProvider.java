package service.factory;

import service.factory.impl.ChokeServiceFactory;
import service.factory.impl.ServiceFactory;

public class ServiceFactoryProvider {

	private static final ServiceFactoryProvider INSTANCE = new ServiceFactoryProvider();

	private ServiceFactoryProvider() {
	}

	public static final ServiceFactoryProvider getInstance() {
		return INSTANCE;
	}

	public IServiceFactory getServiceFactory() {
		return ServiceFactory.getInstance();
	}

	public IServiceFactory getServiceFactory(ServiceFactoryName name) {

		switch (name) {

		case SIMPLE:
			return ServiceFactory.getInstance();
		case CHOKE:
			// return ChokeServiceFactory.getInstance();
			return ServiceFactory.getInstance();
		default:
			throw new IllegalArgumentException();
		}
	}
}