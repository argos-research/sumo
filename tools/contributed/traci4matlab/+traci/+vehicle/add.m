function add(vehID, routeID, varargin)
%add Add a vehicle to the SUMO network
%   add(VEHID,ROUTEID) Adds a vehicle in the current time-step with ID 
%   VEHID and assigns the route with ID ROUTEID to it.
%   add(...,DEPART) Specify the departure time in seconds.
%   add(...,POS) Specify the position relative to the starting lane.
%   add(...,SPEED) Specify the starting speed of the vehicle.
%   add(...,LANE) Specify the lane number in which the vehicle will start.
%   add(...,TYPEID) Specify the type of the vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: add.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message

p = inputParser;
p.FunctionName = 'vehicle.add';
p.addRequired('vehID',@ischar)
p.addRequired('routeID',@ischar)
p.addOptional('depart', -3, @isnumeric)   % -3 = DEPART_NOW
p.addOptional('pos', 0, @isnumeric)
p.addOptional('speed', 0, @isnumeric)
p.addOptional('lane', 0, @isnumeric)
p.addOptional('typeID', 'DEFAULT_VEHTYPE', @ischar)
p.parse(vehID, routeID, varargin{:})

vehID = p.Results.vehID;
routeID = p.Results.routeID;
depart = p.Results.depart;
pos = p.Results.pos;
speed = p.Results.speed;
lane = p.Results.lane;
typeID = p.Results.typeID;


traci.beginMessage(constants.CMD_SET_VEHICLE_VARIABLE, constants.ADD, vehID,...
    1+4 + 1+4+length(typeID) + 1+4+length(routeID) + 1+4 + 1+8 + 1+8 + 1+1);
if depart > 0
    depart = depart*1000;
end

message.string = [message.string uint8(sscanf(constants.TYPE_COMPOUND,'%x')) ...
    traci.packInt32(6)];
message.string = [message.string uint8(sscanf(constants.TYPE_STRING,'%x')) ...
    traci.packInt32(length(typeID)) uint8(typeID)];
message.string = [message.string uint8(sscanf(constants.TYPE_STRING,'%x')) ...
    traci.packInt32(length(routeID)) uint8(routeID)];
message.string = [message.string uint8(sscanf(constants.TYPE_INTEGER,'%x')) ...
    traci.packInt32(depart)];
message.string = [message.string uint8(sscanf(constants.TYPE_DOUBLE,'%x')) ...
    traci.packInt64(pos) uint8(sscanf(constants.TYPE_DOUBLE,'%x')) ...
    traci.packInt64(speed)];
message.string = [message.string uint8([sscanf(constants.TYPE_BYTE,'%x') uint8(lane)])];
traci.sendExact();