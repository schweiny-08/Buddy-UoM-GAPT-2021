using BuddyAPI.Data;
using BuddyAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;


namespace BuddyAPI
{
    public class Navigation
    {
        //initialising all neccessary objects and variables
        static BuddyAPIContext _context;
        List<Pinpoints> path = new();
        int userStart, userEnd, userStartFloorLvl, userEndFloorLvl = 0;
        Pinpoints userStartPin, userEndPin;
        GraphNode userStartNode, userEndNode;
        bool check = false;

        public Navigation(BuddyAPIContext context)
        {
            _context = context;
        }

        public static double getHCost(double endLat, double endLong, double currLat, double currLong)
        {
            return Math.Sqrt(Math.Pow(Math.Abs(endLat - currLat), 2) + Math.Pow(Math.Abs(endLong - currLong), 2));
        }

        public static double getGCost(double startLat, double startLong, double currLat, double currLong)
        {
            return Math.Sqrt(Math.Pow(Math.Abs(startLat - currLat), 2) + Math.Pow(Math.Abs(startLong - currLong), 2));
        }

        //converts pinpoint object to node
        public static GraphNode ConvertToNode(Pinpoints pin)
        {
            GraphNode node = new GraphNode(pin.pinpoint_Id, pin.longitude, pin.latitude, pin.floor_Id);
            return node;
        }

        //returns pinpoint object of a specific ID
        public Pinpoints GetPinpointsObject(int id)
        {
            return _context.Pinpoints.FirstOrDefault(e => e.pinpoint_Id == id);
        }

        //returns list of all pinpoints connected to a specific pinpoint
        public List<Pinpoints> GetConnectingPins(Pinpoints startPin)
        {
            List<Pinpoints> paths = new List<Pinpoints>();
            List<int> pathIds = new List<int>();
            foreach (var p in _context.Paths)
            {
                if (p.pinpoint_Id == startPin.pinpoint_Id)
                {
                    pathIds.Add(p.pinpoint_Id_2);
                }
                else if (p.pinpoint_Id_2 == startPin.pinpoint_Id)
                {
                    pathIds.Add(p.pinpoint_Id);
                }
            }
            foreach(var p in pathIds)
            {
                paths.Add(GetPinpointsObject(p));
            }
            return paths;
        }

        //returns the connected top staircase
        public Pinpoints GetConnectingStaircaseTop(Pinpoints start)
        {
            List<Pinpoints> connPins = GetConnectingPins(start);
            GraphNode connNode;
            Pinpoints topPin = new();
            int floorLvl;
            foreach(var c in connPins)
            {
                connNode = ConvertToNode(c);
                floorLvl = GetFloorLevel(connNode.floorId);
                connNode.setFloorLevel(floorLvl);
                if (connNode.floorLevel > GetFloorLevel(start.floor_Id))
                {
                    topPin = c;
                }
            }
            return topPin;
        }

        //returns the connected bottom staircase
        public Pinpoints GetConnectingStaircaseBottom(Pinpoints start)
        {
            //get pin type 4
            List<Pinpoints> connPins = GetConnectingPins(start);
            GraphNode connNode;
            Pinpoints topPin = new();
            int floorLvl;
            foreach (var c in connPins)
            {
                connNode = ConvertToNode(c);
                floorLvl = GetFloorLevel(connNode.floorId);
                connNode.setFloorLevel(floorLvl);
                if (connNode.floorLevel < GetFloorLevel(start.floor_Id))
                {
                    topPin = c;
                }
            }
            return topPin;
        }

        //returns the node with the corresponding H cost from the given list
        public GraphNode GetNodeByHcost(List<GraphNode> notVisited, double hcost)
        {
            return notVisited.Find(i => i.hcost == hcost);
        }

        //returns the node with the corresponding G cost from the given list
        public GraphNode GetNodeByGcost(List<GraphNode> notVisited, double gcost)
        {
            return notVisited.Find(i => i.gcost == gcost);
        }

        //returns a list of all top staircases
        public List<GraphNode> GetStaircasesTop()
        {
            List<Pinpoints> stairsTopPin = _context.Pinpoints.Where(e => e.pinpointType_Id == 14).ToList();
            List<GraphNode> stairsTopNodes = new List<GraphNode>();
            foreach(var s in stairsTopPin)
            {
                stairsTopNodes.Add(ConvertToNode(s));
            }
            return stairsTopNodes;
        }

