package com.howtodoinjava.jersey;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

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
     *
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
    public String uploadFile(
            @FormDataParam("fileName") final String fileName,
            @FormDataParam("workgroupId") String workgroupId,
            @FormDataParam("userId") final int userId,
            @FormDataParam("content") final InputStream content) {

        logger.info("@POST-anrop från klient kl 16:29  /patmoore");
        logger.info("fileName is " + fileName);
        logger.info("workgroupId is " + workgroupId);
        logger.info("userid is " + userId);
        try {
            String pathname = "/tmp/uploader/xxxx.image";
            if (fileName != null) {
                pathname = "/tmp/uploader/".concat(fileName);
            }
            File file = new File(pathname);
            OutputStream output = new FileOutputStream(file);
            BufferedImage image = ImageIO.read(content);
            boolean success = ImageIO.write(image, "png", output);
            output.close();
            content.close();
            return pathname;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    @POST
    @Path("/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadFileAgain(
            @FormDataParam("fileName") final String fileName,
            @FormDataParam("workgroupId") String workgroupId,
            @FormDataParam("userId") final int userId,
            @FormDataParam("content") final InputStream content) {

        logger.info("@POST-anrop från klient /file 2019-11-13 kl 10:28 ");
        logger.info("fileName is " + fileName);
        logger.info("userid is " + userId);
        logger.info("workgroupId is " + workgroupId);
        try {
            String generateFileName = "received" + userId + ".png";
            String pathname = "/tmp/uploader/xxxx.image";
            if (fileName != null) {
                pathname = "/tmp/uploader/".concat(fileName);
            }
            logger.info("(only png) saving image to  ".concat(pathname));
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
