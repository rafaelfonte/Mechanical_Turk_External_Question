import com.amazonaws.mturk.addon.BatchItemCallback;
import com.amazonaws.mturk.requester.ApproveAssignment;
import com.amazonaws.mturk.requester.HIT;
import com.amazonaws.mturk.requester.RegisterHITType;
import com.amazonaws.mturk.service.axis.RequesterService;
import com.amazonaws.mturk.service.exception.ServiceException;
import com.amazonaws.mturk.util.PropertiesClientConfig;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Rafael
 * Date: 5/16/13
 * Time: 1:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreateHIT {
    private String title = "Choosing the most realistic image";
    private String description = "In this HIT, the worker should choose one image of a pair of images that seems to be the most realistic to him/her. There will be sixteen pairs.";
    private int numAssignments = 450;
    private double reward = 0.50;


    private RequesterService service;

    public CreateHIT(){
        service = new RequesterService(new PropertiesClientConfig());
    }


    public void createSurveyHIT(){
        try{
            HIT hit = service.createHIT(
                    title,
                    description,
                    reward,
                    testImage(),
                    numAssignments);


            // Print out the HITId and the URL to view the HIT.
            System.out.println("Created HIT: " + hit.getHITId());
            System.out.println("HIT location: ");
            System.out.println(service.getWebsiteURL() + "/mturk/preview?groupId=" + hit.getHITTypeId());

        }
        catch(ServiceException e){
            System.out.println("ERROR: " + e);
        }
    }

    public static void main(String [] args){
        CreateHIT c = new CreateHIT();
        c.createSurveyHIT();
    }

    public String testImage(){

        String q = new String(readXMLFile("Test.xml"));

        System.out.println(q);

        return q;
    }

    public String readXMLFile(String filename){
        String resultString = null;
        try{
            Scanner s = new Scanner(new BufferedReader(new FileReader(filename)));
            s.useDelimiter("\\Z");
            resultString = s.next();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return resultString;
    }

}
