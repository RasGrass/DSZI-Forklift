/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dszi.forklift.logic;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Slawek
 */
public class MapBeerSpecies {
    
    Map<String, RackObjects> mappingTable;
        
     final RackObjects DEFAULT_RACK = RackObjects.RACK1;
    
    public MapBeerSpecies()
    {
        mappingTable = new HashMap<>();
        InitValues();
    }
    
    private void InitValues()
    {
        mappingTable.put("Not Recognized", RackObjects.RACK1);
        mappingTable.put("English IPA", RackObjects.RACK1);
        mappingTable.put("American Amber Ale", RackObjects.RACK1);
        mappingTable.put("Blonde Ale", RackObjects.RACK1);
        
        mappingTable.put("German Pilsner", RackObjects.RACK2);
        mappingTable.put("American Barleywine", RackObjects.RACK2);
        mappingTable.put("Dunkelweizen", RackObjects.RACK2);
        mappingTable.put("Russian Imperial Stout", RackObjects.RACK2);
        
        mappingTable.put("Witbier", RackObjects.RACK3);
        mappingTable.put("American IPA", RackObjects.RACK3);
        mappingTable.put("Biere de Garde", RackObjects.RACK3);
    }
    
    public RackObjects Map(String species)
    {
        if(mappingTable.containsKey(species))
        {
            return mappingTable.get(species);
        }
        
        return DEFAULT_RACK;
    }
}
