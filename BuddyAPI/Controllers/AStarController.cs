using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BuddyAPI.Controllers
{ 
    public class AStarController
    {
        //Pathfinding graphs
        struct MinPriorityQueue
        {
            public List<GraphEdge> map_edges;
            public List<int> map_costs;

            public GraphEdge Add(GraphEdge edge, int cost)
            {
                map_edges.Add(edge);
                map_costs.Add(cost);

                return edge;
            }

            public GraphEdge Pop()
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
                GraphEdge returnedEdge = map_edges[index];
                map_edges.RemoveAt(index);
                map_costs.RemoveAt(index);
                return returnedEdge;
            }
        }

        struct GraphEdge
        {
            public GraphEdge(int start, int end)
            {
                from = start;
                to = end;
            }
            public int from;
            public int to;
        }
        struct GraphNode
        {
            public GraphNode(int index)
            {
                map_index = index;
                map_edges = new List<GraphEdge>();
            }
            public int map_index;
            public List<GraphEdge> map_edges;//adjacency list
        }

        struct NavigateMap
        {
            public List<GraphNode> m_nodes;//start node and end needs to be passed as pinpoint latitude and longitude  for both locations
            public List<int> CalculateAStar(int startNode, int endNode)
            {
                List<int> path = new List<int>();
                for (int y = 0; y < 30; y++)
                {
                    for (int x = 0; x < 30; x++)
                    {
                        path.Add(-1);
                        //making the path empty in order to start a new path -> setting the path to null
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
                List<GraphEdge> alreadyTraversed = new List<GraphEdge>();
                MinPriorityQueue minQueue = new MinPriorityQueue();
                minQueue.map_edges = new List<GraphEdge>();
                minQueue.map_costs = new List<int>();

                map_costs[startNode] = 0;
                int targetX = endNode % 30;
                int targetY = endNode / 30;
                int sourceX = startNode % 30;
                int sourceY = startNode / 30;

                //Add all adjacent nodes to the source, to the min priority queue
                for (int i = 0; i < m_nodes[startNode].map_edges.Count; i++)
                {
                    GraphEdge edge = m_nodes[startNode].map_edges[i];
                    //Input manhattan heuristic cost -- still need to understand exactly
                    //computed by calculating the total number of squares moved horizontally and vertically to reach the target square from the current square
                    //https://brilliant.org/wiki/a-star-search/#heuristics
                    int nodeX = edge.to % 30;
                    int nodeY = edge.to / 30;

                    int heuristicCost = Math.Abs(nodeX - targetX) + Math.Abs(nodeY - targetY);
                    //All costs start of at 1 - (in a Grid based program)
                    minQueue.Add(edge, 1 + heuristicCost);
                }
                bool TargetNodeFound = false;

                while (minQueue.map_edges.Count > 0)
                {
                    //Pop element from minQueue and put into traversedEdges list
                    GraphEdge curEdge = minQueue.Pop();
                    alreadyTraversed.Add(curEdge);

                    //Check if the cost of the node the current edge leads to is greater than the cost of the previous node added with the cost of the edge
                    if (map_costs[curEdge.to] > map_costs[curEdge.from] + 1)
                    {
                        path[curEdge.to] = curEdge.from;
                        map_costs[curEdge.to] = map_costs[curEdge.from] + 1;
                        if (endNode == curEdge.to)
                        {
                            TargetNodeFound = true;
                            break;
                        }
                        else
                        {
                            //Add all adjacent edges to the queue using a for loop
                            GraphNode curNode = m_nodes[curEdge.to];
                            for (int i = 0; i < curNode.map_edges.Count; i++)
                            {
                                //If the edge is on the already traversed queue or the min-priority queue then it is not added
                                if (alreadyTraversed.Contains(curNode.map_edges[i]))
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

                                int heuristicCost = Math.Abs(nodeX - targetX) + Math.Abs(nodeY - targetY);
                                minQueue.Add(curNode.map_edges[i], map_costs[curNode.map_index] + 1 + heuristicCost);
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
