/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dszi.forklift.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import org.dszi.forklift.models.Grid;
import org.dszi.forklift.models.MoveActionTypes;
import org.dszi.forklift.models.TreeItem;

/**
 *
 * @author Slawek
 */
public class TreeState {
    // fringe - struktura danych przechowująca wierzchołki do odwiedzenia
// istate - stan początkowy
 // succ - funkcja następnika
 // goaltest - test spełnienia celu
 // f - funkcja "kosztu"
public ArrayList<MoveActionTypes> treesearch(Grid grid, TreeItem startItem, Point destPoint)
{
    int deep = 100;
    ArrayList<TreeItem> fringe = new ArrayList();
    fringe.add(startItem);
    
    for(int i =0;i < deep;i++)
    {
       if(fringe.isEmpty())
            return new ArrayList();

        TreeItem elem = fringe.get(0);
        fringe.remove(elem);
        
       if (goalTest(elem.GetPoint(), destPoint))
       {
           ArrayList<MoveActionTypes> actions = new ArrayList();
           TreeItem item = elem;
           while(item.GetParent() != null)
           {               
               actions.add(item.GetAction());
               item = item.GetParent();               
           }
           Collections.reverse(actions);
           return actions;
       }

       for (TreeItem item :successor(grid, elem))
       {
           item.SetParent(elem);   
           Boolean exists = false;
           
           for(TreeItem f:fringe)
           {
               if(f.GetPoint().x == item.GetPoint().x && f.GetPoint().y == item.GetPoint().y)
               {
                   exists = true;
                   break;
               }
           }
           
           if(!exists)
           {
                fringe.add(item);
           }
       }    
    }   
    
    return new ArrayList();
}
 

private Boolean goalTest(Point srcPoint, Point descPoint)
{
    return srcPoint.x == descPoint.x && srcPoint.y == descPoint.y;
}

private ArrayList<TreeItem> successor(Grid grid, TreeItem item)
{
     ArrayList<TreeItem> actions = new ArrayList();
     Point point = item.GetPoint();
     MoveActionTypes action = item.GetAction();
     Boolean canRight = true;
     Boolean canLeft = true;
     Boolean canTop = true;
     Boolean canBottom = true;
     
     
     if(action == MoveActionTypes.RIGHT)
     {     
         canLeft = false;
     }
     if(action == MoveActionTypes.LEFT)
     {     
         canRight = false;
     }
     if(action == MoveActionTypes.TOP)
     {     
         canBottom = false;
     }
     if(action == MoveActionTypes.BOTTOM)
     {     
         canTop = false;
     }
     
     
     if(canTop && point.y > 0)
         {           
            if(grid.GetObject(point.x, point.y - 1) == null)
            {
                actions.add(new TreeItem(new Point(point.x  , point.y - 1), MoveActionTypes.TOP));
            }
         }
     
      if(canLeft && point.x > 0)
     {
         if(grid.GetObject(point.x - 1, point.y) == null)
            {
                actions.add(new TreeItem(new Point(point.x - 1 , point.y), MoveActionTypes.LEFT));
            }         
     }
   
        
     if(canRight && point.x < grid.GetWidth() - 1 )
     {
         if(grid.GetObject(point.x + 1, point.y) == null)
            {
                actions.add(new TreeItem(new Point(point.x + 1 , point.y), MoveActionTypes.RIGHT));
            }         
     }
     
      
     if(canBottom && point.y < grid.GetHeight() - 1)
         {           
            if(grid.GetObject(point.x, point.y +1) == null)
            {
                actions.add(new TreeItem(new Point(point.x  , point.y + 1), MoveActionTypes.BOTTOM));
            }
         }
     
     return actions;
}

}
