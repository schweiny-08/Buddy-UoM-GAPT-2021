using BuddyAPI.Data;
using BuddyAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BuddyAPI.Controllers
{
    [Route("api/[controller]")]
    //[ApiController]
    public class NavigationController : ControllerBase
    {
        //GET: api/Navigation
        [HttpGet]
        public async Task<ActionResult<List<int>>>GetNavigation(int startNode, int endNode)
        {
            NavigateMap nav = new NavigateMap();
            List<int> path = nav.CalculateAStar(startNode, endNode); // gave error
            return path;
        }

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

        struct GraphNode
        {
            //info from pinpoints is transfered into an object of Graph Node
            public GraphNode(int id, int longitude, int latitude)
            {
                //map_index = index;
                map_pinId = id;
                map_long = longitude;
                map_lat = latitude;
                map_edges = new List<NavEdge>();
            }
            public int map_long, map_lat, map_pinId;
            public List<NavEdge> map_edges; //adjacency list

            public static explicit operator GraphNode(Task<ActionResult<Pinpoints>> v)
            {
                throw new NotImplementedException(); // thrown
            }
        }

        struct NavigateMap
        {
            public List<GraphNode> nodes;
            private BuddyAPIContext context;

            public List<int> CalculateAStar(int start, int end)
            {
                List<int> path = new List<int>();
                for (int i = 0; i < 54; i++)
                {
                        //setting the path to null to start a new one
                        path.Add(-1);
                }

                List<int> map_costs = new List<int>();
                //navigation nodes with type 15 aree navigation nodes - 54 nav nodes in the database
                for (int i=0; i < 54; i++)
                {
                        // add the largest possible int to the costs
                        map_costs.Add(int.MaxValue);
                }

                //to avoid repeated visits to the same node
                List<NavEdge> traversed = new List<NavEdge>();
                MinPriorityQueue minQueue = new MinPriorityQueue();
                minQueue.map_edges = new List<NavEdge>();
                minQueue.map_costs = new List<int>();

                //***********I THINK THIS IS INCORRECT ****************
                PinpointsController pc = new PinpointsController(context);
                GraphNode endPin = (GraphNode)pc.GetPinpoints(end); // gives error 
                GraphNode startPin = (GraphNode)pc.GetPinpoints(start);
                //**********need a way to send the long and lat of start/finish into the variables
                map_costs[start] = 0;
                double targetX = endPin.map_long;
                double targetY = endPin.map_long;
                double sourceX = startPin.map_long;
                double sourceY = startPin.map_lat;

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
}

