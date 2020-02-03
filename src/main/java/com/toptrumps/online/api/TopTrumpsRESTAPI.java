package com.toptrumps.online.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.toptrumps.online.configuration.TopTrumpsJSONConfiguration;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 *
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {

    /**
     * A Jackson Object writer. It allows us to turn Java objects
     * into JSON strings easily.
     */
    ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

    /**
     * Contructor method for the REST API. This is called first. It provides
     * a TopTrumpsJSONConfiguration from which you can get the location of
     * the deck file and the number of AI players.
     *
     * @param conf
     */
    public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
        // ----------------------------------------------------
        // Add relevant initalization here
        // ----------------------------------------------------
    }

    // ----------------------------------------------------
    // Add relevant API methods here
    // ----------------------------------------------------

    @GET
    @Path("/getTopCard")
    /**
     * Handler for a GET request to get the top card
     * 
     * @param playerID - ID of a player 
     * @return - card data as JSON (name and attributes)
     * @throws IOException
     */
    public String getTopCard(@QueryParam("playerID") int playerID) throws IOException {

        JsonNodeFactory factory = JsonNodeFactory.instance;

        ObjectNode attributesNode = factory.objectNode();
        attributesNode.put("Strength", 10);
        attributesNode.put("Dexterity", 9);
        attributesNode.put("Constitution", 8);
        attributesNode.put("Intelligence", 7);
        attributesNode.put("Charisma", 6);

        ObjectNode rootNode = factory.objectNode();
        rootNode.put("name", "Card name");
        rootNode.put("attributes", attributesNode);

        String cardData = rootNode.toString();

        return cardData;
    }

    @GET
    @Path("/getActivePlayer")
    /**
     * Handler to get active player id
     * 
     * @return - player id as JSON
     * @throws IOException
     */
    public String getActivePlayer() throws IOException {
        JsonNodeFactory factory = JsonNodeFactory.instance;

        ObjectNode node = factory.objectNode();
        node.put("playerID", 2);

        String playerID = node.toString();

        return playerID;
    }
    
    @GET
    @Path("/getChosenCategory")
    /**
     * Handler to get chosen category
     * 
     * @return chosen category as JSON
     * @throws IOException
     */
    public String getChosenCategory() throws IOException {
        JsonNodeFactory factory = JsonNodeFactory.instance;

        ObjectNode node = factory.objectNode();
        node.put("category", "Dexterity");

        String category = node.toString();

        return category;
    }

    @GET
    @Path("/getOpponentsCards")
    /**
     * Handler to get all opponents' cards
     * 
     * @return array of players and corresponding cards as JSON
     * @throws IOException
     */
    public String getOpponentsCards() throws IOException {
        JsonNodeFactory factory = JsonNodeFactory.instance;

        ObjectNode rootNode = factory.objectNode();

        for (int i = 1; i < 5; i++) {
            ObjectNode attributesNode = factory.objectNode();
            attributesNode.put("Strength", 10);
            attributesNode.put("Dexterity", 9);
            attributesNode.put("Constitution", 8);
            attributesNode.put("Intelligence", 7);
            attributesNode.put("Charisma", 6);

            ObjectNode playerNode = factory.objectNode();
            playerNode.put("name", "Card name");
            playerNode.put("attributes", attributesNode);

            String playerID = i+"";

            rootNode.put(playerID, playerNode);
        }

        String cardData = rootNode.toString();

        return cardData;
    }
}
