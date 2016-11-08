function setEffort(vehID, begTime, endTime, edgeID, effort)
%setEffort Inserts the information about the effort of the given edge.
%   setEffort(VEHID,BEGTIME,ENDTIME,EDGEID,EFFORT) Inserts the information 
%   about the effort of edge EDGEID valid from BEGTIME time to ENDTIME time
%   into the vehicle's internal edge weights container.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setEffort.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message

traci.beginMessage(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_EDGE_EFFORT, vehID,...
    1+4+1+4+1+4+1+4+length(edgeID)+1+4);
message.string = [message.string uint8(sscanf(constants.TYPE_COMPOUND,'%x')) ...
    traci.packInt32(int32(4)) uint8(sscanf(constants.TYPE_INTEGER,'%x')) ...
    traci.packInt32(int32(begTime)) uint8(sscanf(constants.TYPE_INTEGER,'%x')) ...
    traci.packInt32(int32(endTime)) uint8(sscanf(constants.TYPE_STRING,'%x')) ...
    traci.packInt32(int32(length(uint8(edgeID)))) uint8(edgeID)];
message.string = [message.string uint8(sscanf(constants.TYPE_DOUBLE,'%x')) ...
    traci.packInt64(effort)];
traci.sendExact()