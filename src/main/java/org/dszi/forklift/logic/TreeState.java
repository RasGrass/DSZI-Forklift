/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dszi.forklift.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
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
public ArrayList<MoveActionTypes> treesearch(TreeItem startItem, Point destPoint, Grid grid)
{
    ArrayList<Point> points = new  ArrayList<>();
    PriorityQueue<TreeItem> fringe = new PriorityQueue<>(1, idComparator);
    startItem.SetPriority(0);
    fringe.add(startItem);
    
    int step = 1;
    while(!fringe.isEmpty())
    {
       TreeItem elem = fringe.poll();
        
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
           item.SetPriority(step + heurestic(item.GetPoint(), destPoint));
           
           if(!points.contains(item.GetPoint()))
           {
               points.add(item.GetPoint());
               fringe.add(item);
           }
       } 
      step++;
    }   
    
    return new ArrayList();
}
 

private Boolean goalTest(Point srcPoint, Point descPoint)
{
    return srcPoint.x == descPoint.x && srcPoint.y == descPoint.y;
}

private int heurestic(Point currentPoint, Point destPoint)
{
    int dx = Math.abs(currentPoint.x - destPoint.x);
    int dy = Math.abs(currentPoint.y - destPoint.y);
    
    return dx+ dy;
}

   //Comparator anonymous class implementation
    public static Comparator<TreeItem> idComparator = new Comparator<TreeItem>(){
         
        @Override
        public int compare(TreeItem t1, TreeItem t2) {
            return (int) (t1.GetPriority() - t2.GetPriority());
        }
    };

private ArrayList<TreeItem> successor(Grid grid, TreeItem item)
{
     ArrayList<TreeItem> actions = new ArrayList();
     Point point = item.GetPoint();
     
     if( point.y > 0)
         {           
            if(grid.GetObject(point.x, point.y - 1) == null)
            {
                actions.add(new TreeItem(new Point(point.x  , point.y - 1), MoveActionTypes.TOP));
            }
         }
     
     if( point.x > 0)
     {
         if(grid.GetObject(point.x - 1, point.y) == null)
            {
                actions.add(new TreeItem(new Point(point.x - 1 , point.y), MoveActionTypes.LEFT));
            }         
     }  
        
     if(point.x < grid.GetWidth() - 1 )
     {
         if(grid.GetObject(point.x + 1, point.y) == null)
            {
                actions.add(new TreeItem(new Point(point.x + 1 , point.y), MoveActionTypes.RIGHT));
            }         
     }
           
     if(point.y < grid.GetHeight() - 1)
         {           
            if(grid.GetObject(point.x, point.y +1) == null)
            {
                actions.add(new TreeItem(new Point(point.x  , point.y + 1), MoveActionTypes.BOTTOM));
            }
         }
     
     return actions;
}

}
