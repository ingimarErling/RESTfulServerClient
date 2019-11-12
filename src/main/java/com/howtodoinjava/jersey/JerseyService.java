package com.howtodoinjava.jersey;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/upload")
public class JerseyService {

    private final static Logger logger = Logger.getLogger(JerseyService.class);

    @GET
    @Path("/hello")
    public Response tst() {
        logger.info("@GET /hello");
        // http://localhost:8080/JerseyDemos/rest/upload/hello
        return Response.status(200).entity("Hello from the Jersey Service").build();
    }


    /**
     * https://github.com/patmoore/jersey-example/blob/master/src/main/java/jersey/samples/helloworld/resources/UploadService.java
     * @param fileName
     * @param workgroupId
     * @param userId
     * @param content
     * @return 
     */
    @POST
    @Path("/patmoore")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadFile(@PathParam("fileName") final String fileName,
            @FormDataParam("workgroupId") String workgroupId,
            @FormDataParam("userId") final int userId,
            @FormDataParam("content") final InputStream content) {
        //.......Upload the file to S3 or netapp or any storage service
        
        logger.info("@POST-anrop från klient kl 16:29  /patmoore");
        logger.info("userid is "+userId);
        logger.info("workgroupId is "+workgroupId);
        try {
            String generateFileName = "received" + userId + ".png";
            String pathname = "/tmp/uploader/xxxx.image";
            File file = new File(pathname);
            OutputStream output = new FileOutputStream(file);
            BufferedImage image = ImageIO.read(content);
            boolean success = ImageIO.write(image, "png", output);
            output.close();
            content.close();
            return generateFileName;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

}
