using BuddyAPI.Controllers;
using BuddyAPI.Data;
using BuddyAPI.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BuddyAPI
{
    public class AStar
    {   
        private BuddyAPIContext _context;
        public AStar(BuddyAPIContext context)
        {
            _context = context;
        }

        public static double getHCost(double currLong, double currLat, double endLong, double endLat)
        {
            return Math.Sqrt(Math.Pow((Math.Abs(endLong - currLong)), 2) + Math.Pow((Math.Abs(endLat - currLat)), 2));
        }

        public static double getGCost(double startLong, double startLat, double currLong, double currLat)
        {
            return Math.Sqrt(Math.Pow((Math.Abs(startLong - currLong)),2) + Math.Pow((Math.Abs(startLat - currLat)),2));
        }
        
        public static double getFCost(double gcost, double hcost)
        {
            return gcost+hcost;
        }

        public GraphNode ConvertToNode(Pinpoints pin) {
            GraphNode node = new GraphNode(pin.pinpoint_Id, pin.longitude, pin.latitude, pin.floor_Id);
            return node;
        }

        public Pinpoints GetPinpointsObject(int id)
        {
            return _context.Pinpoints.FirstOrDefault(e => e.pinpoint_Id == id);

        }

        public List<GraphNode> GetSurroundingPins(GraphNode curr ,double longi, double lat, List<GraphNode> visited, List<GraphNode> notVisited)
        {
            List<GraphNode> nearbyNodes = new List<GraphNode>();
            List<GraphNode> final = new List<GraphNode>();
            int newId;
            Pinpoints newPin;
            GraphNode newG;
            double G;
            double H ;
            double F ;

            if (curr.map_pinId + 1 != null || curr.map_pinId + 1 != 0)
            {
                newId = curr.map_pinId + 1;
                newPin = GetPinpointsObject(newId);
                if (newPin == null) {}
                else
                {
                    newG = ConvertToNode(newPin);
                    //GraphNode newG = new GraphNode(newId+1, newId.map_long = longi, curr.map_lat = lat, curr.Floor);
                    G = getGCost(curr.map_long, curr.map_lat, newG.map_long, newG.map_lat);
                    H = getHCost(newG.map_long, newG.map_lat, curr.map_long, curr.map_lat);
                    F = getFCost(G, H);
                    newG.setF(F);
                    newG.setG(G);
                    newG.setG(H);
                    nearbyNodes.Add(newG);
                }
                
            }
           

            if (curr.map_pinId + 2 != null || curr.map_pinId + 2 != 0)
            {
                newId = curr.map_pinId + 2;
                newPin = GetPinpointsObject(newId);
                if (newPin == null) {}
                else {
                    newG = ConvertToNode(newPin);
                    //GraphNode newG = new GraphNode(newId+1, newId.map_long = longi, curr.map_lat = lat, curr.Floor);
                    G = getGCost(curr.map_long, curr.map_lat, newG.map_long, newG.map_lat);
                    H = getHCost(newG.map_long, newG.map_lat, curr.map_long, curr.map_lat);
                    F = getFCost(G, H);
                    newG.setF(F);
                    newG.setG(G);
                    newG.setG(H);
                    nearbyNodes.Add(newG);
                }  
            }
           

            if (curr.map_pinId - 1 != null || curr.map_pinId - 1 != 0) {
                newId = curr.map_pinId - 1;
                newPin = GetPinpointsObject(newId);
                if (newPin == null) {} 
                else
                {
                    newG = ConvertToNode(newPin);
                    //GraphNode newG = new GraphNode(newId+1, newId.map_long = longi, curr.map_lat = lat, curr.Floor);
                    G = getGCost(curr.map_long, curr.map_lat, newG.map_long, newG.map_lat);
                    H = getHCost(newG.map_long, newG.map_lat, curr.map_long, curr.map_lat);
                    F = getFCost(G, H);
                    newG.setF(F);
                    newG.setG(G);
                    newG.setG(H);
                    nearbyNodes.Add(newG);
                }
               
            }
            

            if (curr.map_pinId - 2 != null || curr.map_pinId - 2 != 0)
            {
                newId = curr.map_pinId - 2;
                newPin = GetPinpointsObject(newId);
                if (newPin == null) {}
                else {
                    newG = ConvertToNode(newPin);
                    //GraphNode newG = new GraphNode(newId+1, newId.map_long = longi, curr.map_lat = lat, curr.Floor);
                    G = getGCost(curr.map_long, curr.map_lat, newG.map_long, newG.map_lat);
                    H = getHCost(newG.map_long, newG.map_lat, curr.map_long, curr.map_lat);
                    F = getFCost(G, H);
                    newG.setF(F);
                    newG.setG(G);
                    newG.setG(H);
                    nearbyNodes.Add(newG);
                }  
            }
            

            //***MIGHT NEED TO ADD A GET FLOOR IN THE FUTURE 
            //finding whether the surrounding nodes are within the pinpoints
            foreach (GraphNode node in nearbyNodes)
            {
               final.Add(node);
            }

            return final;
        }

        public List<int> CalculateAStar(int startId, int endId)
        {
            List<int> path = new List<int>();
            GraphNode Curr = null;
            Pinpoints pin1 = GetPinpointsObject(startId);
            GraphNode startPin =  ConvertToNode(pin1);
            Pinpoints pin2 = GetPinpointsObject(endId);
            GraphNode endPin = ConvertToNode(pin2);

            var start = new GraphNode (startId, pin1.longitude, pin1.latitude, pin1.floor_Id );
            var end = new GraphNode (endId, pin2.longitude, pin2.latitude, pin2.floor_Id );
            var notVisited = new List<GraphNode>();
            var Visited = new List<GraphNode>();//could be the possible path

            //setting the cost values by calculating them for the start node

            double G = 0;
            double H = getHCost(start.map_long, start.map_lat, end.map_long, end.map_lat);
            double F = getFCost(G, H);
            start.setF(F);
            start.setG(G);
            start.setH(H);
            path.Add(start.map_pinId);
            GraphNode prev = start;
            start.Parent = null;
            for (int i = 36; i <= 204; i++)
            {
                //adding all navigation nodes to the list of nodes
                Pinpoints pin = GetPinpointsObject(i);

                if (pin != null)
                {
                    if (pin.floor_Id ==3 && pin.pinpointType_Id==15)
                    {
                        GraphNode node = ConvertToNode(pin);

                        //setting the cost values by calculating them for every navigation node
                        G = getGCost(start.map_long, start.map_lat, node.map_long, node.map_lat);
                        H = getHCost(node.map_long, node.map_lat, end.map_long, end.map_lat);
                        F = getFCost(G, H);
                        node.setF(F);
                        node.setG(G);
                        node.setH(H);
                        node.Parent = prev;


                        notVisited.Add(node);
                        prev = node;

                    }
                }
            }
            //setting the cost values by calculating them for the end node
            notVisited.Add(end);
            G = getGCost(start.map_long, start.map_lat, end.map_long, end.map_lat);
            H = 0;
            F = getFCost(G, H);
            end.setF(F);
            end.setG(G);
            end.setH(H);
            end.Parent = prev;

            

            //Pathfinding starts and ends within this loop
            while (notVisited.Count > 0)
            {
                GraphNode picked=null;
                int counter = 0;
                // retrieving node with the lowest FCost
                // var lowest = notVisited.Min(l => l.fcost);
                //Curr = notVisited.First(l => l.fcost == lowest);

                foreach (GraphNode g in notVisited) {
                    if(g.Parent != null && counter >0)
                    {
                        if (picked==null)
                            picked = g; 

                        if (g.fcost < g.Parent.fcost && g.fcost<picked.fcost)
                        {
                            picked = g;
                        }

                        if (g.fcost > g.Parent.fcost && g.Parent.fcost<picked.fcost)
                        {
                            picked = g.Parent;
                        }
                    }
                    counter++;
                    //if (g.Parent.fcost < g.fcost)
                       // picked = g.Parent.fcost;

                }

                path.Add(picked.map_pinId);
                // adding current pin to visited list
                Visited.Add(picked);

                // remove current pin from not visited list
                notVisited.Remove(picked);

                //****not working within this loop
                //List<GraphNode> surroundingPins = GetSurroundingPins(Curr, Curr.map_long, Curr.map_lat, Visited, notVisited);
                /*foreach (var surrPin in surroundingPins)
                {
                    // if this surrounding pin has already been visited, ignore it
                    if (Visited.FirstOrDefault(l => l.map_long == surrPin.map_long && l.map_lat == surrPin.map_lat) != null){ }

                    // if it hasn't been visited put it in the notvisited list
                    if (notVisited.FirstOrDefault(l => l.map_long == surrPin.map_long && l.map_lat == surrPin.map_lat) == null)
                    {
                        // computing its costs and setting the parent
                        surrPin.gcost = getGCost(start.map_long, start.map_lat, surrPin.map_long, surrPin.map_lat);
                        surrPin.hcost = getHCost(surrPin.map_long, surrPin.map_lat, end.map_long, end.map_lat);

                        surrPin.fcost = getFCost(surrPin.gcost, surrPin.hcost);
                        surrPin.Parent = Curr;

                        // and add it to the notVisited list - could be might have to use insert(0, surrpin)
                        notVisited.Add(surrPin);
                    }
                    else
                    {
                        // if  current G cost makes the neearby pins's F cost if yes update the parent because it means it's a better path
                        if (surrPin.gcost + surrPin.hcost < surrPin.fcost)
                        {
                            surrPin.gcost = getGCost(start.map_long, start.map_lat, surrPin.map_long, surrPin.map_lat);
                            surrPin.fcost = getFCost(surrPin.gcost, surrPin.hcost);
                            surrPin.Parent = Curr;
                        }
                    }
                }*/



                // if end point added to the visited list, path completed
                if (Visited.FirstOrDefault(l => l.map_long == end.map_long && l.map_lat == end.map_lat) != null) {
                    //Visited.Add(end);
                    //foreach (GraphNode g in Visited) {
                      //  int id = g.map_pinId;
                        //path.Add(id);
                    //}
                    break;
                }
            }

            //int stops = Visited.Count();
            //List<int> gettingPath = new List<int>();
           // gettingPath.Add(end.map_pinId);
            
            /*for (int i=nodesVisited.Count(); i>0;i--) {
                //*************this is the gist of what needs to be done - get the parent node of every element in the nodes visited list - LINE 201 THROWING NULL EXCEPTION
                do//out of range ----FIX IT 
                {
                    int GetpinId = nodesVisited[i].Parent.map_pinId;
                    gettingPath.Add(GetpinId);
                } while (nodesVisited[i].Parent != null);
            }*/
            /*for (int i= nodesVisited.Count(); i>0 ;i--) {
                path.Add(gettingPath[i]);
            }*/
            return path;
        }
    }
}
