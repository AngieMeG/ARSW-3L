/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.model.Filter;

import edu.eci.arsw.blueprints.model.Filter.*;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Arrays;

public class BlueprintsFiltersTest {
    
    @Test
    public void RedundacyFilterTest(){
        BlueprintsFilter bf = new RedundacyFilter();
        Point[] pts=new Point[]{new Point(1, 1), new Point(1, 1), new Point(2, 2),
            new Point(3, 3), new Point(3, 3), new Point(4, 4),
            new Point(5, 5), new Point(6, 6), new Point(7, 7), new Point(7, 7)};

        Blueprint blueprint = new Blueprint("Tester", "RedundancyTest",pts);
        List<Point> ans = Arrays.asList(new Point[]{new Point(1, 1), new Point(2, 2), 
            new Point(3, 3), new Point(4, 4), new Point(5, 5), new Point(6, 6), new Point(7, 7)}); 
        
        List<Point> filteredPoints = bf.applyFilter(blueprint).getPoints();
        assertEquals(ans,filteredPoints);
        
    }

    @Test
    public void SubsampleFilterTest(){
        BlueprintsFilter bf = new SubsampleFilter();
        Point[] pts=new Point[]{new Point(1, 1), new Point(1, 1), new Point(2, 2),
            new Point(3, 3), new Point(3, 3), new Point(4, 4),
            new Point(5, 5), new Point(6, 6), new Point(7, 7)};

        Blueprint blueprint = new Blueprint("Tester", "RedundancyTest",pts);
        List<Point> ans = Arrays.asList(new Point[]{new Point(1, 1), new Point(1, 1),
            new Point(3, 3), new Point(3, 3), new Point(5, 5), new Point(6, 6)}); 
        
        List<Point> filteredPoints = bf.applyFilter(blueprint).getPoints();
        assertEquals(ans,filteredPoints);
    }
}
