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
        int fcost, gcost, hcost;
        
        private BuddyAPIContext _context;
        public AStar(BuddyAPIContext context)
        {
            _context = context;
        }

        public static double getHCost(double currLong, double currLat, double endLong, double endLat)
        {
            return Math.Abs(endLong - currLong) + Math.Abs(endLat - currLat);
        }

        public static double getGCost(double startLong, double startLat, double currLong, double currLat)
        {
            return Math.Abs(startLong - currLong) + Math.Abs(startLat - currLat);
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

        static List<GraphNode> GetSurroundingPins(GraphNode curr ,double longi, double lat, List<GraphNode> visited, List<GraphNode> notVisited)
        {
            List<GraphNode> nearbyNodes = new List<GraphNode>();
            List<GraphNode> final = new List<GraphNode>();
            GraphNode newG = new GraphNode(curr.map_pinId, curr.map_long = longi, curr.map_lat = lat - 1, curr.Floor);
            nearbyNodes.Add(newG);
            newG = new GraphNode(curr.map_pinId, curr.map_long = longi, curr.map_lat = lat + 1, curr.Floor);
            nearbyNodes.Add(newG);
            newG = new GraphNode(curr.map_pinId, curr.map_long = longi - 1, curr.map_lat = lat, curr.Floor);
            nearbyNodes.Add(newG);
            newG = new GraphNode(curr.map_pinId, curr.map_long = longi + 1, curr.map_lat = lat, curr.Floor);
            nearbyNodes.Add(newG);

            //finding whether the surrounding nodes are within the pinpoints
            foreach (GraphNode node in nearbyNodes)
            {
                if (visited.Contains(node))
                {
                    final.Add(node);
                }
            }
            foreach (GraphNode node in nearbyNodes)
            {
                if (notVisited.Contains(node))
                {
                    final.Add(node);
                }
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
            var nodesVisited = new List<GraphNode>();//could be the possible path

            //setting the cost values by calculating them for the start node
            notVisited.Add(start);
            double G = 0;
            double H = getHCost(start.map_long, start.map_lat, end.map_long, end.map_lat);
            double F = getFCost(G, H);
            start.setF(F);
            start.setG(G);
            start.setG(H);

            for (int i = 1; i <= 110; i++)
            {
                //adding all navigation nodes to the list of nodes
                Pinpoints pin = GetPinpointsObject(i);

                if (pin != null)
                {
                    if (pin.pinpointType_Id == 15)
                    {
                        GraphNode node = ConvertToNode(pin);
                        //*********NOT ADDING INTO THE NOTVISITED LIST AS THEY SHOULD BE
                        //setting the cost values by calculating them for every navigation node
                        G = getGCost(start.map_long, start.map_lat, node.map_long, node.map_lat);
                        H = getHCost(node.map_long, node.map_lat, end.map_long, end.map_lat);
                        F = getFCost(G, H);
                        node.setF(F);
                        node.setG(G);
                        node.setG(H);

                        notVisited.Add(node);

                        //navNodesAmount++;
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
            end.setG(H);

            //Pathfinding starts and ends within this loop
            while (notVisited.Count > 0)
            {
                // retrieving node with the lowest FCost
                var lowest = notVisited.Min(l => l.fcost);
                Curr = notVisited.First(l => l.fcost == lowest);

                //retrieving ny surrounding pins that have not been visited yet
                var surroundingPins = GetSurroundingPins(Curr, Curr.map_long, Curr.map_lat, nodesVisited, notVisited);
                foreach (var surrPin in surroundingPins)
                {
                    // if this surrounding pin has already been visited, ignore it
                    if (nodesVisited.FirstOrDefault(l => l.map_long == surrPin.map_long && l.map_lat == surrPin.map_lat) != null)
                        continue;

                    // if it hasn't been visited put it in the notvisited list
                    if (notVisited.FirstOrDefault(l => l.map_long == surrPin.map_long && l.map_lat == surrPin.map_lat) == null)
                    {
                        // compute its costs and set the parent
                        surrPin.gcost = getGCost(start.map_long, start.map_lat, surrPin.map_long, surrPin.map_lat);
                        surrPin.hcost = getHCost(surrPin.map_long, surrPin.map_lat, end.map_long, end.map_lat);
                        surrPin.fcost = getFCost(surrPin.gcost, surrPin.hcost);
                        surrPin.Parent = Curr;

                        // and add it to the open list - could be might have to use insert(0, surrpin)
                        notVisited.Add(surrPin);
                    }
                    else
                    {
                        // test if using the current G score makes the adjacent square's F score
                        // lower, if yes update the parent because it means it's a better path
                        if (surrPin.gcost + surrPin.hcost < surrPin.fcost)
                        {
                            surrPin.gcost = getGCost(start.map_long, start.map_lat, surrPin.map_long, surrPin.map_lat);
                            surrPin.fcost = getFCost(surrPin.gcost, surrPin.hcost);
                            surrPin.Parent = Curr;
                        }
                    }
                }

                // adding current pin to visited list
                nodesVisited.Add(Curr);

                // remove current pin from not visited list
                notVisited.Remove(Curr);

                // if end point added to the visited list, path completed
                if (nodesVisited.FirstOrDefault(l => l.map_long == end.map_long && l.map_lat == end.map_lat) != null) {
                    nodesVisited.Add(end);
                    //foreach (GraphNode g in nodesVisited) {
                        //int id = g.map_pinId;
                        //path.Add(id);
                    //}
                    break;
                }
            }

            int stops = nodesVisited.Count();
            List<int> gettingPath = new List<int>();
            
            //foreach (GraphNode n in nodesVisited) {
                //*************this is the gist of what needs to be done - get the parent node of every element in the nodes visited list - LINE 201 THROWING NULL EXCEPTION
                do
                {
                    int GetpinId = nodesVisited[stops].Parent.map_pinId;
                    gettingPath.Add(GetpinId);
                    stops--;
                } while (nodesVisited[stops].Parent != null);
           // }
            for (int i= nodesVisited.Count(); i>0 ;i--) {
                path.Add(gettingPath[i]);
            }
            
            return path;
        }
    }
}
