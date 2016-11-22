function setAdaptedTraveltime(vehID, begTime, endTime, edgeID, time)
%setAdaptedTraveltime Assign edge's travel time to vehicle's container.
%   setAdaptedTraveltime(VEHID,BEGTIME,ENDTIME,EDGEID,TIME) Inserts the 
%   information about the travel time of edge EDGEID valid from BEGTIME 
%   time to ENDTIME time into the vehicle's internal edge weights container.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setAdaptedTraveltime.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message

traci.beginMessage(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_EDGE_TRAVELTIME, vehID,...
    1+4+1+4+1+4+1+4+length(edgeID)+1+8);
message.string = [message.string uint8(sscanf(constants.TYPE_COMPOUND,'%x')) ...
    traci.packInt32(4) uint8(sscanf(constants.TYPE_INTEGER,'%x')) ...
    traci.packInt32(begTime) uint8(sscanf(constants.TYPE_INTEGER,'%x')) ...
    traci.packInt32(endTime) uint8(sscanf(constants.TYPE_STRING,'%x')) ...
    traci.packInt32(length(edgeID)) uint8(edgeID)];
message.string = [message.string uint8(sscanf(constants.TYPE_DOUBLE,'%x')) ...
    traci.packInt64(time)];
traci.sendExact();