package at.htl.rest;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/manage")
public class ProjectManager {
    @Inject
    @RestClient
    ProjectResource projectResource;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Fallback(fallbackMethod = "getAllProjectsFallback")
    @Retry(maxRetries = 3)
    @Counted(name = "requests", description = "Count of Requests")
    @Timed(name = "requestTime", description = "Time the request takes", unit = MetricUnits.MILLISECONDS)
    public Response getAllProjects(){
        return Response.ok().entity(projectResource.getAllProjects()).build();
    }

    public Response getAllProjectsFallback(){
        return Response.status(404).build();
    }
}
