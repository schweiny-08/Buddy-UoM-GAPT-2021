using BuddyAPI.Controllers;
using BuddyAPI.Data;
using BuddyAPI.Models;
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

        public AStar(){}

        struct MinPriorityQueue
        {
            public List<NavEdge> map_edges;
            public List<int> map_costs;

            public NavEdge Add(NavEdge edge, int cost)
            {
                map_edges.Add(edge);
                map_costs.Add(cost);

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
                    if (map_costs[i] < lowestPriority)
                    {
                        lowestPriority = map_costs[i];
                        index = i;
                    }
                }
                NavEdge returnedEdge = map_edges[index];
                map_edges.RemoveAt(index);
                map_costs.RemoveAt(index);
                return returnedEdge;
            }
        }

        public List<GraphNode> nodes;

        public GraphNode ConvertToNode(Pinpoints pin) {
            GraphNode node = new GraphNode(pin.pinpoint_Id, pin.longitude, pin.latitude);
            return node;
        }

        public List<int> CalculateAStar(int start, int end)
        {
            List<int> path = new List<int>();
            List<int> map_costs = new List<int>();
            int navNodesAmount = 0;

            for (int i=0;i<110;i++) { 
                //adding all navigation nodes to the list of nodes
                PinpointsController pinCon = new PinpointsController(_context);
                PinpointTypesController pinTypes = new PinpointTypesController(_context);
                Pinpoints pin = (Pinpoints)pinCon.GetPinpoints(i);
                if (pinTypes.GetPinpointTypes(pin.pinpoint_Id).Equals(15))
                {
                    nodes.Add(ConvertToNode(pin));
                    navNodesAmount++;
                }
            }

            for (int i = 0; i < navNodesAmount; i++)
            {
                //setting the path to null to start a new one
                path.Add(-1);

                //navigation nodes with type 15 are navigation nodes - 54 nav nodes in the database
                // add the largest possible int to the costs
                map_costs.Add(int.MaxValue);
            }

            //to avoid repeated visits to the same node
            List<NavEdge> traversed = new List<NavEdge>();
            MinPriorityQueue minQueue = new MinPriorityQueue();
            minQueue.map_edges = new List<NavEdge>();
            minQueue.map_costs = new List<int>();

            //***********I THINK THIS IS INCORRECT ****************
            PinpointsController pc = new PinpointsController(_context);
            Pinpoints pin1 = (Pinpoints)pc.GetPinpoints(start);
            Pinpoints pin2 = (Pinpoints)pc.GetPinpoints(end);
            GraphNode startNode = ConvertToNode(pin1);
            GraphNode endNode = ConvertToNode(pin2); 

            map_costs[start] = 0;
            double sourceX = startNode.map_long;
            double sourceY = startNode.map_lat;
            double targetX = endNode.map_long;
            double targetY = endNode.map_long;

            //Add all adjacent nodes to the source, to the min priority queue
            for (int i = 0; i < nodes[start].map_edges.Count; i++)
            {
                NavEdge edge = nodes[start].map_edges[i];
                //manhattan heuristic cost calculating the total number of squares moved horizontally and vertically to reach the target square from the current
                int nodeX = edge.to;
                int nodeY = edge.to;

                int heuristicCost = Convert.ToInt32(Math.Abs(nodeX - targetX) + Math.Abs(nodeY - targetY));
                //All costs start of at 1
                minQueue.Add(edge, 1 + heuristicCost);
            }
            bool TargetNodeFound = false;

            while (minQueue.map_edges.Count > 0)
            {
                //Pop element from minQueue enter into traversedEdges list
                NavEdge curEdge = minQueue.Pop();
                traversed.Add(curEdge);

                //Check if node cost = current edge leads to is greater than previous node cost + cost of the edge
                if (map_costs[curEdge.to] > map_costs[curEdge.from] + 1)
                {
                    path[curEdge.to] = curEdge.from;
                    map_costs[curEdge.to] = map_costs[curEdge.from] + 1;
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
                            minQueue.Add(curNode.map_edges[i], map_costs[curNode.map_pinId] + 1 + heuristicCost);
                        }
                    }
                }
            }
            if (TargetNodeFound) return path;
            else return null;
        }
    }
}
