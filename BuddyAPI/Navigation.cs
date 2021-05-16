<<<<<<< Updated upstream:BuddyAPI/AStar.cs
﻿using BuddyAPI.Data;
using BuddyAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;


namespace BuddyAPI
{
    public class AStar
    {
        static BuddyAPIContext _context;
        List<GraphNode> path = new List<GraphNode>();
        //GraphNode originalStart, originalEnd = null;
        int userStart, userEnd, userStartFloorLvl, userEndFloorLvl = 0;
        Pinpoints userStartPin, userEndPin;
        GraphNode userStartNode, userEndNode;
        bool check = false;

        public AStar(BuddyAPIContext context)
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

        public static double getFCost(double gcost, double hcost)
        {
            return gcost + hcost;
        }

        public GraphNode ConvertToNode(Pinpoints pin)
        {
            GraphNode node = new GraphNode(pin.pinpoint_Id, pin.longitude, pin.latitude, pin.floor_Id);
            return node;
        }

        public Pinpoints GetPinpointsObject(int id)
        {
            return _context.Pinpoints.FirstOrDefault(e => e.pinpoint_Id == id);

        }

        public List<int> GetConnectingNodeId(int id)
        {
            List<int> paths = new List<int>();
            foreach (var p in _context.Paths)
            {
                if (p.pinpoint_Id == id)
                {
                    paths.Add(p.pinpoint_Id_2);
                }
                else if (p.pinpoint_Id_2 == id)
                {
                    paths.Add(p.pinpoint_Id);
                }
            }
            return paths;
        }

        public List<int> GetConnectingStaircaseId(int id)
        {
            List<int> connNodes = GetConnectingNodeId(id);
            List<int> connStaircaseId = new List<int>();
            Pinpoints connStairCasePin = new Pinpoints();
            foreach (var c in connNodes)
            {
                connStairCasePin = GetPinpointsObject(c);
                //connStairCasePin2 = GetPinpointsObject(p.pinpoint_Id_2);
                if (connStairCasePin.pinpointType_Id == 4 || connStairCasePin.pinpointType_Id == 14)
                {
                    connStaircaseId.Add(c);
                }
            }
            return connStaircaseId;
        }

        public int GetConnectingStaircaseTop(GraphNode start)
        {
            //get pin type 14
            List<int> connNodes = GetConnectingNodeId(start.map_pinId);
            Pinpoints connPins;
            GraphNode connNode;
            int topId = 0;
            int floorLvl;
            foreach(var c in connNodes)
            {
                connPins = GetPinpointsObject(c);
                connNode = ConvertToNode(connPins);
                floorLvl = GetFloorLevel(connPins.floor_Id);
                connNode.setFloorLevel(floorLvl);
                if (connNode.floorLevel > start.floorLevel)
                {
                    topId = c;
                }
            }
            return topId;
        }

        public int GetConnectingStaircaseBottom(GraphNode start)
        {
            //get pin type 4
            List<int> connNodes = GetConnectingNodeId(start.map_pinId);
            Pinpoints connPins;
            GraphNode connNode;
            int topId = 0;
            int floorLvl;
            foreach (var c in connNodes)
            {
                connPins = GetPinpointsObject(c);
                connNode = ConvertToNode(connPins);
                floorLvl = GetFloorLevel(connPins.floor_Id);
                connNode.setFloorLevel(floorLvl);
                if (connNode.floorLevel < start.floorLevel)
                {
                    topId = c;
                }
            }
            return topId;
        }

        public GraphNode GetMatchingNode(List<GraphNode> notVisited, int id)
        {
            return notVisited.Find(i => i.map_pinId == id);
        }

        public GraphNode GetNodeByFcost(List<GraphNode> notVisited, double fcost)
        {
            return notVisited.Find(i => i.fcost == fcost);
        }

        public GraphNode GetNodeByHcost(List<GraphNode> notVisited, double hcost)
        {
            return notVisited.Find(i => i.hcost == hcost);
        }

        public GraphNode GetNodeByGcost(List<GraphNode> notVisited, double gcost)
        {
            return notVisited.Find(i => i.gcost == gcost);
        }

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

        public int GetFloorLevel(int floorId)
        {
            return _context.Floor.FirstOrDefault(e => e.floor_Id == floorId).floorLevel;
        }