        //returns a list of all bottom staircases
        public List<GraphNode> GetStaircasesBottom()
        {
            List<Pinpoints> stairsBottPin = _context.Pinpoints.Where(e => e.pinpointType_Id == 4).ToList();
            List<GraphNode> stairsBottNodes = new List<GraphNode>();
            foreach (var s in stairsBottPin)
            {
                stairsBottNodes.Add(ConvertToNode(s));
            }
            return stairsBottNodes;
        }

        //returns the corresponding floor level from the floor ID provided
        public int GetFloorLevel(int floorId)
        {
            return _context.Floor.FirstOrDefault(e => e.floor_Id == floorId).floorLevel;
        }

        // MAIN NAVIGATION ALGORITHM
        public List<Pinpoints> CalculatePath(int startId, int endId)
        {
            //creating pinpoints for current start and end IDs
            Pinpoints startPin = GetPinpointsObject(startId);
            Pinpoints endPin = GetPinpointsObject(endId);

            //creating nodes for current start and end IDs
            GraphNode startNode = ConvertToNode(startPin);
            GraphNode endNode = ConvertToNode(endPin);

            //setting the floor level for the start and end points
            startNode.setFloorLevel(GetFloorLevel(startPin.floor_Id));
            endNode.setFloorLevel(GetFloorLevel(endPin.floor_Id));

            //storing the staircases in lists for future use
            List<GraphNode> staircasesTop = GetStaircasesTop();
            List<GraphNode> staircasesBottom = GetStaircasesBottom();

            //creating empty Graphnode for stair nodes
            GraphNode stairs = new();
            //creating empty pinpoints to store the next pin
            Pinpoints nextPin = new();

            //getting all connected nodes of the current node
            List<Pinpoints> connections = GetConnectingPins(startPin);

            
            //creating not visited lists for future path calculation
            List<GraphNode> notVisited = new();

            //creating node and pinpoint for connected staircase
            Pinpoints connStaircasePin = new();
            GraphNode connStaircaseNode = new();
            
            //creating empty node for next start point
            GraphNode nextStart = new();

            //creating variables for floor levels and next node ID
            int startFloorLevel = 0;
            int endFloorLevel = 0;
            int nextNodeId;

            //declaring the costs
            double H, G = 0;
            //declaring var for minimum G cost
            double minGCost, minHCost = 0;

            //a boolean used to check if one of the connections is the user end pin
            bool checkIfEnd = false;

            //adds the current start node to the path List
            //this is the final path returned by the algorithm
            path.Add(startPin);


            //checks if first iteration and if true stores the original user start and end points and sets the floor level
            if (check == false)
            {
                userStart = startId;
                userEnd = endId;

                userStartPin = GetPinpointsObject(userStart);
                userStartNode = ConvertToNode(userStartPin);
                userStartFloorLvl = GetFloorLevel(userStartPin.floor_Id);
                userStartNode.setFloorLevel(userStartFloorLvl);

                userEndPin = GetPinpointsObject(userEnd);
                userEndNode = ConvertToNode(userEndPin);
                userEndFloorLvl = GetFloorLevel(userEndPin.floor_Id);
                userEndNode.setFloorLevel(userEndFloorLvl);
                check = true;
            }
            
            //check if start pin floor level is same as end pin floor level
            if (startPin.floor_Id != endPin.floor_Id)
            {
                startFloorLevel = GetFloorLevel(startPin.floor_Id);
                startNode.setFloorLevel(startFloorLevel);

                endFloorLevel = GetFloorLevel(endPin.floor_Id);
                endNode.setFloorLevel(endFloorLevel);

                //path goes up
                if(startNode.floorLevel < endNode.floorLevel)
                {
                    //finding closest staircase bottom to start
                    staircasesBottom = staircasesBottom.FindAll(g => g.floorId == startNode.floorId);
                    //iterating the list of bottom staircases and setting the G cost for each item
                    foreach (var s in staircasesBottom)
                    {
                        G = getGCost(startNode.map_lat, startNode.map_long, s.map_lat, s.map_long);
                        s.setG(G);
                    }
                    /*
                     * finding and storing the node with the minimum G cost to be stored as the end point
                     * this is so that the algorithm knows that it has to direct the user to a staircase
                     * first before attempting to go to the user selected end point
                    */
                    minGCost = staircasesBottom.Min(g => g.gcost);
                    stairs = GetNodeByGcost(staircasesBottom, minGCost);
                    endNode = stairs;
                }
                //path goes down
                else
                {
                    //finding closest staircase top to start
                    staircasesTop = staircasesTop.FindAll(g => g.floorId == startPin.floor_Id);
                    foreach(var s in staircasesTop)
                    {
                        G = getGCost(startNode.map_lat, startNode.map_long, s.map_lat, s.map_long);
                        s.setG(G);
                    }
                    minGCost = staircasesTop.Min(g => g.gcost);
                    stairs = GetNodeByGcost(staircasesTop, minGCost);
                    endNode = stairs;
                }
            }

            //check if the current node is not the end node (path is incomplete)
            if (startNode.map_pinId != endNode.map_pinId)
            {                
                //iterate through connections
                foreach (var c in connections)
                {
                    //converting each connection to a node
                    GraphNode node = ConvertToNode(c);

                    //checking if c is the user end pin meaning we need to jump to user end pin and path calculation is finished
                    if(c == userEndPin)
                    {
                        checkIfEnd = true;
                        break;
                    }

                    //if connected node in current iteration is not in path
                    //ensures that if a node is already in the path it is not visited again to make sure there aren't any infinite loops
                    if (!path.Contains(path.FirstOrDefault(e => e.pinpoint_Id == node.map_pinId)))
                    {
                        //if current node is a navigation node, the end node, a staircase or and entrance/exit to reduce unneccessary computation
                        if (c.pinpointType_Id == 15 || node.map_pinId == endNode.map_pinId || c.pinpointType_Id == 4 || c.pinpointType_Id == 14 || c.pinpointType_Id == 2)
                        {
                            //skip current node if start and end floor level are the same and current node is a staircase
                            if (!(userStartPin.floor_Id == userEndPin.floor_Id && (c.pinpointType_Id == 4 || c.pinpointType_Id == 14)))
                            {
                                //calculating distance of connected node to end node
                                H = getHCost(endNode.map_lat, endNode.map_long, node.map_lat, node.map_long);
                                node.setH(H);
                                //adding the connected node to the not visited list for future comparison
                                notVisited.Add(node);
                            }
                        }
                    }
                }

                //as long as the algorithm hasn't arrived at the user selected end node
                if (checkIfEnd == false)
                {
                    //storing the lowest H Cost from the not visited list
                    minHCost = notVisited.Min(h => h.hcost);

                    //retrieving the ID of the node with lowest H Cost
                    nextNodeId = GetNodeByHcost(notVisited, minHCost).map_pinId;

                    //getting node with corresponding ID and saving as the next node
                    nextPin = GetPinpointsObject(nextNodeId);
                }
                //if algorithm arrived at user selected end node
                else
                {
                    nextNodeId = userEnd;
                    nextPin = userEndPin;
                }

                //if current node is a navigation node or the end node or user selected end node or an entrance/exit
                if (nextPin.pinpointType_Id == 15 || nextPin.pinpoint_Id == endNode.map_pinId || nextPin.pinpoint_Id == userEndNode.map_pinId || nextPin.pinpointType_Id == 2)
                {
                    //recursive call with the best option from connections passed as new start node ID
                    return CalculatePath(nextNodeId, endNode.map_pinId);
                }
            }
            //path calculation finished
            else
            {
                //check if current start node is not the user selected end node
                if (startNode.map_pinId != userEnd)
                {
                    //if start node is a staircase
                    if (startPin.pinpointType_Id == 4 || startPin.pinpointType_Id == 14)
                    {
                        //if path went up
                        if(startNode.floorLevel < userEndNode.floorLevel)
                        {
                            //find connected top staircase and pass that as new start node
                            connStaircasePin = GetConnectingStaircaseTop(startPin);
                            connStaircaseNode = ConvertToNode(connStaircasePin);
                            nextStart = connStaircaseNode;
                            return CalculatePath(nextStart.map_pinId, userEndNode.map_pinId);
                        }
                        else
                        {
                            connStaircasePin = GetConnectingStaircaseBottom(startPin);
                            connStaircaseNode = ConvertToNode(connStaircasePin);
                            nextStart = connStaircaseNode;
                            return CalculatePath(nextStart.map_pinId, userEndNode.map_pinId);
                        }
                    }
                }
                //current start node is the user selected end node meaning path calculation is finished
                else
                {
                    //returning the calculated path
                    return path;
                }
            }
            //returning the calculated path
            return path;
        }
    }
}