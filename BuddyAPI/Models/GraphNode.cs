using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BuddyAPI.Models
{
    public class GraphNode
    {
        //info from pinpoints is transfered into an object of Graph Node
        public GraphNode(int id, double longitude, double latitude, int cost)
        {
            //map_index = index;
            map_pinId = id;
            map_long = longitude;
            map_lat = latitude;
            map_edges = new List<NavEdge>();
            map_cost = cost;
        }

        public void setMap_Cost(int cost) {
            map_cost = cost;
        }
        public double map_long, map_lat; 
        public int map_pinId, map_cost;
        public List<NavEdge> map_edges; //adjacency list
    }
}
