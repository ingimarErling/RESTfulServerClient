package com.howtodoinjava.client;

import com.howtodoinjava.jersey.JerseyService;
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
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author ingimar
 */
public class TestClient {

    private final static Logger logger = Logger.getLogger(TestClient.class);

    public static void main(String[] args) throws IOException {

        TestClient c = new TestClient();
//        c.png();
        c.other();

    }

    /**
     * the uploaded file does not have the same md5sum !?
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void png() throws FileNotFoundException, IOException {
        final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

//        logger.info("only png-files");
        String fileName = "testbild-svt-666.png";
        String filePath = "/tmp/".concat(fileName);
        System.out.println("File ".concat(filePath));

        int i = 0;

        FormDataMultiPart form = new FormDataMultiPart();
        form.field("owner", "ingimar");
        form.field("fileName", fileName);
        form.field("workgroupId", "XXX");
        form.field("userId", Integer.toString(i));
        InputStream content = new FileInputStream(new File(filePath));
        FormDataBodyPart fdp = new FormDataBodyPart("content", content, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        form.bodyPart(fdp);

        final WebTarget target = client.target("http://localhost:8080/JerseyServer/rest/upload/png");
        final Response response = target.request().post(Entity.entity(form, MediaType.MULTIPART_FORM_DATA));
        content.close();
        System.out.println("response " + response.getStatus());
    }
    
    private void other() throws FileNotFoundException, IOException {
        final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
        System.out.println("Testclient OTHER");
                

//        String fileName = "testbild-svt-666.png";
        String fileName = "file100.zip";
        String filePath = "/tmp/".concat(fileName);
        
        System.out.println("File ".concat(filePath));

        int i = 0;

        FormDataMultiPart form = new FormDataMultiPart();
        form.field("owner", "ingimar");
        form.field("fileName", fileName);
        form.field("workgroupId", "XXX");
        form.field("userId", Integer.toString(i));
        InputStream content = new FileInputStream(new File(filePath));
        FormDataBodyPart fdp = new FormDataBodyPart("content", content, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        form.bodyPart(fdp);

        final WebTarget target = client.target("http://localhost:8080/JerseyServer/rest/upload/other");
        final Response response = target.request().post(Entity.entity(form, MediaType.MULTIPART_FORM_DATA));
        content.close();
        System.out.println("response " + response.getStatus());
    }

}
