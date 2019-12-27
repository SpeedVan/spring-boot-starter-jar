package org.supsys.springframework.boot.starter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.Assert;

public class ProxyResourceLoader extends DefaultResourceLoader {

    private String customPath;

    public ProxyResourceLoader(String customPath) {
        super();
        this.customPath = customPath;
    }

    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
		for (ProtocolResolver protocolResolver : this.getProtocolResolvers()) {
			Resource resource = protocolResolver.resolve(location, this);
			if (resource != null) {
				return resource;
			}
		}

		if (location.startsWith("/")) {
			return getResourceByPath(location);
		}
		else if (location.startsWith(CLASSPATH_URL_PREFIX)) {
			return getCustomPathResource(location);
		}
		else {
			try {
				// Try to parse the location as a URL...
				URL url = new URL(location);
				return new UrlResource(url);
			}
			catch (MalformedURLException ex) {
				// No URL -> resolve as resource path.
				return getResourceByPath(location);
			}
		}
    }

    public Resource getCustomPathResource(String location) {
        String path = location.substring(CLASSPATH_URL_PREFIX.length());
        String fullPath = this.customPath + path;
        if(new File(fullPath).exists()){
            Resource res = new AbsolutePathResource(fullPath);
            System.out.println("use custom path resource:"+fullPath+", exist:"+ res.exists());
            return res;
        }
        System.out.println("use class path resource:"+location);
        return new ClassPathResource(path, getClassLoader());
    }
}