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
        public GraphNode(int id, double longitude, double latitude, int floor)
        {
            //map_index = index;
            map_pinId = id;
            map_long = longitude;
            map_lat = latitude;
            Floor = floor;
            //map_edges = new List<NavEdge>();
            //map_cost = cost;
            fcost = 0;
            gcost = 0;
            hcost = 0;
        }

        public void setF(double cost) {
            fcost = cost;
        }

        public void setG(double cost)
        {
            gcost = cost;
        }

        public void setH(double cost)
        {
            hcost = cost;
        }

        public double map_long, map_lat, fcost, gcost, hcost;
        public int map_pinId, map_cost,  Floor;
        public GraphNode Parent;
        //public List<NavEdge> map_edges; //adjacency list
    }
}
