/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts = new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        
        Point[] pts = new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2 = new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
    }

    @Test
    public void getExistingBlueprintTest(){
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0),new Point(10, 10)};
        String author = "Maud", name = "thepaint";
        Blueprint bp = new Blueprint(author, name, pts);
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the blueprint.");
        }
        try{
            ibpp.getBlueprint(author, name);
            assertEquals(bp, ibpp.getBlueprint(author, name));
        }
        catch (BlueprintNotFoundException ex){
            fail("Wasn't able to find the blueprint.");
        }
    }

    @Test
    public void saveAllBlueprintsTest(){
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint",pts0);
        try{
            ibpp.saveBlueprint(bp0);   
        } catch (BlueprintPersistenceException ex) {
            fail("An exception ocurred when saving the first blueprint.");
        }
        
        Point[] pts = new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint",pts);
        
        try{
            ibpp.saveBlueprint(bp);   
        } catch (BlueprintPersistenceException ex) {
            fail("An exception ocurred when saving the second blueprint.");
        }
        assertEquals(2 + 1, ibpp.getAllBlueprints().size());
    }

    @Test
    public void getAllBlueprintsByAuthor(){
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint",pts0);
        try{
            ibpp.saveBlueprint(bp0);   
        } catch (BlueprintPersistenceException ex) {
            fail("An exception ocurred when saving the first blueprint.");
        }
        
        Point[] pts = new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint",pts);
        
        try{
            ibpp.saveBlueprint(bp);   
        } catch (BlueprintPersistenceException ex) {
            fail("An exception ocurred when saving the second blueprint.");
        }

        Point[] pts2 = new Point[]{new Point(10, 0),new Point(40, 10)};
        Blueprint bp2 = new Blueprint("mack", "mysecondpaint",pts2);
        
        try{
            ibpp.saveBlueprint(bp2);   
        } catch (BlueprintPersistenceException ex) {
            fail("An exception ocurred when saving the third blueprint.");
        }
        try{
            assertEquals(2, ibpp.getBlueprintsByAuthor("mack").size());
        } catch (BlueprintNotFoundException ex) {
            fail("The given author wasn't found.");
        }
    }

    @Test
    public void authorNotFoundTest(){
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint",pts0);
        try{
            ibpp.saveBlueprint(bp0);   
        } catch (BlueprintPersistenceException ex) {
            fail("An exception ocurred when saving the blueprint.");
        }
        try{
            ibpp.getBlueprintsByAuthor("jack");
            fail("An exception was expected after search blueprint from an unexisting author.");
        } catch (BlueprintNotFoundException ex){
        }
    }
}