        public List<int> CalculatePath(int startId, int endId)
        {
            //getting all connected nodes of the current node
            List<int> connections = GetConnectingNodeId(startId);
            List<int> finalPath = new List<int>();
            List<GraphNode> staircasesTop = GetStaircasesTop();
            List<GraphNode> staircasesBottom = GetStaircasesBottom();

            //checks if first iteration and if true stores the original user start and end points
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
                //Pinpoints start = GetPinpointsObject(userStart);
                //originalStart = ConvertToNode(start);
                //Pinpoints end = GetPinpointsObject(userEnd);
                //originalEnd = ConvertToNode(end);
            }

            //creating nodes for current start and end IDs
            Pinpoints pin1 = GetPinpointsObject(startId);
            GraphNode startPin = ConvertToNode(pin1);
            Pinpoints pin2 = GetPinpointsObject(endId);
            GraphNode endPin = ConvertToNode(pin2);

            //adds the current start node to the path List
            path.Add(startPin);

            //creating visited and not visited lists for future path calculation
            var notVisited = new List<GraphNode>();
            var Visited = new List<GraphNode>();

            var startFloorLevel = 0;
            var endFloorLevel = 0;

            //creating nodes from user original start and end IDs
            Pinpoints startPinFinal = GetPinpointsObject(userStart);
            GraphNode startNode = ConvertToNode(startPinFinal);
            Pinpoints endPinFinal = GetPinpointsObject(userEnd);
            GraphNode endNode = ConvertToNode(endPinFinal);
            
            //declaring the costs
            double H = 0;   //distance from current to end node
            double G = 0;

            //check if start pin floor level is same as end pin floor level
            if (startPin.floorId != endPin.floorId)
            {
                startFloorLevel = GetFloorLevel(startPin.floorId);
                startPin.setFloorLevel(startFloorLevel);
                endFloorLevel = GetFloorLevel(endPin.floorId);
                endPin.setFloorLevel(endFloorLevel);
                //path goes up
                if(startPin.floorLevel < endPin.floorLevel)
                {
                    //finding closest staircase bottom to start
                    staircasesBottom = staircasesBottom.FindAll(g => g.floorId == startPin.floorId);
                    foreach (var s in staircasesBottom)
                    {
                        G = getGCost(startPin.map_lat, startPin.map_long, s.map_lat, s.map_long);
                        s.setG(G);
                    }
                    double minGCost = staircasesBottom.Min(g => g.gcost);
                    GraphNode stairs = GetNodeByGcost(staircasesBottom, minGCost);
                    endPin = stairs;
                }
                //path goes down
                else
                {
                    //finding closest staircase top to start
                    staircasesTop = staircasesTop.FindAll(g => g.floorId == startPin.floorId);
                    foreach(var s in staircasesTop)
                    {
                        G = getGCost(startPin.map_lat, startPin.map_long, s.map_lat, s.map_long);
                        s.setG(G);
                    }
                    //staircasesTop.Min(g => g.gcost);
                    double minGCost = staircasesTop.Min(g => g.gcost);
                    GraphNode stairs = GetNodeByGcost(staircasesTop, minGCost);
                    endPin = stairs;
                }
            }

