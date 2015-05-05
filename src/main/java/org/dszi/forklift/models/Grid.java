/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dszi.forklift.models;

/**
 *
 * @author Slawek
 */
public class Grid {
    
    GridItem[][] pools;
    
    int _width;
    int _height;   
    
    public Grid() {
        _width = 20;
        _height = 15;
        initPool();
    }

    private void initPool() {
        pools = new GridItem[_width][];

        for (int x = 0; x < _width; x++) {
            pools[x] = new GridItem[_height];
            for (int y = 0; y < _height; y++) {
                pools[x][y] = null;
            }
        }
    }
    
    public void SetObject(GridItem item, int x, int y){
         pools[x][y] = item;
    }
    
    public GridItem GetObject(int x, int y){
        return  pools[x][y];
    }
    
    public void DeleteObject(int x, int y){
         pools[x][y] = null;
    }
    
    public int GetWidth()
    {
        return _width;
    }
    
    public int GetHeight()
    {
        return _height;
    }
}
