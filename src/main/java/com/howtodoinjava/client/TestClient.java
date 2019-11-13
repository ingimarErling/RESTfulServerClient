package com.howtodoinjava.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author ingimar
 */
public class TestClient {

    public static void main(String[] args) throws IOException {

        TestClient c = new TestClient();
        c.patmoore();

    }
    
    /**
     * the uploaded file does not have the same md5sum !?
     * 
     * @throws FileNotFoundException
     * @throws IOException 
     */

    private void patmoore() throws FileNotFoundException, IOException {
        final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

        System.out.println("Testing 1");
        String fileName = "testbild-svt-666.jpg";
        String filePath = "/tmp/testbild-svt-666.jpg";

        int i = 0;

        FormDataMultiPart form = new FormDataMultiPart();
        form.field("owner", "ingimar");
        form.field("fileName", "testbild-svt-666.jpg");
        form.field("workgroupId", "XXX");
        form.field("userId", Integer.toString(i));
        InputStream content = new FileInputStream(new File(filePath));
        FormDataBodyPart fdp = new FormDataBodyPart("content", content, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        form.bodyPart(fdp);

        final WebTarget target = client.target("http://localhost:8080/JerseyServer/rest/upload/file");
        final Response response = target.request().post(Entity.entity(form, MediaType.MULTIPART_FORM_DATA));
        content.close();
        System.out.println("response " + response.getStatus());

    }

}