            //check if the current node is the end node (path is complete)
            if (startPin.map_pinId != endPin.map_pinId)
            {
                //converting connections to an int list
                List<int> conn = connections.ToList();
                Pinpoints nextPin;
                
                //iterate through connections
                foreach (var c in conn)
                {
                    //converting each connection to a node
                    Pinpoints pin = GetPinpointsObject(c);
                    GraphNode node = ConvertToNode(pin);

                    //if connected node in current iteration is not in path (not visited)
                    if (!path.Contains(path.FirstOrDefault(e => e.map_pinId == node.map_pinId)))
                    {
                        //if current node is a navigation node or the end node
                        if (pin.pinpointType_Id == 15 || node.map_pinId == endNode.map_pinId || pin.pinpointType_Id == 4 || pin.pinpointType_Id == 14)
                        {
                            //calculating distance of connected node to end node
                            H = getHCost(endPin.map_lat, endPin.map_long, node.map_lat, node.map_long);
                            node.setH(H);
                            //adding the connected node to the not visited list for future comparison
                            notVisited.Add(node);
                        }
                    }
                }

                //storing the lowest H Cost from the not visited list
                double minHCost = notVisited.Min(h => h.hcost);
                //retrieving the ID of the node with lowest H Cost
                int nextNodeId = GetNodeByHcost(notVisited, minHCost).map_pinId;
                //getting node with corresponding ID and saving as the next node
                nextPin = GetPinpointsObject(nextNodeId);

                

                //if current node is a navigation node or the end node
                if (nextPin.pinpointType_Id == 15 || nextPin.pinpoint_Id == endNode.map_pinId || nextPin.pinpoint_Id == endPin.map_pinId)
                {
                    //recursive call with the best option from connections passed as new start node ID
                    return CalculatePath(nextNodeId, endPin.map_pinId);
                }
            }
            //path calculation finished
            else
            {
                //creating node from end ID
                Pinpoints pin = GetPinpointsObject(endId);
                GraphNode node = ConvertToNode(pin);
                Pinpoints connStaircasePin;
                GraphNode connStaircaseNode, nextStart;
                int nextStairCase;
                List<int> connStaircase = new List<int>();
                if (startPin.map_pinId != userEnd)
                {
                    if (pin1.pinpointType_Id == 4 || pin1.pinpointType_Id == 14)
                    {
                        if(startPin.floorLevel < userEndNode.floorLevel)
                        {
                            nextStairCase = GetConnectingStaircaseTop(startPin);
                            connStaircasePin = GetPinpointsObject(nextStairCase);
                            connStaircaseNode = ConvertToNode(connStaircasePin);
                            nextStart = connStaircaseNode;
                            endPin = endNode;
                            return CalculatePath(nextStart.map_pinId, endPin.map_pinId);
                        }
                        else
                        {
                            nextStairCase = GetConnectingStaircaseBottom(startPin);
                            connStaircasePin = GetPinpointsObject(nextStairCase);
                            connStaircaseNode = ConvertToNode(connStaircasePin);
                            nextStart = connStaircaseNode;
                            endPin = endNode;
                            return CalculatePath(nextStart.map_pinId, endPin.map_pinId);
                        }
                    }
                }
                else
                {
                    //iterating through path list
                    foreach (var o in path)
                    {
                        //populating final path list with IDs of nodes in path
                        finalPath.Add(o.map_pinId);
                    }
                    return finalPath;
                }
            }
            //returning the calculated path
            return finalPath;
        }
    }
}
=======
﻿using BuddyAPI.Data;
using BuddyAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;


namespace BuddyAPI
{
    public class Navigation
    {
        static BuddyAPIContext _context;
        List<Pinpoints> path = new();
        //GraphNode originalStart, originalEnd = null;
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

        public static double getFCost(double gcost, double hcost)
        {
            return gcost + hcost;
        }

        public static GraphNode ConvertToNode(Pinpoints pin)
        {
            GraphNode node = new GraphNode(pin.pinpoint_Id, pin.longitude, pin.latitude, pin.floor_Id);
            return node;
        }

        public Pinpoints GetPinpointsObject(int id)
        {
            return _context.Pinpoints.FirstOrDefault(e => e.pinpoint_Id == id);
        }

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

        public List<Pinpoints> GetConnectingStaircasePin(int id)
        {
            List<Pinpoints> connPins = GetConnectingPins(GetPinpointsObject(id));
            List<Pinpoints> connStaircasePins = new List<Pinpoints>();
            foreach (var c in connPins)
            {
                //connStairCasePin2 = GetPinpointsObject(p.pinpoint_Id_2);
                if (c.pinpointType_Id == 4 || c.pinpointType_Id == 14)
                {
                    connStaircasePins.Add(c);
                }
            }
            return connStaircasePins;
        }

