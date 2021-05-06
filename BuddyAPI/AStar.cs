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

        public static List<GraphNode> nodes;
        struct MinPriorityQueue
        {
            public List<NavEdge> map_edges;
            //public List<int> map_costs;

            public NavEdge Add(NavEdge edge, int i, int cost)
            {
                map_edges.Add(edge);
                nodes[i].setMap_Cost(cost);

                return edge;
            }

            public NavEdge Pop()
            {
                //Pop the one with lowest priority
                int lowestPriority = int.MaxValue;
                int index = -1;
                for (int i = 0; i < map_edges.Count; i++)
                {
                    //Size should be the same as the cost list.
                    if (nodes[i].map_cost < lowestPriority)
                    {
                        lowestPriority = nodes[i].map_cost;
                        index = i;
                    }
                }
                NavEdge returnedEdge = map_edges[index];
                map_edges.RemoveAt(index);
                nodes.RemoveAt(index);
                return returnedEdge;
            }
        }

       

        public GraphNode ConvertToNode(Pinpoints pin) {
            GraphNode node = new GraphNode(pin.pinpoint_Id, pin.longitude, pin.latitude, int.MaxValue);
            return node;
        }

        public Pinpoints GetPinpointsObject(int id)
        {
            return _context.Pinpoints.FirstOrDefault(e => e.pinpoint_Id == id);

        }

        public List<int> CalculateAStar(int start, int end)
        {
            List<int> path = new List<int>();
            //List<int> map_costs = new List<int>();
            int navNodesAmount = 0;

            Pinpoints pin1 = GetPinpointsObject(start);
            nodes.Add(ConvertToNode(pin1));

            for (int i=1;i<=110;i++) {
               //adding all navigation nodes to the list of nodes
                Pinpoints pin = GetPinpointsObject(i);
                //int p = pin.pinpointType_Id; 
                
                if (pin != null)
                {

                    if (pin.pinpointType_Id == 15)
                    {
                        nodes.Add(ConvertToNode(pin));
                        navNodesAmount++;
                    }
                }
            }
            Pinpoints pin2 = GetPinpointsObject(end);
            nodes.Add(ConvertToNode(pin2));


            for (int i = 0; i < navNodesAmount; i++)
            {
                //setting the path to null to start a new one
                path.Add(-1);

                //navigation nodes with type 15 are navigation nodes - 54 nav nodes in the database
                //add the largest possible int to the costs
                //map_costs.Add(int.MaxValue);
            }

           // map_costs.Add(end);

            //to avoid repeated visits to the same node
            List<NavEdge> traversed = new List<NavEdge>();
            MinPriorityQueue minQueue = new MinPriorityQueue();
            minQueue.map_edges = new List<NavEdge>();
            //minQueue.nodes = new List<int>();

            GraphNode startNode = ConvertToNode(pin1);
            GraphNode endNode = ConvertToNode(pin2); 

            //map_costs[0] = 0;
            double sourceX = startNode.map_long;
            double sourceY = startNode.map_lat;
            double targetX = endNode.map_long;
            double targetY = endNode.map_lat;

            //GOT TO HERE with liam
            //Add all adjacent nodes to the source, to the min priority queue  
            for (int i = 0; i < nodes[start].map_edges.Count; i++)
            {
                NavEdge edge = nodes[start].map_edges[i];
                //manhattan heuristic cost calculating the total number of squares moved horizontally and vertically to reach the target square from the current
                int nodeX = edge.to;
                int nodeY = edge.to;

                int heuristicCost = Convert.ToInt32(Math.Abs(nodeX - targetX) + Math.Abs(nodeY - targetY));
                //All costs start of at 1
                minQueue.Add(edge, i, 1 + heuristicCost);
            }
            bool TargetNodeFound = false;

            while (minQueue.map_edges.Count > 0)
            {
                //Pop element from minQueue enter into traversedEdges list
                NavEdge curEdge = minQueue.Pop();
                traversed.Add(curEdge);

                //Check if node cost = current edge leads to is greater than previous node cost + cost of the edge
                // if (map_costs[curEdge.to] > map_costs[curEdge.from] + 1)
                if (nodes[curEdge.to].map_cost > nodes[curEdge.from].map_cost + 1)
                {
                    path[curEdge.to] = curEdge.from;
                    nodes[curEdge.to].map_cost = nodes[curEdge.from].map_cost + 1;
                    if (end == curEdge.to)
                    {
                        TargetNodeFound = true;
                        break;
                    }
                    else
                    {
                        //Add adjacent edges to the queue
                        GraphNode curNode = nodes[curEdge.to];
                        for (int i = 0; i < curNode.map_edges.Count; i++)
                        {
                            //If edge already in traversed queue or min-priority queue then  not added
                            if (traversed.Contains(curNode.map_edges[i]))
                            {
                                continue;
                            }
                            if (minQueue.map_edges.Contains(curNode.map_edges[i]))
                            {
                                continue;
                            }
                            //add to queue and priority = current node's cost + the cost of this adjacent edge
                            int nodeX = curNode.map_edges[i].to;
                            int nodeY = curNode.map_edges[i].to;

                            int heuristicCost = Convert.ToInt32(Math.Abs(nodeX - targetX) + Math.Abs(nodeY - targetY));
                            minQueue.Add(curNode.map_edges[i], i, nodes[curNode.map_pinId].map_cost + 1 + heuristicCost);
                        }
                    }
                }
            }
            if (TargetNodeFound) return path;
            else return null;
        }
    }
}
