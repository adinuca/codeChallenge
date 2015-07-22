package co.wds.resource;

import co.wds.service.DeviceService;
import com.sun.jersey.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


@Path("/devices")
public class DeviceResource {

    private final DeviceService deviceService;
    private static final Logger logger = LoggerFactory.getLogger(DeviceResource.class);

    public DeviceResource(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GET
    @Produces("application/json")
    public Response getDevices() throws FileNotFoundException {
        try{
            return Response.ok(deviceService.getDevices()).build();
        }catch(Exception exception){
            logger.error(exception.getMessage(), exception);
            return Response.serverError().entity("No devices were found, or none could be retrieved").build();
        }
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadDevices(@FormDataParam("devices") InputStream devicesFile){
        try {
            deviceService.saveDevices(devicesFile);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return Response.serverError().build();
        }
        return Response.ok("Devices were saved successfully").build();
    }
}