        public Pinpoints GetConnectingStaircaseTop(Pinpoints start)
        {
            //get pin type 14
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

        public GraphNode GetMatchingNode(List<GraphNode> notVisited, int id)
        {
            return notVisited.Find(i => i.map_pinId == id);
        }

        public GraphNode GetNodeByFcost(List<GraphNode> notVisited, double fcost)
        {
            return notVisited.Find(i => i.fcost == fcost);
        }

        public GraphNode GetNodeByHcost(List<GraphNode> notVisited, double hcost)
        {
            return notVisited.Find(i => i.hcost == hcost);
        }

        public GraphNode GetNodeByGcost(List<GraphNode> notVisited, double gcost)
        {
            return notVisited.Find(i => i.gcost == gcost);
        }

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

        public int GetFloorLevel(int floorId)
        {
            return _context.Floor.FirstOrDefault(e => e.floor_Id == floorId).floorLevel;
        }

        public List<Pinpoints> CalculatePath(int startId, int endId)
        {
            //creating nodes for current start and end IDs
            Pinpoints startPin = GetPinpointsObject(startId);
            Pinpoints endPin = GetPinpointsObject(endId);
            
            GraphNode startNode = ConvertToNode(startPin);
            GraphNode endNode = ConvertToNode(endPin);

            startNode.setFloorLevel(GetFloorLevel(startPin.floor_Id));
            endNode.setFloorLevel(GetFloorLevel(endPin.floor_Id));

            List<GraphNode> staircasesTop = GetStaircasesTop();
            List<GraphNode> staircasesBottom = GetStaircasesBottom();

            GraphNode stairs = new();
            Pinpoints nextPin = new();

            //getting all connected nodes of the current node
            List<Pinpoints> connections = GetConnectingPins(startPin);
            List<GraphNode> finalPath = new();


            //creating visited and not visited lists for future path calculation
            List<GraphNode> notVisited = new();
            List<GraphNode> Visited = new();

            Pinpoints connStaircasePin = new();
            GraphNode connStaircaseNode = new();
            GraphNode nextStart = new();
            //int nextStairCase;

            //creating variables for floor levels
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
            path.Add(startPin);


            //checks if first iteration and if true stores the original user start and end points
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
                    foreach (var s in staircasesBottom)
                    {
                        G = getGCost(startNode.map_lat, startNode.map_long, s.map_lat, s.map_long);
                        s.setG(G);
                    }
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
                    //staircasesTop.Min(g => g.gcost);
                    minGCost = staircasesTop.Min(g => g.gcost);
                    stairs = GetNodeByGcost(staircasesTop, minGCost);
                    endNode = stairs;
                }
            }

            //check if the current node is the end node (path is complete)
            if (startNode.map_pinId != endNode.map_pinId)
            {
                //converting connections to an int list
                //conn = connections.ToList();
                
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

                    //if connected node in current iteration is not in path (not visited)
                    if (!path.Contains(path.FirstOrDefault(e => e.pinpoint_Id == node.map_pinId)))
                    {
                        //if current node is a navigation node or the end node
                        if (c.pinpointType_Id == 15 || node.map_pinId == endNode.map_pinId || c.pinpointType_Id == 4 || c.pinpointType_Id == 14)
                        {
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

                if (checkIfEnd == false)
                {
                    //storing the lowest H Cost from the not visited list
                    minHCost = notVisited.Min(h => h.hcost);

                    //retrieving the ID of the node with lowest H Cost
                    nextNodeId = GetNodeByHcost(notVisited, minHCost).map_pinId;

                    //getting node with corresponding ID and saving as the next node
                    nextPin = GetPinpointsObject(nextNodeId);
                }
                else
                {
                    nextNodeId = userEnd;
                    nextPin = userEndPin;
                }

                //if current node is a navigation node or the end node
                if (nextPin.pinpointType_Id == 15 || nextPin.pinpoint_Id == endNode.map_pinId || nextPin.pinpoint_Id == userEndNode.map_pinId)
                {
                    //recursive call with the best option from connections passed as new start node ID
                    return CalculatePath(nextNodeId, endNode.map_pinId);
                }
            }
            //path calculation finished
            else
            {                
                
                List<int> connStaircase = new List<int>();
                if (startNode.map_pinId != userEnd)
                {
                    if (startPin.pinpointType_Id == 4 || startPin.pinpointType_Id == 14)
                    {
                        if(startNode.floorLevel < userEndNode.floorLevel)
                        {
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
                else
                {
                    ////iterating through path list
                    //foreach (var o in path)
                    //{
                    //    //populating final path list with IDs of nodes in path
                    //    finalPath.Add(o.map_pinId);
                    //}
                    return path;
                }
            }
            //returning the calculated path
            return path;
        }
    }
}
>>>>>>> Stashed changes:BuddyAPI/Navigation.cs
