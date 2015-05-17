/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dszi.forklift.models;

import java.awt.Point;

/**
 *
 * @author Slawek
 */
public class TreeItem {
    private final Point _point;
    private final MoveActionTypes _action;
    private TreeItem _parent;
    private int _priority;
    private int _cost;
    
    public TreeItem(Point point, MoveActionTypes action)
    {
        _point = point;
        _action = action;
    }
    
    public Point GetPoint()
    {
        return _point;
    }
    
    public MoveActionTypes GetAction()
    {
        return _action;
    }
    
    public TreeItem GetParent()
    {
        return _parent;
    }
    
    public void SetParent(TreeItem item)
    {
        _parent = item;
    }
    
    public void SetPriority(int priority)
    {
        _priority = priority;
    }
    
    public int GetPriority()
    {
        return _priority;
    }
    
    public void SetCost(int cost)
    {
        _cost = cost;
    }
    
    public int GetCost()
    {
        return _cost;
    }
}
