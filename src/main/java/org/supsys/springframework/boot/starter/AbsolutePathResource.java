package org.supsys.springframework.boot.starter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

/**
 * AbsolutePathResource
 */
public class AbsolutePathResource extends AbstractFileResolvingResource {

    private String filePath;

    public AbsolutePathResource(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    @Override
	public String getFilename() {
		return StringUtils.getFilename(this.filePath);
	}

    @Override
	public URL getURL() throws IOException {
		return new URL(ResourceUtils.URL_PROTOCOL_FILE, "/",this.filePath.substring(1));
	}

    @Override
    public String getDescription() {
        return "CustomProxyResource[" + this.filePath + "]";
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new BufferedInputStream(new FileInputStream(this.filePath));
    }

    @Override
	public boolean exists() {
        return new File(this.filePath).exists();
    }







    @Override
	public boolean equals(Object obj) {
		return (obj == this ||
			(obj instanceof AbsolutePathResource && this.filePath.equals(((AbsolutePathResource) obj).getFilePath())));
    }
    
	@Override
	public int hashCode() {
		return this.filePath.hashCode();
	}
}