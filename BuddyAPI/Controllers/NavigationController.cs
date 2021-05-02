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
    [ApiController]
    public class NavigationController : ControllerBase
    {
        [HttpGet]
        public IActionResult Get(Pinpoints startNode, Pinpoints endNode)
        {
            NavigateMap nav = new NavigateMap();
            return Ok(nav.CalculateAStar(startNode, endNode));
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

        struct NavigateMap
        {
            public List<Pinpoints> nodes;
            //start node and end needs to be passed as objects of GraphNode - info stored there
            public List<int> CalculateAStar(Pinpoints start, Pinpoints end)
            {
                List<int> path = new List<int>();
                for (int y = 0; y < 30; y++)
                {
                    for (int x = 0; x < 30; x++)
                    {
                        //setting the path to null to start a new one
                        path.Add(-1);
                    }
                }

                List<int> map_costs = new List<int>();
                for (int y = 0; y < 30; y++)
                {
                    for (int x = 0; x < 30; x++)
                    {
                        // add the largest possible int to the costs
                        map_costs.Add(int.MaxValue);
                    }
                }

                //to avoid repeated visits to the same node
                List<NavEdge> traversed = new List<NavEdge>();
                MinPriorityQueue minQueue = new MinPriorityQueue();
                minQueue.map_edges = new List<NavEdge>();
                minQueue.map_costs = new List<int>();

                map_costs[start.pinpoint_Id] = 0;
                double targetX = end.longitude;
                double targetY = end.latitude;
                double sourceX = start.longitude;
                double sourceY = start.latitude;

                //Add all adjacent nodes to the source, to the min priority queue
                for (int i = 0; i < nodes[start.pinpoint_Id].map_edges.Count; i++)
                {
                    NavEdge edge = nodes[start.pinpoint_Id].map_edges[i];
                    //Input manhattan heuristic cost -- still need to understand exactly
                    //computed by calculating the total number of squares moved horizontally and vertically to reach the target square from the current square
                    //https://brilliant.org/wiki/a-star-search/#heuristics
                    int nodeX = edge.to % 30;
                    int nodeY = edge.to / 30;

                    int heuristicCost = Convert.ToInt32(Math.Abs(nodeX - targetX) + Math.Abs(nodeY - targetY));
                    //All costs start of at 1 - (in a Grid based program)
                    minQueue.Add(edge, 1 + heuristicCost);
                }
                bool TargetNodeFound = false;

                while (minQueue.map_edges.Count > 0)
                {
                    //Pop element from minQueue and put into traversedEdges list
                    NavEdge curEdge = minQueue.Pop();
                    traversed.Add(curEdge);

                    //Check if the cost of the node the current edge leads to is greater than the cost of the previous node added with the cost of the edge
                    if (map_costs[curEdge.to] > map_costs[curEdge.from] + 1)
                    {
                        path[curEdge.to] = curEdge.from;
                        map_costs[curEdge.to] = map_costs[curEdge.from] + 1;
                        if (end.pinpoint_Id == curEdge.to)
                        {
                            TargetNodeFound = true;
                            break;
                        }
                        else
                        {
                            //Add all adjacent edges to the queue using a for loop
                            Pinpoints curNode = nodes[curEdge.to];
                            for (int i = 0; i < curNode.map_edges.Count; i++)
                            {
                                //If the edge is on the already traversed queue or the min-priority queue then it is not added
                                if (traversed.Contains(curNode.map_edges[i]))
                                {
                                    continue;
                                }
                                if (minQueue.map_edges.Contains(curNode.map_edges[i]))
                                {
                                    continue;
                                }
                                //else add to the queue and set its priority as the current node's cost plus the cost of this adjacent edge
                                int nodeX = curNode.map_edges[i].to % 30;
                                int nodeY = curNode.map_edges[i].to / 30;

                                int heuristicCost = Convert.ToInt32(Math.Abs(nodeX - targetX) + Math.Abs(nodeY - targetY));
                                minQueue.Add(curNode.map_edges[i], map_costs[curNode.pinpoint_Id] + 1 + heuristicCost);
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

