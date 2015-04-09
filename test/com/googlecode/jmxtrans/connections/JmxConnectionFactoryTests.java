package com.googlecode.jmxtrans.connections;

import com.googlecode.jmxtrans.classloader.ClassLoaderEnricher;
import org.junit.Ignore;
import org.junit.Test;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JmxConnectionFactoryTests {

	@Test(expected = MalformedURLException.class)
	public void jmxmpUrlCannotBeOpenedWithoutAdditionalJars() throws Exception {
		JMXConnectionParams params = new JMXConnectionParams(jmxmpUrl(), emptyEnv());
		new JmxConnectionFactory().makeObject(params);
	}

	/** This test modify the class loader. This makes it hard to isolate, so let's just run it manually. */
	@Test
	@Ignore("Manual test")
	public void jmxmpSupportedWithAdditionalJar() throws Exception {
		new ClassLoaderEnricher().add(new File("target/dependency/jmxremote_optional.jar"));
		JMXConnectionParams params = new JMXConnectionParams(jmxmpUrl(), emptyEnv());
		JMXConnector jmxConnector = new JmxConnectionFactory().makeObject(params);
		assertThat(jmxConnector).isNotNull();
	}

	private JMXServiceURL jmxmpUrl() throws MalformedURLException {
		return new JMXServiceURL("service:jmx:jmxmp://localhost:8998");
	}

	private Map<String, String> emptyEnv() {
		return Collections.emptyMap();
	}

}
