package com.howtodoinjava.jersey;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;
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
    @Path("/png")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadFileAgain(
            @FormDataParam("fileName") final String fileName,
            @FormDataParam("workgroupId") String workgroupId,
            @FormDataParam("userId") final int userId,
            @FormDataParam("content") final InputStream content) {

        logger.info("@POST-anrop http://localhost:8080/JerseyServer/rest/upload/png ");
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

    @POST
    @Path("/other")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadAllFiles(
            @FormDataParam("fileName") String fileName,
            @FormDataParam("workgroupId") String workgroupId,
            @FormDataParam("userId") final int userId,
            @FormDataParam("content") final InputStream uploadedInputStream) {

        final String UPLOAD_FOLDER = "/tmp/uploader/";
        logger.info("other");
        logger.info("@POST-anrop http://localhost:8080/JerseyServer/rest/upload/other ");

        logger.info("fileName is " + fileName);
        logger.info("userid is " + userId);
        logger.info("workgroupId is " + workgroupId);

        try {
            createFolderIfNotExists(UPLOAD_FOLDER);
        } catch (SecurityException se) {
            logger.info(se.getLocalizedMessage());
            return Response.status(500).entity("Can not create destination folder on server").build();
        }

        // wip: workaround
        if (fileName == null) {
            UUID uuid = UUID.randomUUID();
            fileName = uuid.toString().concat(".unknown");
            logger.info("if null : fileName is " + fileName);
        }

        String uploadedFileLocation = UPLOAD_FOLDER.concat(fileName);
        try {
            saveToFile(uploadedInputStream, uploadedFileLocation);
        } catch (IOException e) {
            e.getStackTrace();
            return Response.status(500).entity("Can not save file").build();
        }

        return Response.status(200).entity("File saved to " + uploadedFileLocation).build();
    }

    private boolean saveToFile(InputStream inStream, String target) throws IOException {
        boolean isSuccess=false;
        if (inStream != null) {
            logger.info("InputStream available?  " + inStream.available());
        } else {
            logger.info("InputStream: null  ");
            return isSuccess;
        }
        logger.info("target is " + target);
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];

        out = new FileOutputStream(new File(target));
        while ((read = inStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        isSuccess = true;
        
        out.flush();
        out.close();
        return isSuccess;
    }

    private void createFolderIfNotExists(String dirName) throws SecurityException {
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }
}
